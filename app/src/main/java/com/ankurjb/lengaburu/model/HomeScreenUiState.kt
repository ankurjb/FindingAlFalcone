package com.ankurjb.lengaburu.model

data class HomeScreenUiState(
    val planets: List<PlanetResponse> = emptyList(),
    val vehicles: List<VehicleResponse> = emptyList()
)
