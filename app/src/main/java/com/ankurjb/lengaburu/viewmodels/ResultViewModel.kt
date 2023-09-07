package com.ankurjb.lengaburu.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankurjb.lengaburu.api.UiState
import com.ankurjb.lengaburu.repo.VehiclesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: VehiclesRepository
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
        when (val response = repository.getToken()) {
            is UiState.Success -> getResults(response.data)
            is UiState.Error -> {

            }
        }
    }

    private fun getResults(token: String) = viewModelScope.launch {
        val response = repository.findFalcone(
            token = token,
            planetNames = selectedPlanets,
            vehicleNames = selectedVehicles
        )

        when (response) {
            is UiState.Success -> _uiState.update {
                response.data
            }

            is UiState.Error -> {

            }
        }
    }

    companion object {
        const val PLANETS = "PLANETS"
        const val VEHICLES = "VEHICLES"
    }
}
