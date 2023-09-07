package com.ankurjb.lengaburu.viewmodels

import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankurjb.lengaburu.api.UiState
import com.ankurjb.lengaburu.model.ResultUiState
import com.ankurjb.lengaburu.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MainRepository
) : ViewModel() {
    private val selectedPlanets by lazy {
        savedStateHandle.get<Array<String>>(PLANETS)
    }
    private val selectedVehicles by lazy {
        savedStateHandle.get<Array<String>>(VEHICLES)
    }
    private val timeTaken by lazy {
        savedStateHandle.get<Double>(TIME_TAKEN) ?: 0.0
    }
    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage

    init {
        getToken()
        _uiState.update {
            it.copy(timeTaken = timeTaken.toString())
        }
    }

    private fun getToken() = viewModelScope.launch {
        when (val response = repository.getToken()) {
            is UiState.Success -> getResults(response.data)
            is UiState.Error -> _errorMessage.emit(response.errorMessage)
        }
    }

    private fun getResults(token: String) = viewModelScope.launch {
        val response = repository.findFalcone(
            token = token,
            planets = selectedPlanets,
            vehicles = selectedVehicles
        )

        when (response) {
            is UiState.Success -> _uiState.update {
                it.copy(
                    message = "Al Falcone is hiding in ${response.data}",
                    errorMessage = null
                )
            }

            is UiState.Error -> {
                _errorMessage.emit(response.errorMessage)
                _uiState.update {
                    it.copy(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun retry() = getToken()

    companion object {
        private const val PLANETS = "PLANETS"
        private const val VEHICLES = "VEHICLES"
        private const val TIME_TAKEN = "TIME_TAKEN"

        fun getBundle(
            timeTaken: Double,
            planets: List<String>,
            vehicles: List<String>
        ) = bundleOf(
            PLANETS to planets.toTypedArray(),
            VEHICLES to vehicles.toTypedArray(),
            TIME_TAKEN to timeTaken
        )
    }
}
