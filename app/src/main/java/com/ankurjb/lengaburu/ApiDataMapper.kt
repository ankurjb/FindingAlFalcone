package com.ankurjb.lengaburu

import com.ankurjb.lengaburu.model.FindFalconRequestBody
import com.ankurjb.lengaburu.model.PlanetResponse
import com.ankurjb.lengaburu.model.VehicleResponse
import javax.inject.Inject

class ApiDataMapper @Inject constructor() {

    fun toFindFalconRequestBody(
        token: String,
        planets: Array<String>?,
        vehicles: Array<String>?
    ) = FindFalconRequestBody(
        token = token,
        planetNames = planets,
        vehicleNames = vehicles
    )
}
