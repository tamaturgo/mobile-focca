package com.tamaturgo.focca.presentation.routine

import androidx.lifecycle.ViewModel
import com.tamaturgo.focca.presentation.components.WorkoutSplit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class RoutineDayUiModel(
    val dayLabel: String,
    val split: WorkoutSplit?,
    val workoutName: String,
    val subLabel: String?,
    val enabled: Boolean
)

data class RoutineUiState(
    val title: String = "Routine",
    // TODO(real-data): replace with the real RoutineDay schedule.
    val days: List<RoutineDayUiModel> = listOf(
        RoutineDayUiModel("MON", WorkoutSplit.A, "Workout A", "Chest + Triceps · 7:00 PM", enabled = true),
        RoutineDayUiModel("TUE", null, "Rest", null, enabled = false),
        RoutineDayUiModel("WED", WorkoutSplit.B, "Workout B", "Back + Biceps · 7:00 PM", enabled = true),
        RoutineDayUiModel("THU", WorkoutSplit.C, "Workout C", "Legs + Abs · 7:00 PM", enabled = true),
        RoutineDayUiModel("FRI", null, "Rest", null, enabled = false),
        RoutineDayUiModel("SAT", WorkoutSplit.A, "Workout A", "Chest + Triceps · 8:00 AM", enabled = true),
        RoutineDayUiModel("SUN", null, "Rest", null, enabled = false)
    )
)

@HiltViewModel
class RoutineViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RoutineUiState())
    val uiState: StateFlow<RoutineUiState> = _uiState
}
