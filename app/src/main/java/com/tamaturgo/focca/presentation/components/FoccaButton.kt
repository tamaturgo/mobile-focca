package com.tamaturgo.focca.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tamaturgo.focca.presentation.theme.FoccaBodyStyle
import com.tamaturgo.focca.presentation.theme.FoccaButtonLabelStyle
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaLine
import com.tamaturgo.focca.presentation.theme.FoccaRadius
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

private val OnEmberText = Color(0xFF1A0E08)

/** Solid ember CTA button, per PRD 7.7. */
@Composable
fun FoccaPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(FoccaRadius.md),
        colors = ButtonDefaults.buttonColors(
            containerColor = FoccaEmber,
            contentColor = OnEmberText,
            disabledContainerColor = FoccaEmber.copy(alpha = 0.4f),
            disabledContentColor = OnEmberText.copy(alpha = 0.6f)
        ),
        contentPadding = PaddingValues(vertical = 13.dp)
    ) {
        Text(text = text, style = FoccaButtonLabelStyle)
    }
}

/** Outlined/ghost secondary button, per PRD 7.7. */
@Composable
fun FoccaGhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(FoccaRadius.md),
        border = BorderStroke(1.dp, FoccaLine),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = FoccaTextMuted),
        contentPadding = PaddingValues(vertical = 11.dp)
    ) {
        Text(text = text, style = FoccaBodyStyle)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun FoccaButtonsPreview() {
    androidx.compose.foundation.layout.Column(
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        FoccaPrimaryButton(text = "Começar treino", onClick = {})
        FoccaGhostButton(text = "Remarcar para outro dia", onClick = {})
    }
}
