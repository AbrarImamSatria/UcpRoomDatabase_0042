package com.example.ucp2.repository

import com.example.ucp2.data.dao.DokterDao
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDtr (
    private val dokterDao: DokterDao
) : RepositoryDtr {

    override suspend fun insertDtr(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }

    override fun getAllDtr(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }

    override fun getDtr(id: String): Flow<Dokter> {
        return dokterDao.getDokter(id)
    }
}