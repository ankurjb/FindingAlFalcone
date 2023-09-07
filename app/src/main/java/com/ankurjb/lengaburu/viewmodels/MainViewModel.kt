package com.ankurjb.lengaburu.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankurjb.lengaburu.api.UiState
import com.ankurjb.lengaburu.mapper.ViewDataMapper
import com.ankurjb.lengaburu.model.HomeScreenUiState
import com.ankurjb.lengaburu.model.PlanetResponse
import com.ankurjb.lengaburu.model.VehicleResponse
import com.ankurjb.lengaburu.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val mapper: ViewDataMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.stateIn(
        scope = viewModelScope,
        initialValue = _uiState.value,
        started = SharingStarted.Lazily
    )

    private val _planetErrorMessage = MutableSharedFlow<String>()
    val planetErrorMessage: SharedFlow<String> = _planetErrorMessage

    private val _vehicleErrorMessage = MutableSharedFlow<String>()
    val vehicleErrorMessage: SharedFlow<String> = _vehicleErrorMessage

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage

    init {
        getInitialData()
    }

    fun getInitialData() = viewModelScope.launch {
        val (
            vehicleResponse,
            planetResponse
        ) = repository.getInitialData()
        processInitialData(planetResponse, vehicleResponse)
    }

    private suspend fun processInitialData(
        planetResponse: UiState<List<PlanetResponse>>,
        vehicleResponse: UiState<List<VehicleResponse>>
    ) {
        if (planetResponse is UiState.Success && vehicleResponse is UiState.Success) {
            _uiState.update {
                mapper.toHomeScreenUiState(planetResponse.data, vehicleResponse.data)
            }
        } else {
            when {
                planetResponse is UiState.Error -> _planetErrorMessage.emit(planetResponse.errorMessage)
                vehicleResponse is UiState.Error -> _vehicleErrorMessage.emit(vehicleResponse.errorMessage)
                else -> _errorMessage.emit("Something went wrong")
            }
        }
    }

    fun getPlanetList() = viewModelScope.launch {
        val response = repository.getPlanets()
        processPlanetList(response)
    }

    private suspend fun processPlanetList(
        response: UiState<List<PlanetResponse>>
    ) = when (response) {
        is UiState.Success -> _uiState.update {
            it.copy(planets = response.data)
        }

        is UiState.Error -> _planetErrorMessage.emit(response.errorMessage)
    }

    fun getVehicleList() = viewModelScope.launch {
        val response = repository.getVehicles()
        processVehicleList(response)
    }

    private suspend fun processVehicleList(
        response: UiState<List<VehicleResponse>>
    ) = when (response) {
        is UiState.Success -> _uiState.update {
            it.copy(vehicles = response.data)
        }

        is UiState.Error -> {
            _planetErrorMessage.emit(response.errorMessage)
        }
    }

    fun getPlanets(
        text: String
    ): List<PlanetResponse> {
        val planets = _uiState.value.planets
        return if (text.isEmpty()) {
            planets.sortedBy { vehicle ->
                vehicle.name
            }
        } else {
            planets.filter { vehicle ->
                vehicle.name.lowercase().contains(text.lowercase())
            }.sortedBy { planet -> planet.name }
        }
    }

    fun getTimeTaken(
        route1: Pair<PlanetResponse?, VehicleResponse?>,
        route2: Pair<PlanetResponse?, VehicleResponse?>,
        route3: Pair<PlanetResponse?, VehicleResponse?>,
        route4: Pair<PlanetResponse?, VehicleResponse?>,
    ): Double {
        var timeTaken = 0.0
        let(route1.first, route1.second) { planet, vehicle ->
            timeTaken += (planet.distance / vehicle.speed)
        }
        let(route2.first, route2.second) { planet, vehicle ->
            timeTaken += (planet.distance / vehicle.speed)
        }
        let(route3.first, route3.second) { planet, vehicle ->
            timeTaken += (planet.distance / vehicle.speed)
        }
        let(route4.first, route4.second) { planet, vehicle ->
            timeTaken += (planet.distance / vehicle.speed)
        }
        return timeTaken
    }

    inline fun <T, S> let(any1: T?, any2: S?, invove: (T, S) -> Unit) {
        if (any1 != null && any2 != null) invove(any1, any2)
    }

    fun isVehicleEnabled(
        vehicleResponse: VehicleResponse,
        planetResponse: PlanetResponse
    ) = planetResponse.distance < vehicleResponse.maxDistance * vehicleResponse.totalNo
}
