package com.ankurjb.lengaburu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ankurjb.lengaburu.composable.navigateTo
import com.ankurjb.lengaburu.composable.onClick
import com.ankurjb.lengaburu.composable.screens.SelectPlanetsScreen
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
        ) { (timeTaken, planets, vehicles) ->
            navController.navigateTo(
                FindFalconeRoute.Result.route,
                ResultViewModel.getBundle(
                    timeTaken = timeTaken,
                    planets = planets,
                    vehicles = vehicles
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
