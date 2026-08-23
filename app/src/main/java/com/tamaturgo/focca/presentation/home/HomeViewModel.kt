package com.tamaturgo.focca.presentation.home

import androidx.lifecycle.ViewModel
import com.tamaturgo.focca.presentation.components.CadenceDay
import com.tamaturgo.focca.presentation.components.CadenceDayState
import com.tamaturgo.focca.presentation.components.WorkoutSplit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class TodayExercisePreview(val name: String, val sets: String)

data class TodayWorkoutSummary(
    val split: WorkoutSplit,
    val dayLabel: String,
    val name: String,
    val muscleGroups: String,
    val time: String,
    val location: String,
    val exerciseCount: Int,
    val durationLabel: String,
    val exercisePreviews: List<TodayExercisePreview>,
    val extraExerciseCount: Int
)

data class HomeUiState(
    val title: String = "Home",
    // TODO(real-data): replace with the current week's cadence from WorkoutSession history.
    val cadenceWeek: List<CadenceDay> = listOf(
        CadenceDay("M", CadenceDayState.Trained),
        CadenceDay("T", CadenceDayState.Rest),
        CadenceDay("W", CadenceDayState.Trained),
        CadenceDay("T", CadenceDayState.Today),
        CadenceDay("F", CadenceDayState.Upcoming),
        CadenceDay("S", CadenceDayState.Upcoming),
        CadenceDay("S", CadenceDayState.Rest)
    ),
    // TODO(real-data): replace with the real streak counter.
    val streakDays: Int = 12,
    // TODO(real-data): replace with the next scheduled/next-in-rotation workout.
    val todayWorkout: TodayWorkoutSummary? = TodayWorkoutSummary(
        split = WorkoutSplit.A,
        dayLabel = "Today · Thursday",
        name = "Chest + Triceps",
        muscleGroups = "Gym",
        time = "7:00 PM",
        location = "Gym",
        exerciseCount = 5,
        durationLabel = "~50 min",
        exercisePreviews = listOf(
            TodayExercisePreview("Barbell bench press", "4×10"),
            TodayExercisePreview("Flat dumbbell fly", "3×12"),
            TodayExercisePreview("Cable triceps pushdown", "4×12")
        ),
        extraExerciseCount = 2
    ),
    // TODO(real-data): replace with the real weekly goal progress.
    val weeklyGoalCompleted: Int = 3,
    val weeklyGoalTarget: Int = 4
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState
}
