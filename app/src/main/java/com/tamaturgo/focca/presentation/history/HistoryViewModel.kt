package com.tamaturgo.focca.presentation.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class CalendarDayUiModel(val day: Int, val trained: Boolean, val isToday: Boolean = false)

data class TimelineEntryUiModel(val date: String, val title: String, val meta: String)

data class HistoryUiState(
    val title: String = "History",
    // TODO(real-data): replace with the real completed-session calendar for the month.
    val monthLabel: String = "August",
    val weekdayHeaders: List<String> = listOf("M", "T", "W", "T", "F", "S", "S"),
    val calendarDays: List<CalendarDayUiModel> = listOf(
        CalendarDayUiModel(3, trained = false), CalendarDayUiModel(4, trained = true), CalendarDayUiModel(5, trained = false),
        CalendarDayUiModel(6, trained = true), CalendarDayUiModel(7, trained = false), CalendarDayUiModel(8, trained = true),
        CalendarDayUiModel(9, trained = false),
        CalendarDayUiModel(10, trained = false), CalendarDayUiModel(11, trained = true), CalendarDayUiModel(12, trained = false),
        CalendarDayUiModel(13, trained = true), CalendarDayUiModel(14, trained = false), CalendarDayUiModel(15, trained = true),
        CalendarDayUiModel(16, trained = false),
        CalendarDayUiModel(17, trained = false), CalendarDayUiModel(18, trained = true), CalendarDayUiModel(19, trained = false),
        CalendarDayUiModel(20, trained = true), CalendarDayUiModel(21, trained = false), CalendarDayUiModel(22, trained = true, isToday = true),
        CalendarDayUiModel(23, trained = false)
    ),
    // TODO(real-data): replace with the real WorkoutSession history.
    val timeline: List<TimelineEntryUiModel> = listOf(
        TimelineEntryUiModel("AUG 22", "Strength training", "52 min · 🔥 Heavy"),
        TimelineEntryUiModel("AUG 20", "Strength training", "48 min · 🙂 Normal"),
        TimelineEntryUiModel("AUG 18", "Strength training", "1h02 · 🔥 Heavy")
    )
)

@HiltViewModel
class HistoryViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState
}
