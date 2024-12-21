package com.example.ucp2.ui.viewmodel

import com.example.ucp2.data.entity.Jadwal

data class HomeUiState (
    val listMhs: List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)