package com.example.ucp2.repository

import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJwl {
    suspend fun insertJwl(jadwal: Jadwal)

    fun getAllJwl(): Flow<List<Jadwal>>

    fun getJwl(id: Long): Flow<Jadwal>

    suspend fun deleteJwl(jadwal: Jadwal)

    suspend fun updateJwl(jadwal: Jadwal)
}