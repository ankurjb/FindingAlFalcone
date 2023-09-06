package com.ankurjb.lengaburu.composable.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ankurjb.lengaburu.composable.BaseScaffold
import com.ankurjb.lengaburu.composable.onClick

@Composable
fun SelectVehicleScreen(
    onBackPress: onClick,
    onVehicleSubmit: onClick
) {
    var isSubmitEnabled by remember {
        mutableStateOf(true)
    }
    BaseScaffold(
        appBarText = "Select Vehicles",
        submitButtonText = "Proceed",
        isSubmitEnabled = isSubmitEnabled,
        onSubmitClick = onVehicleSubmit
    ) {

    }
}
