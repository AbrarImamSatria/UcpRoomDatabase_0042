package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.repository.RepositoryJwl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeJwlViewModel (
    private val repositoryJwl: RepositoryJwl
) : ViewModel() {

    val homeJwlUiState: StateFlow<HomeJwlUiState> = repositoryJwl.getAllJwl()
        .filterNotNull()
        .map {
            HomeJwlUiState (
                listJwl = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeJwlUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeJwlUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeJwlUiState (
                isLoading = true,
            )
        )
}

data class HomeJwlUiState (
    val listJwl: List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)