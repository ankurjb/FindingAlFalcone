package com.ankurjb.lengaburu

import com.ankurjb.lengaburu.model.PlanetResponse
import com.ankurjb.lengaburu.model.VehicleResponse
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

internal fun getPlanetResponse(
    distance: Int = 100,
    name: String = "Kepler"
) = PlanetResponse(distance, name)

internal fun getVehicleResponse(
    maxDistance: Int = 100,
    name: String = "name",
    speed: Int = 0,
    totalNo: Int = 0
) = VehicleResponse(
    maxDistance = maxDistance,
    name = name,
    speed = speed,
    totalNo = totalNo
)
