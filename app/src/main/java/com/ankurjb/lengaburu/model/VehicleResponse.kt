package com.ankurjb.lengaburu.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleResponse(
    @SerializedName("max_distance")
    val maxDistance: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("speed")
    val speed: Int,
    @SerializedName("total_no")
    val totalNo: Int
) : Parcelable
