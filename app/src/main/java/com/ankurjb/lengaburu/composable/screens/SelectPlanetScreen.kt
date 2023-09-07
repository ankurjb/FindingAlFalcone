package com.ankurjb.lengaburu.composable.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ankurjb.lengaburu.composable.AutoCompleteText
import com.ankurjb.lengaburu.composable.BaseScaffold
import com.ankurjb.lengaburu.model.PlanetResponse
import com.ankurjb.lengaburu.model.VehicleResponse
import com.ankurjb.lengaburu.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SelectPlanetsScreen(
    viewModel: MainViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    onSubmit: (Triple<Double, List<String>, List<String>>) -> Unit,
) {

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val uiState by rememberPlanetScreenUiState()
    val homeScreenUiState by viewModel.uiState.collectAsState()

    val updateCount: () -> Double = {
        viewModel.getTimeTaken(
            uiState.route1,
            uiState.route2,
            uiState.route3,
            uiState.route4
        )
    }

    BaseScaffold(
        appBarText = "Find Al Falcone:: Time taken ${uiState.timeTaken}",
        submitButtonText = "Proceed",
        isSubmitEnabled = uiState.isSubmitEnabled.value,
        onSubmitClick = {
            onSubmit(
                Triple(
                    uiState.timeTaken,
                    uiState.allSelectedPlanetNames,
                    uiState.allSelectedVehiclesNames
                )
            )
        },
        snackBarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {
        PlanetSelection(
            header = "Planet 1",
            getPlanets = viewModel::getPlanets,
            vehicleList = homeScreenUiState.vehicles,
            selectedVehicle = uiState.vehicle1,
            isVehicleEnabled = viewModel::isVehicleEnabled,
            updateSelectedVehicle = {
                uiState.vehicle1 = it
                uiState.timeTaken = updateCount()
            }
        ) {
            uiState.planet1 = it
            uiState.vehicle1 = null
            uiState.timeTaken = updateCount()
        }

        if (uiState.planet1 != null && uiState.vehicle1 != null) {
            PlanetSelection(
                header = "Planet 2",
                getPlanets = viewModel::getPlanets,
                vehicleList = homeScreenUiState.vehicles,
                selectedVehicle = uiState.vehicle2,
                isVehicleEnabled = viewModel::isVehicleEnabled,
                updateSelectedVehicle = {
                    uiState.vehicle2 = it
                    uiState.timeTaken = updateCount()
                }
            ) {
                uiState.planet2 = it
                uiState.vehicle2 = null
                uiState.timeTaken = updateCount()
            }
        }

        if (uiState.planet2 != null && uiState.vehicle2 != null) {
            PlanetSelection(
                header = "Planet 3",
                getPlanets = viewModel::getPlanets,
                vehicleList = homeScreenUiState.vehicles,
                selectedVehicle = uiState.vehicle3,
                isVehicleEnabled = viewModel::isVehicleEnabled,
                updateSelectedVehicle = {
                    uiState.vehicle3 = it
                    uiState.timeTaken = updateCount()
                }
            ) {
                uiState.planet3 = it
                uiState.vehicle3 = null
                uiState.timeTaken = updateCount()
            }
        }

        if (uiState.planet3 != null && uiState.vehicle3 != null) {
            PlanetSelection(
                header = "Planet 4",
                getPlanets = viewModel::getPlanets,
                vehicleList = homeScreenUiState.vehicles,
                selectedVehicle = uiState.vehicle4,
                isVehicleEnabled = viewModel::isVehicleEnabled,
                updateSelectedVehicle = {
                    uiState.vehicle4 = it
                    uiState.timeTaken = updateCount()
                }
            ) {
                uiState.planet4 = it
                uiState.vehicle4 = null
                uiState.timeTaken = updateCount()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.errorMessage.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            val snackBarResult = snackBarHostState.showSnackbar(it, "Retry")
            if (snackBarResult == SnackbarResult.ActionPerformed) run {
                viewModel.getInitialData()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.planetErrorMessage.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            val snackBarResult = snackBarHostState.showSnackbar(it, "Retry")
            if (snackBarResult == SnackbarResult.ActionPerformed) run {
                viewModel.getPlanetList()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.vehicleErrorMessage.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            val snackBarResult = snackBarHostState.showSnackbar(it, "Retry")
            if (snackBarResult == SnackbarResult.ActionPerformed) run {
                viewModel.getVehicleList()
            }
        }
    }
}

@Composable
fun PlanetSelection(
    header: String,
    getPlanets: (String) -> List<PlanetResponse>,
    vehicleList: List<VehicleResponse>,
    selectedVehicle: VehicleResponse?,
    isVehicleEnabled: (VehicleResponse, PlanetResponse) -> Boolean,
    updateSelectedVehicle: (VehicleResponse) -> Unit,
    updateSelectedPlanet: (PlanetResponse) -> Unit
) = Column {
    var planet: PlanetResponse? by remember {
        mutableStateOf(null)
    }
    AutoCompleteText(
        header = header,
        getPlanets = getPlanets
    ) {
        planet = it
        updateSelectedPlanet(it)
    }

    planet?.let { notNullPlanet ->
        Spacer(Modifier.height(8.dp))

        LazyRow {
            items(vehicleList) {
                val isEnabled by remember(notNullPlanet) {
                    mutableStateOf(isVehicleEnabled(it, notNullPlanet))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .clickable(enabled = isEnabled) {
                            updateSelectedVehicle(it)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedVehicle == it,
                        onClick = { updateSelectedVehicle(it) },
                        enabled = isEnabled,
                    )
                    Text(
                        text = "${it.name} (${it.totalNo})",
                        color = if (isEnabled) Color.Black else Color.Gray
                    )
                }
            }
        }
    }
}

class SelectPlanetScreenUiState {

    var timeTaken by mutableStateOf(0.0)
    val isSubmitEnabled: MutableState<Boolean>
        get() = mutableStateOf(
            planet1 != null &&
                    planet2 != null &&
                    planet3 != null &&
                    planet4 != null &&
                    vehicle1 != null &&
                    vehicle2 != null &&
                    vehicle3 != null &&
                    vehicle4 != null
        )

    var planet1: PlanetResponse? by mutableStateOf(null)
    var planet2: PlanetResponse? by mutableStateOf(null)
    var planet3: PlanetResponse? by mutableStateOf(null)
    var planet4: PlanetResponse? by mutableStateOf(null)

    var vehicle1: VehicleResponse? by mutableStateOf(null)
    var vehicle2: VehicleResponse? by mutableStateOf(null)
    var vehicle3: VehicleResponse? by mutableStateOf(null)
    var vehicle4: VehicleResponse? by mutableStateOf(null)
    val allSelectedPlanetNames: List<String>
        get() = listOfNotNull(planet1, planet2, planet3, planet4).map { it.name }

    val allSelectedVehiclesNames: List<String>
        get() = listOfNotNull(vehicle1, vehicle2, vehicle3, vehicle4).map { it.name }

    val route1: Pair<PlanetResponse?, VehicleResponse?>
        get() = Pair(planet1, vehicle1)
    val route2: Pair<PlanetResponse?, VehicleResponse?>
        get() = Pair(planet2, vehicle2)
    val route3: Pair<PlanetResponse?, VehicleResponse?>
        get() = Pair(planet3, vehicle3)
    val route4: Pair<PlanetResponse?, VehicleResponse?>
        get() = Pair(planet4, vehicle4)
}

@Composable
fun rememberPlanetScreenUiState() = remember {
    mutableStateOf(SelectPlanetScreenUiState())
}
