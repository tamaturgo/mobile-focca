package com.tamaturgo.focca.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.components.CadenceRow
import com.tamaturgo.focca.presentation.components.FoccaCard
import com.tamaturgo.focca.presentation.components.FoccaGhostButton
import com.tamaturgo.focca.presentation.components.FoccaPrimaryButton
import com.tamaturgo.focca.presentation.components.SplitBadge
import com.tamaturgo.focca.presentation.quicklog.QuickLogSheetContent
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaBodyStyle
import com.tamaturgo.focca.presentation.theme.FoccaCardNameStyle
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaHeroNumberStyle
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaRadius
import com.tamaturgo.focca.presentation.theme.FoccaSurface
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaSurface3
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

private val OnEmberText = Color(0xFF1A0E08)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onStartWorkoutClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreenContent(state = uiState, modifier = modifier, onStartWorkoutClick = onStartWorkoutClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    onStartWorkoutClick: () -> Unit = {}
) {
    var showQuickLog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize().background(FoccaBg)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CadenceRow(days = state.cadenceWeek)
            StreakHero(streakDays = state.streakDays)
            state.todayWorkout?.let { TodayWorkoutCard(it, onStartWorkoutClick = onStartWorkoutClick) }
            WeeklyGoalCard(completed = state.weeklyGoalCompleted, target = state.weeklyGoalTarget)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(50.dp)
                .background(color = FoccaEmber, shape = RoundedCornerShape(FoccaRadius.sm + 8.dp))
                .clickable { showQuickLog = true },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "+", color = OnEmberText, style = FoccaHeroNumberStyle.copy(fontSize = 24.sp))
        }
    }

    if (showQuickLog) {
        ModalBottomSheet(
            onDismissRequest = { showQuickLog = false },
            sheetState = rememberModalBottomSheetState(),
            containerColor = FoccaSurface,
            dragHandle = null
        ) {
            QuickLogSheetContent(onActionClick = { showQuickLog = false })
        }
    }
}

@Composable
private fun StreakHero(streakDays: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = FoccaSurface, shape = RoundedCornerShape(FoccaRadius.lg))
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "$streakDays", style = FoccaHeroNumberStyle, color = FoccaEmber)
            Text(
                text = stringResource(R.string.home_streak_caption),
                style = FoccaBodyStyle.copy(fontSize = 11.5.sp),
                color = FoccaTextMuted
            )
        }
        Text(text = "🔥", style = FoccaHeroNumberStyle.copy(fontSize = 26.sp))
    }
}

@Composable
private fun TodayWorkoutCard(workout: TodayWorkoutSummary, onStartWorkoutClick: () -> Unit) {
    FoccaCard(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = workout.dayLabel, style = FoccaEyebrowStyle, color = FoccaTextFaint)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                SplitBadge(split = workout.split)
                Text(
                    text = stringResource(R.string.workout_label_format, workout.split.letter),
                    style = FoccaMicroStyle,
                    color = FoccaTextMuted
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(text = workout.name, style = FoccaCardNameStyle.copy(fontSize = 21.sp), color = FoccaText)
            Text(text = workout.time, style = FoccaMicroStyle.copy(fontSize = 13.sp), color = FoccaTextMuted)
        }
        Text(
            text = stringResource(R.string.home_workout_meta_format, workout.location, workout.exerciseCount, workout.durationLabel),
            style = FoccaBodyStyle.copy(fontSize = 12.sp),
            color = FoccaTextMuted
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            workout.exercisePreviews.forEach { exercise ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = FoccaSurface2, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 9.dp, vertical = 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = exercise.name, style = FoccaBodyStyle.copy(fontSize = 12.5.sp), color = FoccaText)
                    Text(text = exercise.sets, style = FoccaMicroStyle, color = FoccaTextMuted)
                }
            }
        }
        if (workout.extraExerciseCount > 0) {
            Text(
                text = stringResource(R.string.home_extra_exercises_format, workout.extraExerciseCount),
                style = FoccaMicroStyle.copy(fontSize = 10.5.sp),
                color = FoccaTextFaint,
                modifier = Modifier.padding(top = 6.dp)
            )
        }

        FoccaPrimaryButton(text = stringResource(R.string.action_start_workout), onClick = onStartWorkoutClick, modifier = Modifier.padding(top = 14.dp))
        FoccaGhostButton(text = stringResource(R.string.action_reschedule_workout), onClick = {}, modifier = Modifier.padding(top = 10.dp))
    }
}

@Composable
private fun WeeklyGoalCard(completed: Int, target: Int) {
    FoccaCard(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = stringResource(R.string.home_weekly_goal_label), style = FoccaBodyStyle.copy(fontSize = 13.sp), color = FoccaTextMuted)
            Text(text = "$completed / $target", style = FoccaMicroStyle.copy(fontSize = 13.sp), color = FoccaText)
        }
        val progress = if (target > 0) completed.toFloat() / target else 0f
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(6.dp)
                .background(color = FoccaSurface3, shape = RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .height(6.dp)
                    .background(color = FoccaEmber, shape = RoundedCornerShape(4.dp))
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(state = HomeUiState())
}
