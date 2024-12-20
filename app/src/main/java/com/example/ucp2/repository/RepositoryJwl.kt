package com.example.ucp2.repository

import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJwl {
    suspend fun insertJwl(jadwal: Jadwal)

    fun getAllJwl(): Flow<List<Jadwal>>

    fun getJwl(id: String): Flow<Jadwal>

    suspend fun deleteJwl(mahasiswa: Jadwal)

    suspend fun updateJwl(mahasiswa: Jadwal)
}