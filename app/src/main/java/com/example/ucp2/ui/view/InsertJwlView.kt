@file:Suppress("unused")

package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.ucp2.ui.viewmodel.FormErrorStateJwl
import com.example.ucp2.ui.viewmodel.JadwalEvent
import com.example.ucp2.ui.widget.DynamicSelectTextField


@Composable
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    onValueChange: (JadwalEvent) -> Unit = {},
    errorState: FormErrorStateJwl = FormErrorStateJwl(),
    dokterList: List<String>,
    modifier: Modifier = Modifier,
) {

    Column (
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.namaPasien,
            onValueChange = {
                onValueChange(jadwalEvent.copy(namaPasien = it))
            },
            label = { Text("Nama") },
            isError = errorState.namaPasien != null,
            placeholder = { Text("Masukkan nama pasien") },
        )
        Text(
            text = errorState.namaPasien ?: "",
            color = Color.Red
        )

        DynamicSelectTextField(
            selectedValue = jadwalEvent.namaDokter,
            options = dokterList, // Daftar dokter untuk dropdown
            label = "Nama Dokter",
            onValueChangedEvent = {
                onValueChange(jadwalEvent.copy(namaDokter = it))
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.noHp,
            onValueChange = {
                onValueChange(jadwalEvent.copy(noHp = it))
            },
            label = { Text("Nomor pasien") },
            isError = errorState.noHp != null,
            placeholder = { Text("Masukkan nomor pasien") },
        )
        Text(
            text = errorState.noHp ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.tanggalKonsultasi,
            onValueChange = {
                onValueChange(jadwalEvent.copy(tanggalKonsultasi = it))
            },
            label = { Text("Tanggal") },
            isError = errorState.tanggalKonsultasi != null,
            placeholder = { Text("Masukkan tanggal Konsultasi") },
        )
        Text(
            text = errorState.tanggalKonsultasi ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.status,
            onValueChange = {
                onValueChange(jadwalEvent.copy(status = it))
            },
            label = { Text("Status Penanganan") },
            isError = errorState.status != null,
            placeholder = { Text("Masukkan status Konsultasi") },
        )
        Text(
            text = errorState.status ?: "",
            color = Color.Red
        )

    }
}