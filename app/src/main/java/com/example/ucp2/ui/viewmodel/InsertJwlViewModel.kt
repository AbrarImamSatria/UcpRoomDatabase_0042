package com.example.ucp2.ui.viewmodel

data class JwlUIState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val noHp: String? = null,
    val tanggalKonsultasi: String? = null,
    val status: String? = null,
) {
    fun isValid(): Boolean{
        return namaDokter == null
                && namaPasien == null
                && noHp == null
                && tanggalKonsultasi == null
                && status == null
    }
}