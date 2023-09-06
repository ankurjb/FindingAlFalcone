package com.ankurjb.lengaburu.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ankurjb.lengaburu.composable.navigateTo
import com.ankurjb.lengaburu.composable.onClick
import com.ankurjb.lengaburu.composable.screens.SelectPlanetsScreen
import com.ankurjb.lengaburu.composable.screens.SelectVehicleScreen
import com.ankurjb.lengaburu.composable.screens.ShowResultsScreen
import com.ankurjb.lengaburu.viewmodels.ResultViewModel

@Composable
fun MainNavigation(
    finish: onClick,
    navController: NavHostController = rememberNavController()
) = NavHost(
    navController = navController,
    startDestination = FindFalconeRoute.Planets.route
) {
    composable(route = FindFalconeRoute.Planets.route) {
        SelectPlanetsScreen(
            onBackPress = finish
        ) {
            navController.navigateTo(
                FindFalconeRoute.Vehicle.route,
                bundleOf(ResultViewModel.PLANETS to it.toTypedArray())
            )
        }
    }
    composable(route = FindFalconeRoute.Vehicle.route) {
        val planets = it.arguments?.getStringArray(ResultViewModel.PLANETS).orEmpty()
        SelectVehicleScreen(
            onBackPress = { navController.navigateUp() }
        ) { vehicles ->
            navController.navigateTo(
                FindFalconeRoute.Result.route,
                bundleOf(
                    ResultViewModel.VEHICLES to vehicles.toTypedArray(),
                    ResultViewModel.PLANETS to planets
                )
            )
        }
    }
    composable(route = FindFalconeRoute.Result.route) {
        ShowResultsScreen {
            navController.navigateUp()
        }
    }
}
