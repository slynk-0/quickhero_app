package com.deamon.quickheroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.deamon.quickheroapp.backend.FichaRPG
import com.deamon.quickheroapp.main_ui.screens.DetailsScreen
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
                var fichaSelecionada by remember { mutableStateOf<FichaRPG?>(null) }

                when {
                    fichaSelecionada != null -> {
                        BackHandler {
                            fichaSelecionada = null
                        }
                        DetailsScreen(
                            ficha = fichaSelecionada!!,
                            onLogoClick = {
                                fichaSelecionada = null
                            }
                        )
                    }
                    showHome -> HomeScreen(
                        onNavigateToDetails = { ficha ->
                            fichaSelecionada = ficha
                        }
                    )
                    else -> SplashScreen(onNavigateToHome = { showHome = true })
                }
            }
        }
    }
}
