package com.tamaturgo.focca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tamaturgo.focca.presentation.navigation.FoccaBottomBar
import com.tamaturgo.focca.presentation.navigation.FoccaNavHost
import com.tamaturgo.focca.presentation.navigation.Screen
import com.tamaturgo.focca.presentation.theme.FoccaBg
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
        val darkScrim = FoccaBg.toArgb()
        // The app is dark-only (PRD 7.1): force dark system bars regardless of the
        // device's own light/dark setting, instead of letting them default to light.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(darkScrim),
            navigationBarStyle = SystemBarStyle.dark(darkScrim)
        )
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
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val isTabDestination = Screen.tabs.any { tab ->
        currentDestination?.hierarchy?.any { it.route == tab.route } == true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = FoccaBg,
        bottomBar = { if (isTabDestination) FoccaBottomBar(navController) }
    ) { innerPadding ->
        FoccaNavHost(navController = navController, innerPadding = innerPadding)
    }
}
