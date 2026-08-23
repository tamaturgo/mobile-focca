package com.tamaturgo.focca.presentation.profile.statistics

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamaturgo.focca.R
import com.tamaturgo.focca.presentation.components.FoccaCard
import com.tamaturgo.focca.presentation.components.FoccaCardFlat
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaBodyStyle
import com.tamaturgo.focca.presentation.theme.FoccaEmber
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaGold
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaScreenTitleStyle
import com.tamaturgo.focca.presentation.theme.FoccaSurface
import com.tamaturgo.focca.presentation.theme.FoccaSurface3
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint
import com.tamaturgo.focca.presentation.theme.FoccaTextMuted

data class StatBoxUiModel(
    val value: String,
    @StringRes val labelRes: Int
)

data class WeeklyBarUiModel(
    @StringRes val labelRes: Int,
    val heightFraction: Float,
    val highlighted: Boolean = false
)

data class StatisticsUiState(
    @StringRes val monthLabelRes: Int = R.string.month_august,

    val stats: List<StatBoxUiModel> = listOf(
        StatBoxUiModel(
            value = "12",
            labelRes = R.string.statistics_workouts
        ),
        StatBoxUiModel(
            value = "9h42",
            labelRes = R.string.statistics_training_time
        ),
        StatBoxUiModel(
            value = "75%",
            labelRes = R.string.statistics_consistency
        ),
        StatBoxUiModel(
            value = "🔥 7",
            labelRes = R.string.statistics_current_streak
        )
    ),

    val weeklyBars: List<WeeklyBarUiModel> = listOf(
        WeeklyBarUiModel(
            labelRes = R.string.statistics_week_1,
            heightFraction = 0.40f
        ),
        WeeklyBarUiModel(
            labelRes = R.string.statistics_week_2,
            heightFraction = 0.65f
        ),
        WeeklyBarUiModel(
            labelRes = R.string.statistics_week_3,
            heightFraction = 0.90f,
            highlighted = true
        ),
        WeeklyBarUiModel(
            labelRes = R.string.statistics_week_4,
            heightFraction = 0.55f
        )
    ),

    @StringRes val insightTextRes: Int = R.string.statistics_insight
)

/** Profile → Statistics (PRD screen 12). Standalone in this change. */
@Composable
fun StatisticsScreen(
    state: StatisticsUiState = StatisticsUiState(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = stringResource(state.monthLabelRes),
            style = FoccaScreenTitleStyle,
            color = FoccaText
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            state.stats.chunked(2).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    row.forEach { stat ->
                        StatBox(
                            stat = stat,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        FoccaCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.statistics_workouts_per_week),
                style = FoccaEyebrowStyle,
                color = FoccaTextFaint,
                modifier = Modifier.padding(bottom = 14.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.weeklyBars.forEach { bar ->
                    WeeklyBar(
                        bar = bar,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        FoccaCardFlat(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(state.insightTextRes),
                style = FoccaBodyStyle.copy(fontSize = 12.sp),
                color = FoccaTextMuted
            )
        }
    }
}

@Composable
private fun StatBox(
    stat: StatBoxUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = FoccaSurface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = stat.value,
            style = FoccaScreenTitleStyle.copy(fontSize = 21.sp),
            color = FoccaText
        )

        Text(
            text = stringResource(stat.labelRes),
            style = FoccaMicroStyle.copy(fontSize = 10.5.sp),
            color = FoccaTextFaint,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
private fun WeeklyBar(
    bar: WeeklyBarUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(
                    bar.heightFraction.coerceIn(0f, 1f)
                )
                .background(
                    brush = if (bar.highlighted) {
                        Brush.verticalGradient(
                            listOf(
                                FoccaGold,
                                FoccaEmber
                            )
                        )
                    } else {
                        Brush.verticalGradient(
                            listOf(
                                FoccaSurface3,
                                FoccaSurface3
                            )
                        )
                    },
                    shape = RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp,
                        bottomStart = 2.dp,
                        bottomEnd = 2.dp
                    )
                )
        )

        Text(
            text = stringResource(bar.labelRes),
            style = FoccaMicroStyle.copy(fontSize = 9.sp),
            color = FoccaTextFaint,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F1115
)
@Composable
private fun StatisticsScreenPreview() {
    StatisticsScreen()
}