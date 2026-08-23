package com.tamaturgo.focca.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tamaturgo.focca.presentation.theme.FoccaSurface3

/** Centered drag handle used at the top of every bottom sheet, per PRD 7.7. */
@Composable
fun BottomSheetHandle(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(4.dp)
                .background(color = FoccaSurface3, shape = RoundedCornerShape(3.dp))
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181B21)
@Composable
private fun BottomSheetHandlePreview() {
    BottomSheetHandle()
}
