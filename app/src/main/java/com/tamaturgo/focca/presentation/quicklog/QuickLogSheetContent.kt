package com.tamaturgo.focca.presentation.quicklog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.components.BottomSheetHandle
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaSurface3
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

data class QuickLogAction(val icon: String, val label: String)

/** The freely previewable body of the quick-log sheet (PRD screen 13), triggered by Home's FAB. */
@Composable
fun QuickLogSheetContent(
    actions: List<QuickLogAction> = listOf(
        QuickLogAction("🏋️", stringResource(R.string.quicklog_action_workout)),
        QuickLogAction("⚖️", stringResource(R.string.quicklog_action_weight)),
        QuickLogAction("📏", stringResource(R.string.quicklog_action_measurement)),
        QuickLogAction("📸", stringResource(R.string.quicklog_action_photo)),
        QuickLogAction("🏆", stringResource(R.string.quicklog_action_record)),
        QuickLogAction("✕", stringResource(R.string.quicklog_action_cancel))
    ),
    onActionClick: (QuickLogAction) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(FoccaSurface, shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
            .padding(horizontal = 18.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BottomSheetHandle()
        Text(
            text = stringResource(R.string.quicklog_title),
            style = FoccaEyebrowStyle,
            color = FoccaTextMuted,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            items(actions) { action -> QuickLogItem(action = action, onClick = { onActionClick(action) }) }
        }
    }
}

@Composable
private fun QuickLogItem(action: QuickLogAction, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(color = FoccaSurface2, shape = RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(color = FoccaSurface3, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = action.icon, fontSize = 17.sp)
        }
        Text(text = action.label, fontSize = 12.sp, color = FoccaText)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickLogSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    onActionClick: (QuickLogAction) -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = FoccaSurface,
        dragHandle = null
    ) {
        QuickLogSheetContent(onActionClick = onActionClick)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun QuickLogSheetContentPreview() {
    QuickLogSheetContent()
}
