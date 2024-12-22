package com.example.ucp2.ui.viewmodel

import com.example.ucp2.data.entity.Jadwal

data class DetailUiState (
    val detailUiEvent: JadwalEvent = JadwalEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == JadwalEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != JadwalEvent()
}

/*
* Data class untuk menampung data yang akan ditampilkan di UI*/

//memindahkan data dari entity ke ui
fun Jadwal.toDetailUiEvent() : JadwalEvent {
    return JadwalEvent(
        id = id,
        namaDokter = namaDokter,
        namaPasien = namaPasien,
        noHp = noHp,
        tanggalKonsultasi = tanggalKonsultasi,
        status = status
    )
}