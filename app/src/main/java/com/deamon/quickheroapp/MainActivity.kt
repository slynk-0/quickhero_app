package com.deamon.quickheroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.deamon.quickheroapp.main_ui.screens.SplashScreen
import com.deamon.quickheroapp.ui.theme.QuickHeroAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickHeroAppTheme {
                SplashScreen(onNavigateToHome = {})
            }
        }
    }
}

