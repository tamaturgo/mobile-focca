package com.tamaturgo.focca.presentation.navigation

/**
 * The 5 fixed bottom-navigation destinations from PRD section 4, in display order.
 */
sealed class Screen(val route: String, val label: String, val icon: String) {
    data object Home : Screen(route = "home", label = "Home", icon = "⌂")
    data object Routine : Screen(route = "routine", label = "Routine", icon = "▤")
    data object Achievements : Screen(route = "achievements", label = "Achievements", icon = "🏆")
    data object History : Screen(route = "history", label = "History", icon = "◷")
    data object Profile : Screen(route = "profile", label = "Profile", icon = "◍")

    companion object {
        val tabs = listOf(Home, Routine, Achievements, History, Profile)
    }
}
