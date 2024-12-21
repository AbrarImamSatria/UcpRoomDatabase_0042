package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.RepositoryDtr
import kotlinx.coroutines.launch

class DokterViewModel(
    private val repositoryDtr: RepositoryDtr
) : ViewModel() {

    var uiState by mutableStateOf(DtrUIState())

    //Memperbarui  state berdasarkan input pengguna
    fun updateState(dokterEvent: DokterEvent) {
        uiState = uiState.copy(
            dokterEvent = dokterEvent,
        )
    }

    //validasi data input pengguna
    fun validateFields(): Boolean {
        val event = uiState.dokterEvent
        val errorState = FormErrorState(
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            spesialis = if (event.spesialis.isNotEmpty()) null else "spesialis tidak boleh kosong",
            klinik = if (event.klinik.isNotEmpty()) null else "Klinik tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "No Hp tidak boleh kosong",
            jamKerja = if (event.jamKerja.isNotEmpty()) null else "Jam Kerja tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.dokterEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDtr.insertDtr(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dokterEvent = DokterEvent(), // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }
    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class DtrUIState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val nama: String? = null,
    val spesialis: String? = null,
    val klinik: String? = null,
    val noHp: String? = null,
    val jamKerja:String? = null,
){
    fun isValid(): Boolean{
        return nama == null
                && spesialis == null
                && klinik == null
                && noHp == null
                && jamKerja == null
    }
}

//data class variabel yang menyimpan
//data input form
data class DokterEvent(
    val nama: String = "",
    val spesialis: String = "",
    val klinik: String = "",
    val noHp: String = "",
    val jamKerja:String = ""
)

//Menyimpan input ke form ke dalam entity
fun DokterEvent.toDokterEntity(): Dokter = Dokter(
    nama = nama,
    spesialis = spesialis,
    klinik = klinik,
    noHp = noHp,
    jamKerja = jamKerja
)