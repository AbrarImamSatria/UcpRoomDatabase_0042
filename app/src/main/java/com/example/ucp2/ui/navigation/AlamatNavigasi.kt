package com.example.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}

object DestinasiHomeJadwal : AlamatNavigasi {
    override val route = "home_jadwal"
}

object DestinasiInsertJadwal : AlamatNavigasi {
    override val route = "insert_jadwal"
}

object DestinasiDetail : AlamatNavigasi {
    override val route = "detail"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}

object DestinasiUpdate : AlamatNavigasi {
    override val route = "update"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}