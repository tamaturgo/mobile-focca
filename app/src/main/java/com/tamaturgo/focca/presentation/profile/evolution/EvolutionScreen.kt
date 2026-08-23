package com.tamaturgo.focca.presentation.profile.evolution

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import com.tamaturgo.focca.presentation.theme.FoccaBg
import com.tamaturgo.focca.presentation.theme.FoccaEyebrowStyle
import com.tamaturgo.focca.presentation.theme.FoccaGold
import com.tamaturgo.focca.presentation.theme.FoccaHeroNumberStyle
import com.tamaturgo.focca.presentation.theme.FoccaLine
import com.tamaturgo.focca.presentation.theme.FoccaMicroStyle
import com.tamaturgo.focca.presentation.theme.FoccaPulse
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaSurface3
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint

data class PersonalRecordUiModel(val name: String, val value: String)

data class EvolutionUiState(
    // TODO(real-data): replace with the real latest weight log.
    val currentWeightLabel: String = "82.4 kg",
    val weightDeltaLabel: String = "▼ 1.8 kg",
    val firstPhotoLabel: String = "AUG 01",
    val latestPhotoLabel: String = "AUG 22",
    // TODO(real-data): replace with the real personal-record list.
    val personalRecords: List<PersonalRecordUiModel> = listOf(
        PersonalRecordUiModel("Bench press", "80 kg"),
        PersonalRecordUiModel("Squat", "100 kg"),
        PersonalRecordUiModel("Leg press", "240 kg")
    )
)

/** Profile → Evolution (PRD screen 11). Standalone in this change. */
@Composable
fun EvolutionScreen(state: EvolutionUiState = EvolutionUiState(), modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        FoccaCard(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Column {
                    Text(text = stringResource(R.string.evolution_current_weight_label), style = FoccaEyebrowStyle, color = FoccaTextFaint)
                    Text(text = state.currentWeightLabel, style = FoccaHeroNumberStyle.copy(fontSize = 30.sp), color = FoccaText)
                }
                Text(text = state.weightDeltaLabel, style = FoccaMicroStyle.copy(fontSize = 12.sp), color = FoccaPulse)
            }
        }

        Column {
            Text(
                text = stringResource(R.string.evolution_photos_label),
                style = FoccaEyebrowStyle,
                color = FoccaTextFaint,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PhotoBox(state.firstPhotoLabel, Modifier.weight(1f))
                PhotoBox(state.latestPhotoLabel, Modifier.weight(1f))
            }
        }

        FoccaCard(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = 16.dp)) {
            Text(
                text = stringResource(R.string.evolution_personal_records_label),
                style = FoccaEyebrowStyle,
                color = FoccaTextFaint,
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
            )
            state.personalRecords.forEachIndexed { index, record ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 9.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = record.name, style = FoccaMicroStyle.copy(fontSize = 13.sp), color = FoccaText)
                    Text(text = record.value, style = FoccaMicroStyle.copy(fontSize = 13.5.sp), color = FoccaGold)
                }
                if (index != state.personalRecords.lastIndex) {
                    HorizontalDivider(color = FoccaLine, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
private fun PhotoBox(label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(3f / 4f)
            .background(
                brush = Brush.linearGradient(listOf(FoccaSurface2, FoccaSurface3)),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(text = label, style = FoccaMicroStyle.copy(fontSize = 10.sp), color = FoccaTextFaint)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun EvolutionScreenPreview() {
    EvolutionScreen()
}
