package com.ankurjb.lengaburu.mapper

import com.ankurjb.lengaburu.getPlanetResponse
import com.ankurjb.lengaburu.getVehicleResponse
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class ViewDataMapperTest {

    private lateinit var viewDataMapper: ViewDataMapper

    @Before
    fun init() {
        viewDataMapper = ViewDataMapper()
    }

    @Test
    fun toHomeScreenUiState() {
        val planets = listOf(
            getPlanetResponse()
        )
        val vehicles = listOf(
            getVehicleResponse()
        )
        val result = viewDataMapper.toHomeScreenUiState(planets, vehicles)
        Truth.assertThat(result.vehicles.map { it.name }).isEqualTo(vehicles.map { it.name })
        Truth.assertThat(result.planets.map { it.name }).isEqualTo(planets.map { it.name })
    }
}
