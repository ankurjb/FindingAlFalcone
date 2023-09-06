package com.ankurjb.lengaburu.composable.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ankurjb.lengaburu.composable.AutoCompleteText
import com.ankurjb.lengaburu.composable.BaseScaffold
import com.ankurjb.lengaburu.composable.onClick
import com.ankurjb.lengaburu.viewmodels.PlanetViewModel

@Composable
fun SelectPlanetsScreen(
    viewModel: PlanetViewModel = hiltViewModel(),
    onBackPress: onClick,
    onPlanetSubmit: (List<String>) -> Unit,
) {
    val uiState by rememberPlanetScreenUiState()
    val planets by viewModel.uiState.collectAsState()

    BaseScaffold(
        appBarText = "Select Planets",
        submitButtonText = "Proceed",
        isSubmitEnabled = uiState.isSubmitEnabled.value,
        onSubmitClick = { onPlanetSubmit(uiState.allSelectedPlanets) }
    ) {
        AutoCompleteText("Planet 1", categories = planets) {
            uiState.planet1 = it
        }

        if (uiState.planet1.isNotEmpty()) {
            AutoCompleteText("Planet 2", categories = planets) {
                uiState.planet2 = it
            }
        }

        if (uiState.planet2.isNotEmpty()) {
            AutoCompleteText("Planet 3", categories = planets) {
                uiState.planet3 = it
            }
        }

        if (uiState.planet3.isNotEmpty()) {
            AutoCompleteText("Planet 4", categories = planets) {
                uiState.planet4 = it
            }
        }
    }
}

class SelectPlanetScreenUiState {
    val isSubmitEnabled: MutableState<Boolean>
        get() = mutableStateOf(
            planet1.isNotEmpty() &&
                    planet2.isNotEmpty() &&
                    planet3.isNotEmpty() &&
                    planet4.isNotEmpty()
        )

    var planet1 by mutableStateOf("")
    var planet2 by mutableStateOf("")
    var planet3 by mutableStateOf("")
    var planet4 by mutableStateOf("")
    val allSelectedPlanets: List<String>
        get() = listOf(planet1, planet2, planet3, planet4)
}

@Composable
fun rememberPlanetScreenUiState() = remember {
    mutableStateOf(SelectPlanetScreenUiState())
}
