package com.deamon.quickheroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.deamon.quickheroapp.main_ui.screens.HomeScreen
import com.deamon.quickheroapp.main_ui.screens.SplashScreen
import com.deamon.quickheroapp.ui.theme.QuickHeroAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickHeroAppTheme {
                var showHome by remember { mutableStateOf(false) }
                if (showHome) {
                    HomeScreen()
                } else {
                    SplashScreen(onNavigateToHome = { showHome = true })
                }
            }
        }
    }
}
