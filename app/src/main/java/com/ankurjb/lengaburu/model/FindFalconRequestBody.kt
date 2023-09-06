package com.ankurjb.lengaburu.model


import com.google.gson.annotations.SerializedName

data class FindFalconRequestBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("planet_names")
    val planetNames: Array<String>?,
    @SerializedName("vehicle_names")
    val vehicleNames: Array<String>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FindFalconRequestBody

        if (token != other.token) return false
        if (planetNames != null) {
            if (other.planetNames == null) return false
            if (!planetNames.contentEquals(other.planetNames)) return false
        } else if (other.planetNames != null) return false
        if (vehicleNames != null) {
            if (other.vehicleNames == null) return false
            if (!vehicleNames.contentEquals(other.vehicleNames)) return false
        } else if (other.vehicleNames != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = token.hashCode()
        result = 31 * result + (planetNames?.contentHashCode() ?: 0)
        result = 31 * result + (vehicleNames?.contentHashCode() ?: 0)
        return result
    }
}
