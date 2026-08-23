package com.tamaturgo.focca.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamaturgo.focca.presentation.theme.FoccaBodyStyle
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaLine
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

private val ChipShape = RoundedCornerShape(percent = 50)

/** Pill-shaped chip, inactive/active states per PRD 7.7. */
@Composable
fun FoccaChip(
    text: String,
    active: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (active) FoccaEmber.copy(alpha = 0.14f) else FoccaSurface2
    val borderColor = if (active) FoccaEmber else FoccaLine
    val textColor = if (active) FoccaEmber else FoccaTextMuted

    Text(
        text = text,
        style = FoccaBodyStyle.copy(fontSize = 12.sp, fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal),
        color = textColor,
        modifier = modifier
            .background(color = backgroundColor, shape = ChipShape)
            .border(width = 1.dp, color = borderColor, shape = ChipShape)
            .padding(horizontal = 11.dp, vertical = 7.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun FoccaChipRowPreview() {
    FlowRow(
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(7.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(7.dp)
    ) {
        FoccaChip(text = "Peito", active = true)
        FoccaChip(text = "Costas", active = false)
        FoccaChip(text = "Ombro", active = true)
        FoccaChip(text = "Bíceps", active = false)
    }
}
