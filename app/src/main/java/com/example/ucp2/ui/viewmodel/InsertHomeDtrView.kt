package com.example.ucp2.ui.viewmodel

data class DtrUIState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val id: String? = null,
    val nama: String? = null,
    val spesialis: String? = null,
    val klinik: String? = null,
    val noHp: String? = null,
    val jamKerja:String? = null,
){
    fun isValid(): Boolean{
        return id == null
                && nama == null
                && spesialis == null
                && klinik == null
                && noHp == null
                && jamKerja == null
    }
}

//data class variabel yang menyimpan
//data input form
data class DokterEvent(
    val id: String = "",
    val nama: String = "",
    val spesialis: String = "",
    val klinik: String = "",
    val noHp: String = "",
    val jamKerja:String = ""
)