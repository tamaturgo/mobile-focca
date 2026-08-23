package com.tamaturgo.focca.presentation.workout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.components.FoccaPrimaryButton
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaPulse
import com.tamaturgo.focca.presentation.theme.FoccaScreenTitleStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaSurface3
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

data class WorkoutTimerUiState(
    val startedAtTime: String = "7:03 PM",
    val elapsedLabel: String = "42:15"
)

/** In-progress workout timer (PRD screen 04). */
@Composable
fun WorkoutTimerScreen(
    state: WorkoutTimerUiState = WorkoutTimerUiState(),
    modifier: Modifier = Modifier,
    onFinishClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.workout_timer_started_at_format, state.startedAtTime),
            style = FoccaEyebrowStyle,
            color = FoccaTextFaint
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TimerRing(elapsedLabel = state.elapsedLabel)
            Row(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .background(color = FoccaSurface2, shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .background(color = FoccaPulse, shape = CircleShape)
                )
                Text(text = stringResource(R.string.workout_timer_focus_tag), style = FoccaMicroStyle.copy(fontSize = 12.sp), color = FoccaPulse)
            }
        }

        FoccaPrimaryButton(text = stringResource(R.string.action_finish_workout), onClick = onFinishClick, modifier = Modifier.padding(bottom = 8.dp))
    }
}

/**
 * Two-tone progress ring: an ember arc over a neutral track, approximating the
 * mockup's rotated per-side-colored circle border with a real arc instead.
 */
@Composable
private fun TimerRing(elapsedLabel: String) {
    Box(modifier = Modifier.size(190.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(190.dp)) {
            val strokeWidth = 3.dp.toPx()
            val stroke = Stroke(width = strokeWidth)
            drawArc(
                color = FoccaSurface3,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )
            drawArc(
                color = FoccaEmber,
                startAngle = -110f,
                sweepAngle = 200f,
                useCenter = false,
                style = stroke
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = elapsedLabel, style = FoccaScreenTitleStyle.copy(fontSize = 30.sp), color = FoccaText)
            Text(
                text = stringResource(R.string.workout_timer_in_progress_label),
                style = FoccaMicroStyle.copy(fontSize = 10.5.sp),
                color = FoccaTextMuted,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun WorkoutTimerScreenPreview() {
    WorkoutTimerScreen()
}
