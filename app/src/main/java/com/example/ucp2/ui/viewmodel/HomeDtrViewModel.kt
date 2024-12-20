package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dokter
import com.example.ucp2.repository.LocalRepositoryDtr
import com.example.ucp2.repository.RepositoryDtr
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDtrViewModel(
    private val repositoryDtr: RepositoryDtr
) : ViewModel(){

    val homeDtrUiState: StateFlow<HomeDtrUiState> = repositoryDtr.getAllDtr()
        .filterNotNull()
        .map {
            HomeDtrUiState(
                listDtr = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeDtrUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeDtrUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeDtrUiState(
                isLoading = true,
            )
        )
}

data class HomeDtrUiState (
    val listDtr: List<Dokter> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)