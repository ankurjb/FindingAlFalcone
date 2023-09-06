package com.ankurjb.lengaburu.model

import com.google.gson.annotations.SerializedName

data class PlanetResponse(
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("name")
    val name: String
)
