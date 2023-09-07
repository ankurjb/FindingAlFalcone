package com.ankurjb.lengaburu.composable

import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.ankurjb.lengaburu.model.PlanetResponse

typealias onClick = () -> Unit

fun NavController.navigateTo(
    route: String,
    args: Bundle = bundleOf(),
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    graph.matchDeepLink(routeLink)?.let {
        navigate(it.destination.id, args, navOptions, navigatorExtras)
    } ?: navigate(route, navOptions, navigatorExtras)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    appBarText: String,
    submitButtonText: String,
    showSubmitButton: Boolean = true,
    isSubmitEnabled: Boolean,
    onSubmitClick: onClick,
    snackBarHost: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) = Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
        if (showSubmitButton) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                onClick = onSubmitClick,
                enabled = isSubmitEnabled
            ) {
                Text(text = submitButtonText)
            }
        }
    },
    topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(text = appBarText)
            },
            //navigationIcon = { Icon(Icons.Filled.KeyboardArrowLeft, "") }
        )
    },
    snackbarHost = snackBarHost
) { paddingValues ->
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteText(
    header: String,
    getPlanets: (String) -> List<PlanetResponse>,
    onValueChange: (PlanetResponse) -> Unit
) {
    val uiState = rememberAutocompleteUiState()
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = uiState.interactionSource,
                indication = null,
                onClick = {
                    uiState.expanded = false
                }
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = uiState.planetName,
                    onValueChange = {
                        uiState.planetName = it
                        uiState.expanded = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            uiState.textFieldSize = coordinates.size.toSize()
                        },
                    label = { Text(header) },
                    trailingIcon = {
                        IconButton(onClick = { uiState.expanded = !uiState.expanded }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = uiState.icon,
                                contentDescription = "arrow",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            AnimatedVisibility(visible = uiState.expanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(uiState.textFieldSize.width.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                    shape = RoundedCornerShape(2.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 150.dp),
                    ) {
                        itemsIndexed(getPlanets(uiState.planetName)) { index: Int, item: PlanetResponse ->
                            CategoryItems(title = item.name) {
                                uiState.planetName = item.name
                                uiState.expanded = false
                                onValueChange(item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItems(
    title: String,
    onSelect: onClick
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onSelect)
        .padding(10.dp)
) {
    Text(text = title, fontSize = 16.sp)
}

class AutoCompleteTextUiState {
    var planetName by mutableStateOf("")
    var textFieldSize by mutableStateOf(Size.Zero)
    var expanded by mutableStateOf(false)
    val interactionSource = MutableInteractionSource()
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
}

@Composable
fun rememberAutocompleteUiState() = remember {
    AutoCompleteTextUiState()
}
