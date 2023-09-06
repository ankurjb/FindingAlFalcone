package com.ankurjb.lengaburu.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankurjb.lengaburu.api.ApiService
import com.ankurjb.lengaburu.model.FindFalconRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val apiService: ApiService
) : ViewModel() {
    private val selectedPlanets by lazy {
        savedStateHandle.get<Array<String>>(PLANETS)
    }
    private val selectedVehicles by lazy {
        savedStateHandle.get<Array<String>>(VEHICLES)
    }
    private val _uiState = MutableStateFlow("")
    val uiState: StateFlow<String> = _uiState

    init {
        getToken()
    }

    private fun getToken() = viewModelScope.launch {
        val response = apiService.getToken()
        if (response.isSuccessful && response.body()?.token != null) {
            getResults(response.body()?.token!!)
        }
    }

    private fun getResults(token: String) = viewModelScope.launch {
        val response =
            apiService.findFalcone(
                FindFalconRequestBody(
                    token = token,
                    planetNames = selectedPlanets,
                    vehicleNames = selectedVehicles
                )
            )

        _uiState.update {
            if (response.isSuccessful && response.body()?.planetName != null) {
                response.body()?.planetName.orEmpty()
            } else {
                ""
            }
        }
    }

    companion object {
        const val PLANETS = "PLANETS"
        const val VEHICLES = "VEHICLES"
    }
}
