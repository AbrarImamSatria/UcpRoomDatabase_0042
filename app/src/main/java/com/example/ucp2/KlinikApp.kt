package com.example.ucp2

import android.app.Application
import com.example.ucp2.dependeciesinjection.ContainerApp

class KlinikApp : Application() {
    lateinit var containerApp: ContainerApp // fungsinya untuk menyimpan instance

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)// membuat instance ContainerApp
        // instance adalah object yang dibuat dari class
    }
}