package com.example.ucp2.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.DestinasiInsert
import com.example.ucp2.ui.view.DetailJwlView
import com.example.ucp2.ui.view.HomeDtrView
import com.example.ucp2.ui.view.HomeJwlView
import com.example.ucp2.ui.view.InsertDtrView
import com.example.ucp2.ui.view.InsertJwlView
import com.example.ucp2.ui.view.UpdateJwlView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ) {
        composable(
            route = DestinasiHome.route
        ) {
            HomeDtrView(
                onAddDtr = {
                    navController.navigate(DestinasiInsert.route)
                },
                onLihatJadwal = {
                    navController.navigate(DestinasiHomeJadwal.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsert.route
        ) {
            InsertDtrView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            route = DestinasiHomeJadwal.route
        ) {
            HomeJwlView(
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetail.route}/$id")
                    println(
                        "PengelolaHalaman: id = $id"
                    )
                },
                onAddJwl = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsertJadwal.route) {
            InsertJwlView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(
            DestinasiDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong(DestinasiDetail.ID)
            id?.let { id ->
                DetailJwlView(
                    modifier = modifier,
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },

                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.ID) {
                    type = NavType.LongType
                }
            )
        ) {
            UpdateJwlView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}

