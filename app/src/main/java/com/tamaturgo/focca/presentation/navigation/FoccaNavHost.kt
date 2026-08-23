package com.tamaturgo.focca.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tamaturgo.focca.presentation.achievements.AchievementsScreen
import com.tamaturgo.focca.presentation.history.HistoryScreen
import com.tamaturgo.focca.presentation.home.HomeScreen
import com.tamaturgo.focca.presentation.profile.ProfileScreen
import com.tamaturgo.focca.presentation.routine.RoutineScreen

@Composable
fun FoccaNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Routine.route) { RoutineScreen() }
        composable(Screen.Achievements.route) { AchievementsScreen() }
        composable(Screen.History.route) { HistoryScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}
