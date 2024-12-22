package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KlinikApp

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                klinikApp().containerApp.repositoryDtr
            )
        }

        initializer {
            HomeDtrViewModel(
                klinikApp().containerApp.repositoryDtr,
            )
        }

        initializer {
            HomeJwlViewModel(
                klinikApp().containerApp.repositoryJwl,
            )
        }

        initializer {
            JadwalViewModel(
                klinikApp().containerApp.repositoryJwl,
                klinikApp().containerApp.repositoryDtr
            )
        }

        initializer {
            DetailjwlViewModel(
                createSavedStateHandle(),
                klinikApp().containerApp.repositoryJwl,
            )
        }

        initializer {
            UpdateJwlViewModel(
                createSavedStateHandle(),
                klinikApp().containerApp.repositoryJwl,
                klinikApp().containerApp.repositoryDtr
            )
        }
    }
}

fun CreationExtras.klinikApp(): KlinikApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KlinikApp)