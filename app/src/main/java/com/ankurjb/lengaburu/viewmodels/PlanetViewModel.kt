package com.ankurjb.lengaburu.viewmodels

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
class PlanetViewModel @Inject constructor(
    private val repository: VehiclesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<String>>(emptyList())
    val uiState: StateFlow<List<String>> = _uiState

    init {
        getPlanetList()
    }

    private fun getPlanetList() = viewModelScope.launch {
        when (val response = repository.getPlanets()) {
            is UiState.Success -> _uiState.update {
                response.data
            }

            is UiState.Error -> {

            }
        }
    }
}
