package com.ankurjb.lengaburu.model

import com.google.gson.annotations.SerializedName

data class FindFalconeResponse(
    @SerializedName("planet_name")
    val planetName: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("error")
    val error: String?
)
