package com.ankurjb.lengaburu.navigation

sealed class FindFalconeRoute(val route: String) {
    object Planets : FindFalconeRoute("planets_route")
    object Vehicle : FindFalconeRoute("vehicles_route")
    object Result : FindFalconeRoute("search_result_route")
}
