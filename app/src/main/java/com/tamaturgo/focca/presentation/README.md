# presentation

UI layer (Jetpack Compose + MVVM). `theme/` holds the app-wide Material 3 theme.

Each feature gets its own package here, e.g. `presentation.tasks`, containing:
- `TasksScreen.kt` — stateless composables, hoisted state, no business logic.
- `TasksViewModel.kt` — `@HiltViewModel`, exposes a `StateFlow<TasksUiState>`, calls
  `domain.usecase` classes, never touches `data` directly.
- `TasksUiState.kt` — immutable data class describing everything the screen renders.

A ViewModel never references Android UI types (`Context`, `View`) or `data` classes —
only `domain.model` and `domain.usecase`.
