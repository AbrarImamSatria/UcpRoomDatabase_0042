package com.example.ucp2.dependeciesinjection

import android.content.Context
import com.example.ucp2.data.database.KlinikDatabase
import com.example.ucp2.repository.LocalRepositoryDtr
import com.example.ucp2.repository.LocalRepositoryJwl
import com.example.ucp2.repository.RepositoryDtr
import com.example.ucp2.repository.RepositoryJwl

interface InterfaceContainerApp{
    val repositoryDtr: RepositoryDtr
    val repositoryJwl: RepositoryJwl
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryDtr: RepositoryDtr by lazy {
        LocalRepositoryDtr(KlinikDatabase.getDatabase(context).dokterDao())
    }

    override val repositoryJwl: RepositoryJwl by lazy {
        LocalRepositoryJwl(KlinikDatabase.getDatabase(context).jadwalDao())
    }
}
