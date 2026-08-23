package com.tamaturgo.focca.presentation.routine

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class RoutineUiState(val title: String = "Routine")

@HiltViewModel
class RoutineViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RoutineUiState())
    val uiState: StateFlow<RoutineUiState> = _uiState
}
