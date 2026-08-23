package com.tamaturgo.focca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tamaturgo.focca.presentation.navigation.FoccaBottomBar
import com.tamaturgo.focca.presentation.navigation.FoccaNavHost
import com.tamaturgo.focca.presentation.theme.FoccaTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single-activity host for the Compose UI tree. Annotated with [AndroidEntryPoint] so that
 * this activity (and the composables/ViewModels it hosts) can receive Hilt-injected
 * dependencies.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoccaTheme {
                FoccaApp()
            }
        }
    }
}

@Composable
fun FoccaApp() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { FoccaBottomBar(navController) }
    ) { innerPadding ->
        FoccaNavHost(navController = navController, innerPadding = innerPadding)
    }
}
