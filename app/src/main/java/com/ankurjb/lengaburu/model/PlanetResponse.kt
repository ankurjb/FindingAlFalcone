package com.ankurjb.lengaburu.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanetResponse(
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("name")
    val name: String
) : Parcelable
