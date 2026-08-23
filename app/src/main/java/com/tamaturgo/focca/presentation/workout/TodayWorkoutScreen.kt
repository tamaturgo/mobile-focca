package com.tamaturgo.focca.presentation.workout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.components.FoccaCard
import com.tamaturgo.focca.presentation.components.FoccaPrimaryButton
import com.tamaturgo.focca.presentation.components.SplitBadge
import com.tamaturgo.focca.presentation.components.WorkoutSplit
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaLine
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaScreenTitleStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

data class TodayWorkoutExerciseUiModel(
    val name: String,
    val setsAndReps: String,
    val equipment: String,
    val done: Boolean
)

data class TodayWorkoutUiState(
    val split: WorkoutSplit = WorkoutSplit.A,
    val subtitle: String = "Today · Thursday · 7:00 PM",
    val name: String = "Chest + Triceps",
    val exercises: List<TodayWorkoutExerciseUiModel> = listOf(
        TodayWorkoutExerciseUiModel("Barbell bench press", "4×10", "Barbell", done = true),
        TodayWorkoutExerciseUiModel("Incline bench press", "3×10", "Barbell", done = true),
        TodayWorkoutExerciseUiModel("Flat dumbbell fly", "3×12", "Dumbbells", done = false),
        TodayWorkoutExerciseUiModel("Cable triceps pushdown", "4×12", "Cable", done = false),
        TodayWorkoutExerciseUiModel("Skull crusher", "3×10", "Barbell", done = false)
    )
)

/** Today's workout checklist (PRD screen 02). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayWorkoutScreen(
    state: TodayWorkoutUiState = TodayWorkoutUiState(),
    modifier: Modifier = Modifier,
    onStartSessionClick: () -> Unit = {}
) {
    var selectedExercise by remember { mutableStateOf<TodayWorkoutExerciseUiModel?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = state.subtitle, style = FoccaEyebrowStyle, color = FoccaTextFaint)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    SplitBadge(split = state.split)
                    Text(
                        text = stringResource(R.string.workout_label_format, state.split.letter),
                        style = FoccaMicroStyle,
                        color = FoccaTextMuted
                    )
                }
            }
            Text(text = state.name, style = FoccaScreenTitleStyle, color = FoccaText, modifier = Modifier.padding(top = 4.dp))
        }

        FoccaCard(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp)
        ) {
            state.exercises.forEach { exercise ->
                ExerciseCheckRow(exercise, onClick = { selectedExercise = exercise })
            }
            AddExerciseRow()
        }

        FoccaPrimaryButton(text = stringResource(R.string.action_begin_session), onClick = onStartSessionClick)
    }

    selectedExercise?.let {
        ModalBottomSheet(
            onDismissRequest = { selectedExercise = null },
            sheetState = rememberModalBottomSheetState(),
            containerColor = FoccaSurface,
            dragHandle = null
        ) {
            ExerciseDetailSheetContent(state = ExerciseDetailUiState(name = it.name))
        }
    }
}

@Composable
private fun ExerciseCheckRow(exercise: TodayWorkoutExerciseUiModel, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(19.dp)
                .background(
                    color = if (exercise.done) FoccaEmber else Color.Transparent,
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    BorderStroke(1.5.dp, if (exercise.done) FoccaEmber else FoccaLine),
                    RoundedCornerShape(6.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (exercise.done) {
                Text(text = "✓", color = FoccaBg, fontSize = 11.sp)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = exercise.name,
                style = FoccaMicroStyle.copy(fontSize = 13.sp),
                color = if (exercise.done) FoccaTextMuted else FoccaText,
                textDecoration = if (exercise.done) TextDecoration.LineThrough else null
            )
            Text(
                text = "${exercise.setsAndReps} · ${exercise.equipment}",
                style = FoccaMicroStyle.copy(fontSize = 10.5.sp),
                color = FoccaTextFaint,
                modifier = Modifier.padding(top = 1.dp)
            )
        }
        Text(text = "›", style = FoccaMicroStyle, color = FoccaTextFaint)
    }
}

@Composable
private fun AddExerciseRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(19.dp), contentAlignment = Alignment.Center) {
            Text(text = "+", color = FoccaTextFaint, fontSize = 15.sp)
        }
        Column {
            Text(text = stringResource(R.string.today_workout_add_exercise), style = FoccaMicroStyle.copy(fontSize = 13.sp), color = FoccaTextFaint)
            Text(
                text = stringResource(R.string.today_workout_add_exercise_subtitle),
                style = FoccaMicroStyle.copy(fontSize = 10.5.sp),
                color = FoccaTextFaint,
                modifier = Modifier.padding(top = 1.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun TodayWorkoutScreenPreview() {
    TodayWorkoutScreen()
}
