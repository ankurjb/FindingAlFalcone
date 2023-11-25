package com.ankurjb.lengaburu.repo

import com.ankurjb.lengaburu.ApiDataMapper
import com.ankurjb.lengaburu.MainCoroutineRule
import com.ankurjb.lengaburu.api.ApiService
import com.ankurjb.lengaburu.api.UiState
import com.ankurjb.lengaburu.getPlanetResponse
import com.ankurjb.lengaburu.getVehicleResponse
import com.ankurjb.lengaburu.model.PlanetResponse
import com.ankurjb.lengaburu.model.TokenResponse
import com.ankurjb.lengaburu.model.VehicleResponse
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class MainRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainRepository: MainRepository

    @MockK
    lateinit var apiService: ApiService

    @MockK
    lateinit var mapper: ApiDataMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainRepository = MainRepositoryImpl(
            apiService = apiService,
            mapper = mapper,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun getInitialData() = runBlocking {
        mockkGetAllVehicles()
        mockkGetAllPlanets()

        mainRepository.getInitialData()

        coVerify(exactly = 1) {
            apiService.getAllVehicles()
            apiService.getAllPlanets()
        }
    }

    @Test
    fun getVehicles() = runTest {
        val vehicles = Response.success(
            listOf(getVehicleResponse(name = "Ducati"))
        )
        coEvery {
            apiService.getAllVehicles()
        } returns vehicles
        val result = mainRepository.getVehicles()
        Truth.assertThat((result as UiState.Success).data).isEqualTo(vehicles.body())
    }

    @Test
    fun getPlanets() = runTest {
        val planets = Response.success(
            listOf(getPlanetResponse(name = "Earth"))
        )
        coEvery {
            apiService.getAllPlanets()
        } returns planets
        val result = mainRepository.getPlanets()
        Truth.assertThat((result as UiState.Success).data).isEqualTo(planets.body())
    }

    @Test
    fun getToken() = runTest {
        val token = "token"
        coEvery {
            apiService.getToken()
        } returns Response.success(TokenResponse(token))

        val response = mainRepository.getToken()
        Truth.assertThat((response as UiState.Success).data).isEqualTo(token)
    }

    private suspend fun mockkGetAllVehicles(
        vehicles: Response<List<VehicleResponse>> =
            Response.success(listOf(getVehicleResponse()))
    ) {
        coEvery {
            apiService.getAllVehicles()
        } returns vehicles
    }

    private suspend fun mockkGetAllPlanets(
        planets: Response<List<PlanetResponse>> =
            Response.success(listOf(getPlanetResponse()))
    ) {
        coEvery {
            apiService.getAllPlanets()
        } returns planets
    }
}
