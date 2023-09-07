package com.ankurjb.lengaburu.mapper

import com.ankurjb.lengaburu.model.HomeScreenUiState
import com.ankurjb.lengaburu.model.PlanetResponse
import com.ankurjb.lengaburu.model.VehicleResponse
import javax.inject.Inject

class ViewDataMapper @Inject constructor() {

    fun toHomeScreenUiState(
        planets: List<PlanetResponse>,
        vehicles: List<VehicleResponse>
    )= HomeScreenUiState(
        planets = planets,
        vehicles = vehicles
    )
}
