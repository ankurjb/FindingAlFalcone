package com.ankurjb.lengaburu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankurjb.lengaburu.api.ApiService
import com.ankurjb.lengaburu.model.FindFalconRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {


    fun getAllData() {
        getAllPlanets()
        getAllVehicles()
        getToken()
    }

    fun getAllPlanets() = viewModelScope.launch {
        val response = apiService.getAllPlanets()
    }

    private fun getAllVehicles() = viewModelScope.launch {
        val response = apiService.getAllVehicles()
    }

    private fun getToken() = viewModelScope.launch {
        val response = apiService.getToken()
        findFalcone(response.body()?.token.orEmpty())
    }

    private fun findFalcone(token: String) = viewModelScope.launch {
        val response = apiService.findFalcone(
            FindFalconRequestBody(
                token = token,
                planetNames = arrayListOf("Donlon", "Donlon", "Donlon", "Donlon"),
                vehicleNames = arrayListOf("Space pod", "", "", "")
            )
        )
    }
}
