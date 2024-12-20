package com.example.ucp2.repository

import com.example.ucp2.data.dao.JadwalDao
import com.example.ucp2.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

class LocalRepositoryJwl(
    private val jadwalDao: JadwalDao
): RepositoryJwl {
    override suspend fun insertJwl(jadwal: Jadwal) {
        jadwalDao.insertJadwal(jadwal)
    }

    override fun getAllJwl(): Flow<List<Jadwal>> {
        return jadwalDao.getAllJadwal()
    }

    override fun getJwl(id: String): Flow<Jadwal> {
        return jadwalDao.getJadwal(id)
    }

    override suspend fun deleteJwl(jadwal: Jadwal) {
        jadwalDao.deleteJadwal(jadwal)
    }

    override suspend fun updateJwl(jadwal: Jadwal) {
        jadwalDao.updateJadwal(jadwal)
    }
}