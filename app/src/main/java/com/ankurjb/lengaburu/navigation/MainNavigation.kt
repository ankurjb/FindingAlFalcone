package com.ankurjb.lengaburu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ankurjb.lengaburu.composable.onClick
import com.ankurjb.lengaburu.composable.screens.SelectPlanetsScreen
import com.ankurjb.lengaburu.composable.screens.SelectVehicleScreen
import com.ankurjb.lengaburu.composable.screens.ShowResultsScreen

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
            navController.navigate(FindFalconeRoute.Vehicle.route)
        }
    }
    composable(route = FindFalconeRoute.Vehicle.route) {
        SelectVehicleScreen(
            onBackPress = { navController.navigateUp() }
        ) {
            navController.navigate(FindFalconeRoute.Result.route)
        }
    }
    composable(route = FindFalconeRoute.Result.route) {
        ShowResultsScreen {
            navController.navigateUp()
        }
    }
}
