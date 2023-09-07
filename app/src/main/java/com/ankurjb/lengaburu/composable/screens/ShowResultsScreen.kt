package com.ankurjb.lengaburu.composable.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ankurjb.lengaburu.composable.BaseScaffold
import com.ankurjb.lengaburu.viewmodels.ResultViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ShowResultsScreen(
    viewModel: ResultViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val result by viewModel.uiState.collectAsState()
    BaseScaffold(
        appBarText = "Results",
        submitButtonText = "Proceed",
        isSubmitEnabled = false,
        showSubmitButton = false,
        onSubmitClick = { },
        snackBarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                result.message?.let {
                    Text(text = it)
                }
                result.errorMessage?.let {
                    Text(text = it)
                }
                Text(text = "Time taken ${result.timeTaken}")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.errorMessage.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            val snackBarResult = snackBarHostState.showSnackbar(it, "Retry")
            if (snackBarResult == SnackbarResult.ActionPerformed) run {
                viewModel.retry()
            }
        }
    }
}
