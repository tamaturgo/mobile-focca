package com.tamaturgo.focca.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaSurface3
import com.tamaturgo.focca.presentation.theme.FoccaText

private val TrackWidth = 38.dp
private val TrackHeight = 22.dp
private val ThumbSize = 18.dp
private val ThumbPadding = 2.dp

/** Toggle switch, per PRD 7.7: 38x22 track, circular thumb, ember when on. */
@Composable
fun FoccaSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val trackColor = if (checked) FoccaEmber else FoccaSurface3
    val thumbColor = if (checked) androidx.compose.ui.graphics.Color(0xFF1A0E08) else FoccaText
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) TrackWidth - ThumbSize - ThumbPadding else ThumbPadding,
        label = "switchThumbOffset"
    )

    Box(
        modifier = modifier
            .size(width = TrackWidth, height = TrackHeight)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onCheckedChange(!checked) }
            )
            .background(color = trackColor, shape = CircleShape)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = thumbOffset)
                .size(ThumbSize)
                .background(color = thumbColor, shape = CircleShape)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun FoccaSwitchPreview() {
    androidx.compose.foundation.layout.Row(
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
    ) {
        FoccaSwitch(checked = true, onCheckedChange = {})
        FoccaSwitch(checked = false, onCheckedChange = {})
    }
}
