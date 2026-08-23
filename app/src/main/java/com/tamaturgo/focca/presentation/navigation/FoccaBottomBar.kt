package com.tamaturgo.focca.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

/**
 * Bottom navigation bar for the 5 fixed tabs (PRD section 4), styled per PRD
 * section 7.7: active item in ember with an indicator dot below it.
 */
@Composable
fun FoccaBottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    NavigationBar {
        Screen.tabs.forEach { screen ->
            val isSelected = currentDestination
                ?.hierarchy
                ?.any { it.route == screen.route } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                            contentDescription = screen.label
                        )
                        if (isSelected) {
                            ActiveIndicatorDot()
                        }
                    }
                },
                label = {
                    Text(
                        text = screen.label,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FoccaEmber,
                    selectedTextColor = FoccaEmber,
                    unselectedIconColor = FoccaTextMuted,
                    unselectedTextColor = FoccaTextMuted,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

/** Small dot shown under the active tab's icon, per PRD section 7.7. */
@Composable
private fun ActiveIndicatorDot(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(top = 2.dp)
            .size(4.dp)
            .background(color = FoccaEmber, shape = CircleShape)
    )
}
