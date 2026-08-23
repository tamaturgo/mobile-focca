package com.tamaturgo.focca.presentation.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.components.BottomSheetHandle
import com.tamaturgo.focca.presentation.components.FoccaChip
import com.tamaturgo.focca.presentation.components.FoccaPrimaryButton
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaBodyStyle
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaPulse
import com.tamaturgo.focca.presentation.theme.FoccaScreenTitleStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaSurface3
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

data class SetLogUiModel(val weightLabel: String, val repsLabel: String, val done: Boolean)

data class ExerciseDetailUiState(
    val name: String = "Flat dumbbell fly",
    val tags: List<String> = listOf("Chest", "Dumbbells"),
    val videoDurationLabel: String = "Example video · 0:24",
    val instructions: String = "Lying on the bench with dumbbells, arms open and slightly bent. " +
        "Lower with control until you feel the stretch in your chest, then return without clashing the dumbbells at the top.",
    val setGoal: String = "3×12",
    val sets: List<SetLogUiModel> = listOf(
        SetLogUiModel("14 kg", "12 reps", done = true),
        SetLogUiModel("14 kg", "12 reps", done = true),
        SetLogUiModel("— kg", "— reps", done = false)
    )
)

/** The freely previewable body of the exercise detail sheet (PRD screen 03). */
@Composable
fun ExerciseDetailSheetContent(state: ExerciseDetailUiState = ExerciseDetailUiState(), modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(FoccaSurface, shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
            .padding(horizontal = 18.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BottomSheetHandle()

        Column {
            Text(text = state.name, style = FoccaScreenTitleStyle, color = FoccaText)
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                state.tags.forEach { tag -> FoccaChip(text = tag, active = false) }
            }
        }

        VideoThumb(durationLabel = state.videoDurationLabel)

        Text(text = state.instructions, style = FoccaBodyStyle.copy(fontSize = 12.sp), color = FoccaTextMuted)

        Column {
            Text(
                text = stringResource(R.string.exercise_detail_set_goal_format, state.setGoal),
                style = FoccaEyebrowStyle,
                color = FoccaTextFaint,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            state.sets.forEachIndexed { index, set ->
                SetRow(index = index + 1, set = set)
            }
        }

        FoccaPrimaryButton(text = stringResource(R.string.action_complete_exercise), onClick = {})
    }
}

@Composable
private fun VideoThumb(durationLabel: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .background(
                brush = Brush.linearGradient(listOf(FoccaSurface2, FoccaSurface3)),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(44.dp)
                .background(color = com.tamaturgo.focca.presentation.theme.FoccaEmber, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "▶", color = Color(0xFF1A0E08), fontSize = 15.sp)
        }
        Text(
            text = durationLabel,
            style = FoccaMicroStyle.copy(fontSize = 10.sp),
            color = FoccaTextFaint,
            modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
        )
    }
}

@Composable
private fun SetRow(index: Int, set: SetLogUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "%02d".format(index), style = FoccaMicroStyle.copy(fontSize = 11.sp), color = FoccaTextFaint, modifier = Modifier.width(20.dp))
        Row(
            modifier = Modifier
                .weight(1f)
                .background(color = FoccaSurface2, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = set.weightLabel, style = FoccaMicroStyle.copy(fontSize = 12.5.sp), color = FoccaText)
            Text(text = set.repsLabel, style = FoccaMicroStyle.copy(fontSize = 12.5.sp), color = FoccaText)
        }
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(color = if (set.done) FoccaPulse else Color.Transparent, shape = CircleShape)
                .border(
                    androidx.compose.foundation.BorderStroke(1.5.dp, if (set.done) FoccaPulse else com.tamaturgo.focca.presentation.theme.FoccaLine),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (set.done) {
                Text(text = "✓", color = Color(0xFF062622), fontSize = 11.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailSheet(
    state: ExerciseDetailUiState,
    onDismissRequest: () -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = FoccaSurface,
        dragHandle = null
    ) {
        ExerciseDetailSheetContent(state = state)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun ExerciseDetailSheetContentPreview() {
    ExerciseDetailSheetContent()
}
