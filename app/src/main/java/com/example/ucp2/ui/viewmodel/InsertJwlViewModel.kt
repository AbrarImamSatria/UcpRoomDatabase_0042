package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryDtr
import com.example.ucp2.repository.RepositoryJwl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JadwalViewModel(
    private val repositoryJwl: RepositoryJwl,
    private val repositoryDtr: RepositoryDtr
) : ViewModel() {

    var JwluiState by mutableStateOf(JwlUIState())
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
    //Memperbarui  state berdasarkan input pengguna
    fun updateState(jadwalEvent: JadwalEvent) {
        JwluiState = JwluiState.copy(
            jadwalEvent = jadwalEvent,
        )
    }
    //validasi data input pengguna
    fun validateFields(): Boolean {
        val event = JwluiState.jadwalEvent
        val errorStateJwl = FormErrorStateJwl(
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "nama Dokter tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "nama Pasien tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "no Hp tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "tanggal Konsultasi tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "status tidak boleh kosong"
        )
        JwluiState = JwluiState.copy(isEntryValid =errorStateJwl )
        return errorStateJwl.isValid()
    }

    //menyimpan data ke repository
    fun saveData() {
        val currentEvent = JwluiState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJwl.insertJwl(currentEvent.toJadwalEntity())
                    JwluiState = JwluiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        jadwalEvent = JadwalEvent(), // Reset input form
                        isEntryValid = FormErrorStateJwl() // Reset error state
                    )
                } catch (e: Exception) {
                    JwluiState = JwluiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            JwluiState = JwluiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }
    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        JwluiState = JwluiState.copy(snackBarMessage = null)
    }
}

data class JwlUIState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorStateJwl = FormErrorStateJwl(),
    val snackBarMessage: String? = null,
)

//data class variabel yang menyimpan
//data input form
data class JadwalEvent(
    val namaDokter: String = "",
    val namaPasien: String = "",
    val noHp: String = "",
    val tanggalKonsultasi: String = "",
    val status: String = "",
)

data class FormErrorStateJwl(
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val noHp: String? = null,
    val tanggalKonsultasi: String? = null,
    val status: String? = null,
){
    fun isValid(): Boolean{
        return namaDokter == null
                && namaPasien == null
                && noHp == null
                && tanggalKonsultasi == null
                && status == null
    }
}

//Menyimpan input ke form ke dalam entity
fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noHp = noHp,
    tanggalKonsultasi = tanggalKonsultasi,
    status = status,
)