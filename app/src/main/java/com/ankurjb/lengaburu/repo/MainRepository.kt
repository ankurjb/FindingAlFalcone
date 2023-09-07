package com.ankurjb.lengaburu.repo

import android.util.Log
import com.ankurjb.lengaburu.ApiDataMapper
import com.ankurjb.lengaburu.api.ApiService
import com.ankurjb.lengaburu.api.UiState
import com.ankurjb.lengaburu.di.IoDispatcher
import com.ankurjb.lengaburu.model.PlanetResponse
import com.ankurjb.lengaburu.model.VehicleResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MainRepository {
    suspend fun getVehicles(): UiState<List<VehicleResponse>>

    suspend fun getPlanets(): UiState<List<PlanetResponse>>

    suspend fun getInitialData(): Pair<UiState<List<VehicleResponse>>, UiState<List<PlanetResponse>>>

    suspend fun getToken(): UiState<String>

    suspend fun findFalcone(
        token: String,
        planets: Array<String>?,
        vehicles: Array<String>?
    ): UiState<String>
}

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: ApiDataMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MainRepository {

    override suspend fun getInitialData() = withContext(ioDispatcher) {
        val vehiclesResult = async { apiService.getAllVehicles() }
        val planetsResult = async { apiService.getAllPlanets() }
        val (
            vehiclesResponse,
            planetsResponse
        ) = vehiclesResult.await() to planetsResult.await()
        val vehicles = when {
            vehiclesResponse.isSuccessful && vehiclesResponse.body().isNullOrEmpty()
                .not() -> UiState.Success(
                vehiclesResponse.body().orEmpty()
            )

            vehiclesResponse.isSuccessful -> UiState.Error("No Data Found")
            else -> UiState.Error(vehiclesResponse.message() ?: "No Data Found")
        }
        val planets = when {
            planetsResponse.isSuccessful && planetsResponse.body().isNullOrEmpty()
                .not() -> UiState.Success(
                planetsResponse.body().orEmpty()
            )

            planetsResponse.isSuccessful -> UiState.Error("No Data Found")
            else -> UiState.Error(planetsResponse.message() ?: "No Data Found")
        }
        return@withContext Pair(vehicles, planets)
    }

    override suspend fun getVehicles() = withContext(ioDispatcher) {
        val response = apiService.getAllVehicles()
        val result = when {
            response.isSuccessful && response.body().isNullOrEmpty().not() -> UiState.Success(
                response.body().orEmpty()
            )

            response.isSuccessful -> UiState.Error("No Data Found")
            else -> UiState.Error(response.message() ?: "No Data Found")
        }
        return@withContext result
    }

    override suspend fun getPlanets() = withContext(ioDispatcher) {
        val response = apiService.getAllPlanets()
        val result = when {
            response.isSuccessful && response.body().isNullOrEmpty().not() -> UiState.Success(
                response.body().orEmpty()
            )

            response.isSuccessful -> UiState.Error("No Data Found")
            else -> UiState.Error(response.message() ?: "No Data Found")
        }
        return@withContext result
    }

    override suspend fun getToken() = withContext(ioDispatcher) {
        val response = apiService.getToken()
        val result = when {
            response.isSuccessful && response.body()?.token.isNullOrEmpty()
                .not() -> UiState.Success(
                response.body()?.token.orEmpty()
            )

            response.isSuccessful -> {
                Log.d("TAG", "getResults Auth: ${response.raw()}")
                UiState.Error("No Data Found")
            }

            else -> {
                Log.d("TAG", "getResults Auth: ${response.raw()}")
                UiState.Error(response.message() ?: "No Data Found")
            }
        }
        return@withContext result
    }

    override suspend fun findFalcone(
        token: String,
        planets: Array<String>?,
        vehicles: Array<String>?
    ) = withContext(ioDispatcher) {
        val response = apiService.findFalcone(
            mapper.toFindFalconRequestBody(
                token = token,
                planets = planets,
                vehicles = vehicles
            )
        )
        val result = when {
            response.isSuccessful && response.body()?.planetName.isNullOrEmpty()
                .not() -> UiState.Success(
                response.body()?.planetName.orEmpty()
            )

            response.isSuccessful -> {
                Log.d("TAG", "getResults Success: ${response.raw()}")
                UiState.Error("No Data Found")
            }

            else -> {
                Log.d("TAG", "getResults: ${response.raw()}")
                UiState.Error(response.message() ?: "No Data Found")
            }
        }
        return@withContext result
    }
}
