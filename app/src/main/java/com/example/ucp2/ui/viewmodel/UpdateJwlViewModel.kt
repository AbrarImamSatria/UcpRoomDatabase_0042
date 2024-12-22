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
}
