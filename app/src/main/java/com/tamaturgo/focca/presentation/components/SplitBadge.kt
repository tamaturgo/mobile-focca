package com.tamaturgo.focca.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaGold
import com.tamaturgo.focca.presentation.theme.FoccaPulse
import com.tamaturgo.focca.presentation.theme.SpaceGroteskFamily

/** A workout split letter, per PRD section 7.6. Splits beyond C are not styled yet — see PRD open question 9.1. */
enum class WorkoutSplit(val letter: String) { A("A"), B("B"), C("C") }

enum class SplitBadgeSize { Small, Large }

private fun WorkoutSplit.color(): Color = when (this) {
    WorkoutSplit.A -> FoccaEmber
    WorkoutSplit.B -> FoccaGold
    WorkoutSplit.C -> FoccaPulse
}

/** The reusable A/B/C badge (PRD 7.6), shared by Home, Routine, Today's workout, and Split list. */
@Composable
fun SplitBadge(
    split: WorkoutSplit,
    modifier: Modifier = Modifier,
    size: SplitBadgeSize = SplitBadgeSize.Small
) {
    val dimension = if (size == SplitBadgeSize.Small) 22.dp else 34.dp
    val cornerRadius = if (size == SplitBadgeSize.Small) 7.dp else 10.dp
    val fontSize = if (size == SplitBadgeSize.Small) 11.5.sp else 15.sp
    val color = split.color()

    Box(
        modifier = modifier
            .size(dimension)
            .background(color = color.copy(alpha = 0.16f), shape = RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = split.letter,
            color = color,
            fontFamily = SpaceGroteskFamily,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun SplitBadgePreview() {
    Row {
        WorkoutSplit.entries.forEach { split ->
            SplitBadge(split = split, size = SplitBadgeSize.Small)
        }
        WorkoutSplit.entries.forEach { split ->
            SplitBadge(split = split, size = SplitBadgeSize.Large)
        }
    }
}
