package com.tamaturgo.focca.presentation.profile

import androidx.lifecycle.ViewModel
import com.tamaturgo.focca.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Fixed navigation menu entries. Achievements is intentionally NOT listed here —
 * it already has its own bottom tab, and duplicating it as a Profile menu row
 * gave the app two different CTAs for the same destination.
 */
enum class ProfileMenuAction(val icon: String, val labelRes: Int) {
    Evolution("📈", R.string.profile_menu_evolution),
    Statistics("◍", R.string.profile_menu_statistics),
    Goals("🎯", R.string.profile_menu_goals),
    Notifications("🔔", R.string.profile_menu_notifications),
    Preferences("⚙", R.string.profile_menu_preferences)
}

data class ProfileUiState(
    val title: String = "Profile",
    // TODO(real-data): replace with the real user profile.
    val name: String = "Rafael",
    val memberSince: String = "Member since Mar 2026",
    val totalWorkouts: Int = 54,
    val currentStreak: Int = 12,
    val menuActions: List<ProfileMenuAction> = ProfileMenuAction.entries
)

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState
}
