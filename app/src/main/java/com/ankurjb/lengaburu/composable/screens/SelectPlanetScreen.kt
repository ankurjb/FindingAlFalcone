package com.ankurjb.lengaburu.composable.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ankurjb.lengaburu.composable.BaseScaffold
import com.ankurjb.lengaburu.composable.onClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPlanetsScreen(
    onBackPress: onClick,
    onPlanetSubmit: onClick,
) {
    var isSubmitEnabled by remember {
        mutableStateOf(true)
    }
    BaseScaffold(
        appBarText = "Select Planets",
        submitButtonText = "Proceed",
        isSubmitEnabled = isSubmitEnabled,
        onSubmitClick = onPlanetSubmit
    ) {

    }
}
