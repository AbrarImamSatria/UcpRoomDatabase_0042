package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryDtr
import com.example.ucp2.repository.RepositoryJwl
import com.example.ucp2.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJwlViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryJwl: RepositoryJwl,
    private val repositoryDtr: RepositoryDtr
) : ViewModel() {

    var updateJwlUIState by mutableStateOf(JwlUIState())
        private set
    var dokterList by mutableStateOf(emptyList<Dokter>())

    init {
        fetchDokterList()
    }
    private fun fetchDokterList() {
        viewModelScope.launch {
            repositoryDtr.getAllDtr().collect { dokter ->
                dokterList = dokter
            }
        }
    }

    private val _id: Long = checkNotNull(savedStateHandle[DestinasiUpdate.ID])

    init {
        viewModelScope.launch {
            updateJwlUIState = repositoryJwl.getJwl(_id)
                .filterNotNull()
                .first()
                .toUIStateJwl()
        }
    }

    fun updateState(jadwalEvent: JadwalEvent) {
        updateJwlUIState = updateJwlUIState.copy(
            jadwalEvent = jadwalEvent
        )
    }

    fun validateFieldsJwl(): Boolean {
        val event = updateJwlUIState.jadwalEvent
        val errorState = FormErrorStateJwl(
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "No HP tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong"
        )

        // Set error state ke UI state
        updateJwlUIState = updateJwlUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateJwlUIState.jadwalEvent

        if (validateFieldsJwl()) {
            viewModelScope.launch {
                try {
                    repositoryJwl.updateJwl(currentEvent.toJadwalEntity())
                    updateJwlUIState = updateJwlUIState.copy(
                        snackBarMessage = "Data berhasil di update",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorStateJwl()
                    )
                    println("snackBarMessage diatur: ${updateJwlUIState.snackBarMessage}")
                } catch (e: Exception) {
                    updateJwlUIState = updateJwlUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateJwlUIState = updateJwlUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateJwlUIState = updateJwlUIState.copy(snackBarMessage = null)
    }
}

fun Jadwal.toUIStateJwl(): JwlUIState = JwlUIState(
    jadwalEvent = this.toDetailUiEvent()
)