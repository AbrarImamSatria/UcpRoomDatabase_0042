package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.data.entity.Jadwal

@Composable
fun ItemDetailJwl(
    modifier: Modifier = Modifier,
    jadwal: Jadwal
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailJwl(judul = "Id : ", isinya = jadwal.id.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJwl(judul = "Nama Pasien : ", isinya = jadwal.namaPasien)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJwl(judul = "Nama Dokter : ", isinya = jadwal.namaDokter)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJwl(judul = "Nomor Hp : ", isinya = jadwal.noHp)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJwl(judul = "Tanggal : ", isinya = jadwal.tanggalKonsultasi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJwl(judul = "Status Penanganan", isinya = jadwal.status)
        }
    }
}

@Composable
fun ComponentDetailJwl(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {/*Do Nothing*/},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        })
}