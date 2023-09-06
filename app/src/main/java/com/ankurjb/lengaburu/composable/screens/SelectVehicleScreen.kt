package com.ankurjb.lengaburu.composable.screens

import androidx.activity.compose.BackHandler
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
import com.ankurjb.lengaburu.viewmodels.VehicleViewModel

@Composable
fun SelectVehicleScreen(
    viewModel: VehicleViewModel = hiltViewModel(),
    onBackPress: onClick,
    onVehicleSubmit: (List<String>) -> Unit
) {
    BackHandler {
        onBackPress()
    }
    val vehicles by viewModel.uiState.collectAsState()
    val uiState by rememberVehicleScreenUiState()
    BaseScaffold(
        appBarText = "Select Vehicles",
        submitButtonText = "Proceed",
        isSubmitEnabled = uiState.isSubmitEnabled.value,
        onSubmitClick = { onVehicleSubmit(uiState.allSelectedVehicles) }
    ) {
        AutoCompleteText(header = "Vehicle 1", categories = vehicles) {
            uiState.vehicle1 = it
        }
        if (uiState.vehicle1.isNotEmpty()) {
            AutoCompleteText(header = "Vehicle 2", categories = vehicles) {
                uiState.vehicle2 = it
            }
        }
        if (uiState.vehicle2.isNotEmpty()) {
            AutoCompleteText(header = "Vehicle 3", categories = vehicles) {
                uiState.vehicle3 = it
            }
        }
        if (uiState.vehicle3.isNotEmpty()) {
            AutoCompleteText(header = "Vehicle 4", categories = vehicles) {
                uiState.vehicle4 = it
            }
        }
    }
}

class SelectVehiclesScreenUiState {
    val isSubmitEnabled: MutableState<Boolean>
        get() = mutableStateOf(
            vehicle1.isNotEmpty() &&
                    vehicle2.isNotEmpty() &&
                    vehicle3.isNotEmpty() &&
                    vehicle4.isNotEmpty()
        )

    var vehicle1 by mutableStateOf("")
    var vehicle2 by mutableStateOf("")
    var vehicle3 by mutableStateOf("")
    var vehicle4 by mutableStateOf("")
    val allSelectedVehicles: List<String>
        get() = listOf(vehicle1, vehicle2, vehicle3, vehicle4)
}

@Composable
fun rememberVehicleScreenUiState() = remember {
    mutableStateOf(SelectVehiclesScreenUiState())
}
