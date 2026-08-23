package com.tamaturgo.focca.presentation.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.components.FoccaCard
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaGold
import com.tamaturgo.focca.presentation.theme.FoccaLine
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaScreenTitleStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaSurface3
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

@Composable
fun AchievementsScreen(
    modifier: Modifier = Modifier,
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    AchievementsScreenContent(state = uiState, modifier = modifier)
}

@Composable
fun AchievementsScreenContent(state: AchievementsUiState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column {
            Text(text = stringResource(R.string.achievements_title), style = FoccaScreenTitleStyle, color = FoccaText)
            Text(
                text = stringResource(R.string.achievements_unlocked_format, state.unlockedCount, state.totalCount),
                style = FoccaEyebrowStyle,
                color = FoccaTextFaint,
                modifier = Modifier.padding(top = 4.dp)
            )
            val progress = if (state.totalCount > 0) state.unlockedCount.toFloat() / state.totalCount else 0f
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(6.dp)
                    .background(color = FoccaSurface3, shape = RoundedCornerShape(4.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .height(6.dp)
                        .background(color = FoccaEmber, shape = RoundedCornerShape(4.dp))
                )
            }
        }

        FoccaCard(modifier = Modifier.fillMaxWidth().weight(1f), contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.achievements) { achievement ->
                    AchievementItem(achievement)
                }
            }
        }
    }
}

@Composable
private fun AchievementItem(achievement: AchievementUiModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .background(
                    brush = if (achievement.unlocked) {
                        Brush.linearGradient(listOf(FoccaEmber, FoccaGold))
                    } else {
                        Brush.linearGradient(listOf(FoccaSurface2, FoccaSurface2))
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = achievement.icon, fontSize = 21.sp)
        }
        Text(
            text = achievement.label,
            style = FoccaMicroStyle.copy(fontSize = 10.sp, lineHeight = 13.sp),
            color = if (achievement.unlocked) FoccaTextMuted else FoccaTextFaint,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(top = 7.dp)
        )
        achievement.progressLabel?.let {
            Text(text = it, style = FoccaMicroStyle.copy(fontSize = 8.5.sp), color = FoccaTextFaint)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun AchievementsScreenPreview() {
    AchievementsScreenContent(state = AchievementsUiState())
}
