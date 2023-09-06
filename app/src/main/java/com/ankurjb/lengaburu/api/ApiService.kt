package com.ankurjb.lengaburu.api

import com.ankurjb.lengaburu.model.FindFalconRequestBody
import com.ankurjb.lengaburu.model.FindFalconeResponse
import com.ankurjb.lengaburu.model.PlanetResponse
import com.ankurjb.lengaburu.model.TokenResponse
import com.ankurjb.lengaburu.model.VehicleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @GET("planets")
    suspend fun getAllPlanets(): Response<List<PlanetResponse>>

    @GET("vehicles")
    suspend fun getAllVehicles(): Response<List<VehicleResponse>>

    @POST("token")
    @Headers("Accept: application/json")
    suspend fun getToken(): Response<TokenResponse>

    @POST("find")
    @Headers(
        "Accept: application/json"
    )
    suspend fun findFalcone(
        @Body body: FindFalconRequestBody,
        @Header("Content-Type") header: String = "application/json"
    ): Response<FindFalconeResponse>
}
