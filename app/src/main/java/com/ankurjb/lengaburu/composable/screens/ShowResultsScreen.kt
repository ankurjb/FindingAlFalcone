package com.ankurjb.lengaburu.composable.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ankurjb.lengaburu.composable.BaseScaffold
import com.ankurjb.lengaburu.composable.onClick

@Composable
fun ShowResultsScreen(
    onBackPress: onClick
) {
    var isSubmitEnabled by remember {
        mutableStateOf(true)
    }
    BaseScaffold(
        appBarText = "Results",
        submitButtonText = "Proceed",
        isSubmitEnabled = isSubmitEnabled,
        showSubmitButton = false,
        onSubmitClick = { }
    ) {
        Text(text = "Results")
    }
}
