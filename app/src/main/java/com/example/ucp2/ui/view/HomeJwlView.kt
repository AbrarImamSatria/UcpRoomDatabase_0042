package com.example.ucp2.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.R
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.ui.viewmodel.HomeJwlUiState
import kotlinx.coroutines.launch

@Composable
fun BodyHomeJwlView (
    homeJwlUiState: HomeJwlUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
){

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } //Snackbar state
    when {
        homeJwlUiState.isLoading -> {
            //Menampilkan indikator loading
            Box (
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        homeJwlUiState.isError -> {
            //Menampilkan pesan error
            LaunchedEffect(homeJwlUiState.errorMessage) {
                homeJwlUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) //Tampilkan Snackbar
                    }
                }
            }
        }

        homeJwlUiState.listJwl.isEmpty() -> {
            //Menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data dokter.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            //Menampilkan daftar jadwal
            ListJadwal(
                listJwl = homeJwlUiState.listJwl,
                onClick = {
                    onClick(it)
                    println(
                        it
                    )
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListJadwal(
    listJwl: List<Jadwal>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
){
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listJwl,
            itemContent = {jwl ->
                CardJwl(
                    jwl = jwl,
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardJwl(
    jwl: Jadwal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Ikon account",
                tint = colorResource(id = R.color.blue),
                modifier = Modifier
                    .size(110.dp)
                    .padding(end = 20.dp)

            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = jwl.namaPasien,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = jwl.namaDokter,
                        fontSize = 15.sp,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hospital2),
                        contentDescription = "Logo Hospital",
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = jwl.tanggalKonsultasi,
                        fontSize = 15.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chronometer),
                        contentDescription = "Logo jam",
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = jwl.noHp,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}