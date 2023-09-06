package com.ankurjb.lengaburu.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

typealias onClick = () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    appBarText: String,
    submitButtonText: String,
    showSubmitButton: Boolean = true,
    isSubmitEnabled: Boolean,
    onSubmitClick: onClick,
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
            navigationIcon = { Icon(Icons.Filled.KeyboardArrowLeft, "") }
        )
    }
) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues)) {
        content()
    }
}
