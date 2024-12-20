package com.example.ucp2.repository

import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoryDtr {
    suspend fun insertDtr(dokter: Dokter)

    fun getAllDtr(): Flow<List<Dokter>>

    fun getDtr(id: String): Flow<Dokter>
}