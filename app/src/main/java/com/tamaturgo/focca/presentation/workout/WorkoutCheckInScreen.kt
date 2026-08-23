package com.tamaturgo.focca.presentation.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.components.FoccaCard
import com.tamaturgo.focca.presentation.components.FoccaChip
import com.tamaturgo.focca.presentation.components.FoccaPrimaryButton
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaGold
import com.tamaturgo.focca.presentation.theme.FoccaLine
import com.tamaturgo.focca.presentation.theme.FoccaScreenTitleStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

data class MuscleGroupOption(val label: String, val selected: Boolean)
data class MoodOption(val emoji: String, val selected: Boolean)

data class WorkoutCheckInUiState(
    val durationLabel: String = "48 min",
    val muscleGroups: List<MuscleGroupOption> = listOf(
        MuscleGroupOption("Chest", selected = true),
        MuscleGroupOption("Back", selected = false),
        MuscleGroupOption("Shoulders", selected = true),
        MuscleGroupOption("Biceps", selected = false),
        MuscleGroupOption("Triceps", selected = true),
        MuscleGroupOption("Legs", selected = false),
        MuscleGroupOption("Abs", selected = false),
        MuscleGroupOption("Glutes", selected = false)
    ),
    val intensityOptions: List<String> = listOf("Easy", "Normal", "Heavy"),
    val selectedIntensity: String = "Heavy",
    val moods: List<MoodOption> = listOf(
        MoodOption("😫", selected = false),
        MoodOption("😕", selected = false),
        MoodOption("😐", selected = false),
        MoodOption("🙂", selected = true),
        MoodOption("🔥", selected = false)
    )
)

/** Post-workout check-in (PRD screen 05). Standalone in this change. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkoutCheckInScreen(
    state: WorkoutCheckInUiState = WorkoutCheckInUiState(),
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.checkin_title), style = FoccaEyebrowStyle, color = FoccaTextMuted)
            Text(
                text = state.durationLabel,
                style = FoccaScreenTitleStyle.copy(fontSize = 26.sp),
                color = FoccaText,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        FoccaCard(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.checkin_muscle_groups_label), style = FoccaEyebrowStyle, color = FoccaTextMuted, modifier = Modifier.padding(bottom = 8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(7.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                state.muscleGroups.forEach { group -> FoccaChip(text = group.label, active = group.selected) }
            }
        }

        FoccaCard(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.checkin_intensity_label), style = FoccaEyebrowStyle, color = FoccaTextMuted, modifier = Modifier.padding(bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                state.intensityOptions.forEach { option ->
                    IntensityPill(label = option, active = option == state.selectedIntensity, modifier = Modifier.weight(1f))
                }
            }
        }

        FoccaCard(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.checkin_mood_label), style = FoccaEyebrowStyle, color = FoccaTextMuted, modifier = Modifier.padding(bottom = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                state.moods.forEach { mood -> MoodCircle(mood) }
            }
        }

        FoccaPrimaryButton(text = stringResource(R.string.action_save_log), onClick = onSaveClick)
    }
}

@Composable
private fun IntensityPill(label: String, active: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = if (active) FoccaEmber.copy(alpha = 0.12f) else androidx.compose.ui.graphics.Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                androidx.compose.foundation.BorderStroke(1.dp, if (active) FoccaEmber else FoccaLine),
                RoundedCornerShape(10.dp)
            )
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.5.sp,
            color = if (active) FoccaEmber else FoccaTextMuted
        )
    }
}

@Composable
private fun MoodCircle(mood: MoodOption) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .background(color = if (mood.selected) FoccaGold.copy(alpha = 0.14f) else FoccaSurface2, shape = CircleShape)
            .border(
                androidx.compose.foundation.BorderStroke(1.5.dp, if (mood.selected) FoccaGold else androidx.compose.ui.graphics.Color.Transparent),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = mood.emoji, fontSize = 19.sp)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun WorkoutCheckInScreenPreview() {
    WorkoutCheckInScreen()
}
