package com.ankurjb.lengaburu.repo

import com.ankurjb.lengaburu.api.ApiService
import com.ankurjb.lengaburu.di.IoDispatcher
import com.ankurjb.lengaburu.model.FindFalconRequestBody
import com.ankurjb.lengaburu.api.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface VehiclesRepository {
    suspend fun getVehicles(): UiState<List<String>>

    suspend fun getPlanets(): UiState<List<String>>

    suspend fun getToken(): UiState<String>

    suspend fun findFalcone(
        token: String,
        planetNames: Array<String>?,
        vehicleNames: Array<String>?
    ): UiState<String>
}

class VehiclesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : VehiclesRepository {
    override suspend fun getVehicles() = withContext(ioDispatcher) {
        val response = apiService.getAllVehicles()
        val result = when {
            response.isSuccessful && response.body().isNullOrEmpty().not() -> UiState.Success(
                response.body().orEmpty().map { it.name }
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
                response.body().orEmpty().map { it.name }
            )

            response.isSuccessful -> UiState.Error("No Data Found")
            else -> UiState.Error(response.message() ?: "No Data Found")
        }
        return@withContext result
    }

    override suspend fun getToken() = withContext(ioDispatcher) {
        val response = apiService.getToken()
        val result = when {
            response.isSuccessful && response.body()?.token.isNullOrEmpty() -> UiState.Success(
                response.body()?.token.orEmpty()
            )

            response.isSuccessful -> UiState.Error("No Data Found")
            else -> UiState.Error(response.message() ?: "No Data Found")
        }
        return@withContext result
    }

    override suspend fun findFalcone(
        token: String,
        planetNames: Array<String>?,
        vehicleNames: Array<String>?
    ) = withContext(ioDispatcher) {
        val response = apiService.findFalcone(
            FindFalconRequestBody(
                token = token,
                planetNames = planetNames,
                vehicleNames = vehicleNames
            )
        )
        val result = when {
            response.isSuccessful && response.body()?.planetName.isNullOrEmpty() -> UiState.Success(
                response.body()?.planetName.orEmpty()
            )

            response.isSuccessful -> UiState.Error("No Data Found")
            else -> UiState.Error(response.message() ?: "No Data Found")
        }
        return@withContext result
    }
}
