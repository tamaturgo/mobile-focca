package com.tamaturgo.focca.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tamaturgo.focca.presentation.theme.FoccaGold
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaSurface3
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import androidx.compose.ui.graphics.Color as ComposeColor

/** State of a single day mark in [CadenceRow], per PRD section 7.5. */
enum class CadenceDayState { Trained, Rest, Today, Missed, Upcoming }

data class CadenceDay(val label: String, val state: CadenceDayState)

private val TallMarkHeight = 24.dp
private val ShortMarkHeight = 10.dp
private val MarkWidth = 4.dp

/**
 * The 7-day cadence strip — Focca's signature recurring element (PRD 7.5).
 * Reused wherever weekly cadence needs to be shown (Home at minimum).
 */
@Composable
fun CadenceRow(days: List<CadenceDay>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        days.forEach { day ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                CadenceDash(day.state)
                Text(
                    text = day.label,
                    style = FoccaMicroStyle,
                    color = if (day.state == CadenceDayState.Today) FoccaGold else FoccaTextFaint
                )
            }
        }
    }
}

@Composable
private fun CadenceDash(state: CadenceDayState) {
    val height = when (state) {
        CadenceDayState.Trained, CadenceDayState.Today, CadenceDayState.Missed -> TallMarkHeight
        CadenceDayState.Rest, CadenceDayState.Upcoming -> ShortMarkHeight
    }
    when (state) {
        CadenceDayState.Trained -> SolidDash(height, FoccaEmber, glow = true)
        CadenceDayState.Today -> SolidDash(height, FoccaGold, glow = true)
        CadenceDayState.Rest -> SolidDash(height, FoccaSurface3, glow = false)
        CadenceDayState.Upcoming -> SolidDash(height, FoccaSurface2, glow = false)
        CadenceDayState.Missed -> DashedOutlineDash(height)
    }
}

@Composable
private fun SolidDash(height: Dp, color: ComposeColor, glow: Boolean) {
    val shape: Shape = androidx.compose.foundation.shape.RoundedCornerShape(3.dp)
    Box(
        modifier = Modifier
            .width(MarkWidth)
            .height(height)
            .then(
                if (glow) Modifier.shadow(elevation = 4.dp, shape = shape, ambientColor = color, spotColor = color)
                else Modifier
            )
            .background(color = color, shape = shape)
    )
}

@Composable
private fun DashedOutlineDash(height: Dp) {
    Canvas(modifier = Modifier.width(MarkWidth).height(height)) {
        val stroke = Stroke(
            width = 1.5.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(3.dp.toPx(), 3.dp.toPx()))
        )
        val corner = CornerRadius(3.dp.toPx())
        val path = Path().apply {
            addRoundRect(
                androidx.compose.ui.geometry.RoundRect(
                    rect = androidx.compose.ui.geometry.Rect(offset = androidx.compose.ui.geometry.Offset.Zero, size = Size(size.width, size.height)),
                    cornerRadius = corner
                )
            )
        }
        drawPath(path = path, color = FoccaTextFaint, style = stroke)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun CadenceRowPreview() {
    val mockWeek = listOf(
        CadenceDay("S", CadenceDayState.Trained),
        CadenceDay("T", CadenceDayState.Rest),
        CadenceDay("Q", CadenceDayState.Trained),
        CadenceDay("Q", CadenceDayState.Today),
        CadenceDay("S", CadenceDayState.Upcoming),
        CadenceDay("S", CadenceDayState.Missed),
        CadenceDay("D", CadenceDayState.Rest)
    )
    CadenceRow(days = mockWeek)
}
