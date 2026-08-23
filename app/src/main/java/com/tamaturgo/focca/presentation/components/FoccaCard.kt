package com.tamaturgo.focca.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tamaturgo.focca.presentation.theme.FoccaLine
import com.tamaturgo.focca.presentation.theme.FoccaRadius
import com.tamaturgo.focca.presentation.theme.FoccaSurface
import com.tamaturgo.focca.presentation.theme.FoccaSurface2

/** Default card: surface background + line border + md radius, per PRD 7.7. */
@Composable
fun FoccaCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(color = FoccaSurface, shape = RoundedCornerShape(FoccaRadius.md))
            .border(width = 1.dp, color = FoccaLine, shape = RoundedCornerShape(FoccaRadius.md))
            .padding(contentPadding),
        content = content
    )
}

/** Flat/inset card variant used for elements nested inside a [FoccaCard] (mockup's `.card-flat`). */
@Composable
fun FoccaCardFlat(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(color = FoccaSurface2, shape = RoundedCornerShape(FoccaRadius.sm))
            .padding(contentPadding),
        content = content
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun FoccaCardPreview() {
    Column {
        FoccaCard {
            androidx.compose.material3.Text(
                text = "Card content",
                style = com.tamaturgo.focca.presentation.theme.FoccaBodyStyle,
                color = com.tamaturgo.focca.presentation.theme.FoccaText
            )
        }
    }
}
