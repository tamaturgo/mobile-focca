package com.tamaturgo.focca.presentation.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.components.FoccaCard
import com.tamaturgo.focca.presentation.components.FoccaSwitch
import com.tamaturgo.focca.presentation.components.SplitBadge
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaBodyStyle
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaScreenTitleStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

@Composable
fun RoutineScreen(
    modifier: Modifier = Modifier,
    viewModel: RoutineViewModel = hiltViewModel(),
    onSplitListClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    RoutineScreenContent(state = uiState, modifier = modifier, onSplitListClick = onSplitListClick)
}

@Composable
fun RoutineScreenContent(
    state: RoutineUiState,
    modifier: Modifier = Modifier,
    onSplitListClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = stringResource(R.string.routine_title), style = FoccaScreenTitleStyle, color = FoccaText)

        FoccaCard(modifier = Modifier.fillMaxWidth(), contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)) {
            state.days.forEachIndexed { index, day ->
                RoutineDayRow(day)
                if (index != state.days.lastIndex) {
                    HorizontalDivider(color = com.tamaturgo.focca.presentation.theme.FoccaLine, thickness = 1.dp)
                }
            }
        }

        FoccaCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onSplitListClick)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = stringResource(R.string.split_menu_title), style = FoccaBodyStyle.copy(fontSize = 13.sp), color = FoccaText)
                    Text(
                        text = stringResource(R.string.routine_split_menu_subtitle),
                        style = FoccaMicroStyle.copy(fontSize = 10.5.sp),
                        color = FoccaTextFaint
                    )
                }
                Text(text = "›", style = FoccaBodyStyle, color = FoccaTextFaint)
            }
        }
    }
}

@Composable
private fun RoutineDayRow(day: RoutineDayUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(11.dp)) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(color = FoccaSurface2, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = day.dayLabel, style = FoccaMicroStyle, color = if (day.enabled) FoccaTextMuted else FoccaTextFaint)
            }
            Column {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    day.split?.let { SplitBadge(split = it) }
                    Text(text = day.workoutName, style = FoccaBodyStyle.copy(fontSize = 13.sp), color = FoccaText)
                }
                day.subLabel?.let {
                    Text(text = it, style = FoccaMicroStyle.copy(fontSize = 10.5.sp), color = FoccaTextFaint)
                }
            }
        }
        FoccaSwitch(checked = day.enabled, onCheckedChange = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun RoutineScreenPreview() {
    RoutineScreenContent(state = RoutineUiState())
}
