package com.ankurjb.lengaburu.model

import com.google.gson.annotations.SerializedName

data class VehicleResponse(
    @SerializedName("max_distance")
    val maxDistance: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("speed")
    val speed: Int,
    @SerializedName("total_no")
    val totalNo: Int
)
