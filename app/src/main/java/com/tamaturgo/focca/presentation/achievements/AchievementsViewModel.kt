package com.tamaturgo.focca.presentation.achievements

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class AchievementUiModel(
    val icon: String,
    val label: String,
    val unlocked: Boolean,
    val progressLabel: String? = null
)

data class AchievementsUiState(
    val title: String = "Achievements",
    // TODO(real-data): replace with the real achievement catalog + unlock state.
    val unlockedCount: Int = 5,
    val totalCount: Int = 9,
    val achievements: List<AchievementUiModel> = listOf(
        AchievementUiModel("🔥", "First workout", unlocked = true),
        AchievementUiModel("📅", "7-day streak", unlocked = true),
        AchievementUiModel("🥉", "10 workouts", unlocked = true),
        AchievementUiModel("🥈", "25 workouts", unlocked = false, progressLabel = "18/25"),
        AchievementUiModel("🥇", "50 workouts", unlocked = false, progressLabel = "18/50"),
        AchievementUiModel("🗓️", "1st full month", unlocked = true),
        AchievementUiModel("⏱️", "10h trained", unlocked = true),
        AchievementUiModel("⚡", "5-week streak", unlocked = false, progressLabel = "2/5"),
        AchievementUiModel("💯", "100 workouts", unlocked = false, progressLabel = "18/100")
    )
)

@HiltViewModel
class AchievementsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(AchievementsUiState())
    val uiState: StateFlow<AchievementsUiState> = _uiState
}
