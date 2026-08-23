package com.tamaturgo.focca.presentation.achievements

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class AchievementsUiState(val title: String = "Achievements")

@HiltViewModel
class AchievementsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(AchievementsUiState())
    val uiState: StateFlow<AchievementsUiState> = _uiState
}
