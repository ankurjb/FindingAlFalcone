package com.ankurjb.lengaburu.composable.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ankurjb.lengaburu.composable.BaseScaffold
import com.ankurjb.lengaburu.composable.onClick
import com.ankurjb.lengaburu.viewmodels.ResultViewModel

@Composable
fun ShowResultsScreen(
    viewModel: ResultViewModel = hiltViewModel(),
    onBackPress: onClick
) {
    val result by viewModel.uiState.collectAsState()
    BaseScaffold(
        appBarText = "Results",
        submitButtonText = "Proceed",
        isSubmitEnabled = false,
        showSubmitButton = false,
        onSubmitClick = { }
    ) {
        Text(text = result)
    }
}
