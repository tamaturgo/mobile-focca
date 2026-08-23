package com.tamaturgo.focca.presentation.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaGold
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaScreenTitleStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    HistoryScreenContent(state = uiState, modifier = modifier)
}

@Composable
fun HistoryScreenContent(state: HistoryUiState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(text = state.monthLabel, style = FoccaScreenTitleStyle, color = FoccaText)

        CalendarGrid(weekdayHeaders = state.weekdayHeaders, days = state.calendarDays)

        Text(text = stringResource(R.string.history_timeline_label), style = FoccaEyebrowStyle, color = FoccaTextFaint)

        Column {
            state.timeline.forEachIndexed { index, entry ->
                TimelineRow(entry = entry, showLine = index != state.timeline.lastIndex)
            }
        }
    }
}

@Composable
private fun CalendarGrid(weekdayHeaders: List<String>, days: List<CalendarDayUiModel>) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            weekdayHeaders.forEach { header ->
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(text = header, style = FoccaMicroStyle.copy(fontSize = 9.sp), color = FoccaTextFaint)
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth().height(40.dp * ((days.size / 7) + 1)),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(days) { day -> CalendarCell(day) }
        }
    }
}

@Composable
private fun CalendarCell(day: CalendarDayUiModel) {
    val backgroundColor = if (day.trained) FoccaEmber else FoccaSurface
    val textColor = if (day.trained) androidx.compose.ui.graphics.Color(0xFF1A0E08) else FoccaTextFaint
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(color = backgroundColor, shape = RoundedCornerShape(6.dp))
            .then(
                if (day.isToday) {
                    Modifier.border(BorderStroke(1.5.dp, FoccaGold), RoundedCornerShape(6.dp))
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${day.day}",
            style = FoccaMicroStyle.copy(fontSize = 10.5.sp),
            color = if (day.isToday) FoccaGold else textColor
        )
    }
}

@Composable
private fun TimelineRow(entry: TimelineEntryUiModel, showLine: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = entry.date,
            style = FoccaMicroStyle.copy(fontSize = 10.sp),
            color = FoccaTextFaint,
            modifier = Modifier.width(44.dp).padding(top = 1.dp)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(8.dp)
                    .background(color = FoccaEmber, shape = CircleShape)
            )
            if (showLine) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(1.5.dp)
                        .weight(1f)
                        .background(com.tamaturgo.focca.presentation.theme.FoccaLine)
                )
            }
        }
        Column(modifier = Modifier.padding(bottom = 12.dp)) {
            Text(text = entry.title, style = FoccaMicroStyle.copy(fontSize = 13.5.sp), color = FoccaText)
            Text(
                text = entry.meta,
                style = FoccaMicroStyle.copy(fontSize = 11.sp),
                color = com.tamaturgo.focca.presentation.theme.FoccaTextMuted,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun HistoryScreenPreview() {
    HistoryScreenContent(state = HistoryUiState())
}
