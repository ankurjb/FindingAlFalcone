package com.ankurjb.lengaburu.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankurjb.lengaburu.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<String>>(emptyList())
    val uiState: StateFlow<List<String>> = _uiState

    init {
        getPlanetList()
    }

    private fun getPlanetList() = viewModelScope.launch {
        val response = apiService.getAllPlanets()
        if (response.isSuccessful) {
            _uiState.update {
                response.body()?.map { it.name }.orEmpty()
            }
        }
    }

    companion object {
    }
}
