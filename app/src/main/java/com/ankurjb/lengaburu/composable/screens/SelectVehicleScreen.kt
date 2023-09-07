package com.ankurjb.lengaburu.composable.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ankurjb.lengaburu.composable.AutoCompleteText
import com.ankurjb.lengaburu.composable.BaseScaffold
import com.ankurjb.lengaburu.composable.onClick
import com.ankurjb.lengaburu.model.VehicleResponse
import com.ankurjb.lengaburu.viewmodels.VehicleViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SelectVehicleScreen(
    viewModel: VehicleViewModel = hiltViewModel(),
    onBackPress: onClick,
    context: Context = LocalContext.current,
    onVehicleSubmit: (List<VehicleResponse>) -> Unit
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
        /*AutoCompleteText(
            header = "Vehicle 1",
            planets = vehicles,
            getPlanets = viewModel::getCategories,
            getCategoryName = { vehicles[it].name }
        ) {
            uiState.vehicle1 = it
        }
        uiState.vehicle1?.let {
            AutoCompleteText(
                header = "Vehicle 2",
                planets = vehicles,
                getPlanets = viewModel::getCategories,
                getCategoryName = { vehicles[it].name }
            ) {
                uiState.vehicle2 = it
            }
        }
        uiState.vehicle2?.let {
            AutoCompleteText(
                header = "Vehicle 3",
                planets = vehicles,
                getPlanets = viewModel::getCategories,
                getCategoryName = { vehicles[it].name }
            ) {
                uiState.vehicle3 = it
            }
        }
        uiState.vehicle3?.let {
            AutoCompleteText(
                header = "Vehicle 4",
                planets = vehicles,
                getPlanets = viewModel::getCategories,
                getCategoryName = { vehicles[it].name }
            ) {
                uiState.vehicle4 = it
            }
        }*/
    }

    LaunchedEffect(Unit) {
        viewModel.errorMessage.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
}

class SelectVehiclesScreenUiState {
    val isSubmitEnabled: MutableState<Boolean>
        get() = mutableStateOf(
            vehicle1 != null &&
                    vehicle2 != null &&
                    vehicle3 != null &&
                    vehicle4 != null
        )

    var vehicle1: VehicleResponse? by mutableStateOf(null)
    var vehicle2: VehicleResponse? by mutableStateOf(null)
    var vehicle3: VehicleResponse? by mutableStateOf(null)
    var vehicle4: VehicleResponse? by mutableStateOf(null)
    val allSelectedVehicles: List<VehicleResponse>
        get() = listOfNotNull(vehicle1, vehicle2, vehicle3, vehicle4)
}

@Composable
fun rememberVehicleScreenUiState() = remember {
    mutableStateOf(SelectVehiclesScreenUiState())
}
