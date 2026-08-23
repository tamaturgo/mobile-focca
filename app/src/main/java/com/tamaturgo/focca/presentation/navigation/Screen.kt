package com.tamaturgo.focca.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The 5 fixed bottom-navigation destinations from PRD section 4, in display order.
 *
 * Icons are real vector icons (not the unicode glyph placeholders from PRD section
 * 7.8) so every tab tints consistently with the active/inactive color, which a
 * color emoji glyph (e.g. a trophy emoji) cannot do.
 */
sealed class Screen(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Home : Screen(
        route = "home",
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    data object Routine : Screen(
        route = "routine",
        label = "Routine",
        selectedIcon = Icons.Filled.CalendarMonth,
        unselectedIcon = Icons.Outlined.CalendarMonth
    )

    data object Achievements : Screen(
        route = "achievements",
        label = "Achievements",
        selectedIcon = Icons.Filled.EmojiEvents,
        unselectedIcon = Icons.Outlined.EmojiEvents
    )

    data object History : Screen(
        route = "history",
        label = "History",
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History
    )

    data object Profile : Screen(
        route = "profile",
        label = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )

    companion object {
        val tabs = listOf(Home, Routine, Achievements, History, Profile)
    }
}

/**
 * Non-tab destinations (PRD section 4): reached by navigating from a tab screen,
 * shown full-screen without the bottom bar (see [com.tamaturgo.focca.FoccaApp]).
 */
object Routes {
    const val TodayWorkout = "today_workout"
    const val WorkoutTimer = "workout_timer"
    const val WorkoutCheckIn = "workout_check_in"
    const val SplitList = "split_list"
    const val Evolution = "evolution"
    const val Statistics = "statistics"
}
