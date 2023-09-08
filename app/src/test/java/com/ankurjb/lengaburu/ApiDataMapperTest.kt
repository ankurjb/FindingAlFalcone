package com.ankurjb.lengaburu

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class ApiDataMapperTest {

    private lateinit var apiDataMapper: ApiDataMapper

    @Before
    fun setUp() {
        apiDataMapper = ApiDataMapper()
    }

    @Test
    fun toFindFalconRequestBody() {
        val token= "token"
        val planets = arrayOf("planets")
        val vehicles = arrayOf("vehicles")
        val data =apiDataMapper.toFindFalconRequestBody(
            token = token,
            planets = planets,
            vehicles = vehicles
        )

        Truth.assertThat(data.token).isEqualTo(token)
        Truth.assertThat(data.planetNames).isEqualTo(planets)
        Truth.assertThat(data.vehicleNames).isEqualTo(vehicles)
    }
}


