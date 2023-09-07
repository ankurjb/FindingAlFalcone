package com.ankurjb.lengaburu.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankurjb.lengaburu.api.UiState
import com.ankurjb.lengaburu.model.VehicleResponse
import com.ankurjb.lengaburu.repo.VehiclesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val repository: VehiclesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<VehicleResponse>>(emptyList())
    val uiState: StateFlow<List<VehicleResponse>> = _uiState

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage

    init {
        getVehicleList()
    }

    fun getVehicleList() = viewModelScope.launch {
        when (val response = repository.getVehicles()) {
            is UiState.Success -> _uiState.update {
                response.data
            }

            is UiState.Error -> {
                _errorMessage.emit(response.errorMessage)
            }
        }
    }

    fun getCategories(
        text: String
    ): List<VehicleResponse> {
        val vehicles = _uiState.value
        return if (text.isEmpty()) {
            vehicles.sortedBy { vehicle ->
                vehicle.name
            }
        } else {
            vehicles.filter { vehicle ->
                vehicle.name.lowercase().contains(text.lowercase())
            }.sortedBy { planet -> planet.name }
        }
    }
}
