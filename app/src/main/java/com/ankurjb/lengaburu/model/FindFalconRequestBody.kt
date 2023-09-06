package com.ankurjb.lengaburu.model


import com.google.gson.annotations.SerializedName

data class FindFalconRequestBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("planet_names")
    val planetNames: ArrayList<String>,
    @SerializedName("vehicle_names")
    val vehicleNames: ArrayList<String>
)
