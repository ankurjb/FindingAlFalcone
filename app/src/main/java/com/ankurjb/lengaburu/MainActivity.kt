package com.ankurjb.lengaburu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ankurjb.lengaburu.navigation.MainNavigation
import com.ankurjb.lengaburu.ui.theme.LengaburuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LengaburuTheme {
                MainNavigation()
            }
        }
    }
}
