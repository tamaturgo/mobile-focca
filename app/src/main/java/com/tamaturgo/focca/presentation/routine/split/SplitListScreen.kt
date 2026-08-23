package com.tamaturgo.focca.presentation.routine.split

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import com.tamaturgo.focca.presentation.components.FoccaCardFlat
import com.tamaturgo.focca.presentation.components.SplitBadge
import com.tamaturgo.focca.presentation.components.SplitBadgeSize
import com.tamaturgo.focca.presentation.components.WorkoutSplit
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaBodyStyle
import com.tamaturgo.focca.presentation.theme.FoccaCardNameStyle
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaLine
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaScreenTitleStyle
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

data class SplitSummary(
    val split: WorkoutSplit,
    val name: String,
    val muscleGroups: String,
    val exerciseCount: Int,
    val durationLabel: String
)

private val MockSplits = listOf(
    SplitSummary(WorkoutSplit.A, "Workout A", "Chest · Shoulders · Triceps", 5, "~50 min"),
    SplitSummary(WorkoutSplit.B, "Workout B", "Back · Biceps", 5, "~45 min"),
    SplitSummary(WorkoutSplit.C, "Workout C", "Legs · Abs", 6, "~55 min")
)

/** A/B/C split list (PRD screen 07). Reached from Routine. */
@Composable
fun SplitListScreen(
    splits: List<SplitSummary> = MockSplits,
    nextSplitName: String = "Workout B",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column {
            Text(text = stringResource(R.string.split_menu_title), style = FoccaScreenTitleStyle, color = FoccaText)
            Text(
                text = stringResource(R.string.splitlist_subtitle),
                style = FoccaEyebrowStyle,
                color = FoccaTextFaint,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        FoccaCard(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = 16.dp)) {
            splits.forEachIndexed { index, split ->
                SplitCard(split)
                if (index != splits.lastIndex) {
                    HorizontalDivider(color = FoccaLine, thickness = 1.dp)
                }
            }
            HorizontalDivider(color = FoccaLine, thickness = 1.dp)
            CreateWorkoutRow()
        }

        FoccaCardFlat(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.splitlist_next_format, nextSplitName),
                style = FoccaBodyStyle.copy(fontSize = 12.sp),
                color = FoccaTextMuted
            )
        }
    }
}

@Composable
private fun SplitCard(split: SplitSummary) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        SplitBadge(split = split.split, size = SplitBadgeSize.Large)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = split.name, style = FoccaCardNameStyle, color = FoccaText)
            Text(
                text = split.muscleGroups,
                style = FoccaBodyStyle.copy(fontSize = 11.5.sp),
                color = FoccaTextMuted,
                modifier = Modifier.padding(top = 2.dp)
            )
            Text(
                text = stringResource(R.string.splitlist_exercise_count_format, split.exerciseCount, split.durationLabel),
                style = FoccaMicroStyle.copy(fontSize = 10.sp),
                color = FoccaTextFaint,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Text(text = "›", style = FoccaBodyStyle, color = FoccaTextFaint)
    }
}

@Composable
private fun CreateWorkoutRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "+", style = FoccaCardNameStyle, color = FoccaTextFaint)
        Column {
            Text(text = stringResource(R.string.splitlist_create_workout), style = FoccaCardNameStyle.copy(fontSize = 14.5.sp), color = FoccaTextFaint)
            Text(
                text = stringResource(R.string.splitlist_create_workout_hint),
                style = FoccaMicroStyle.copy(fontSize = 10.sp),
                color = FoccaTextFaint
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun SplitListScreenPreview() {
    SplitListScreen()
}
