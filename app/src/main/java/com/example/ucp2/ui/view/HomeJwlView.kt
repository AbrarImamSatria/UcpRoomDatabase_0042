package com.example.ucp2.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.data.entity.Jadwal
import com.example.ucp2.ui.viewmodel.HomeJwlUiState
import com.example.ucp2.ui.viewmodel.HomeJwlViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeJwlView(
    viewModel: HomeJwlViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddJwl: () -> Unit = { },
    onBack: () -> Unit = { },
    onDetailClick: (Long) -> Unit = { },
    modifier: Modifier = Modifier
){
    val homeJwlUiState by viewModel.homeJwlUiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.blue))
                    .padding(top = 15.dp)
                    .padding(30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.hospital),
                        contentDescription = "Logo Hospital",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "AYO SEHAT",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.boy),
                    contentDescription = "Logo profile",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),

                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.healthcare),
                            contentDescription = "Logo healthcare",
                            modifier = Modifier.size(40.dp)
                        )
                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Text(
                                text = "Chat Dokter di Ayo Sehat",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "Layanan telemedisin yang siap siaga untuk bantu kamu hidup lebih sehat.",
                                color = Color.DarkGray,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray, MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                            Text(
                                text = "Ketik Disini",
                                color = Color.Black,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    val blueColor = colorResource(id = R.color.biru)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onAddJwl,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = blueColor)
                        ) {
                            Text("Tambah Jadwal")
                        }
                        Button(
                            onClick = onBack,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = blueColor)
                        ) {
                            Text("Kembali")
                        }
                    }
                }
            }

            BodyHomeJwlView(
                homeJwlUiState = homeJwlUiState,
                onClick = onDetailClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BodyHomeJwlView (
    homeJwlUiState: HomeJwlUiState,
    onClick: (Long) -> Unit = { },
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
                    text = "Tidak ada data jadwal.",
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
                onClick = { id ->
                    onClick(id)
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
    onClick: (Long) -> Unit = { }
){
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listJwl,
            itemContent = {jwl ->
                CardJwl(
                    jwl = jwl,
                    onClick = { onClick(jwl.id) }
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
                        text = jwl.status,
                        color = Color.Blue,
                        fontSize = 15.sp,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.schedule),
                        contentDescription = "Logo Jadwal",
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
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
                        painter = painterResource(id = R.drawable.telephone),
                        contentDescription = "Logo telpon",
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = jwl.noHp,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}