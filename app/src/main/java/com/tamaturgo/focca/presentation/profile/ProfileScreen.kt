package com.tamaturgo.focca.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.tamaturgo.focca.presentation.theme.FoccaSurface
import com.tamaturgo.focca.presentation.theme.FoccaSurface2
import com.tamaturgo.focca.presentation.theme.FoccaText
import com.tamaturgo.focca.presentation.theme.FoccaTextFaint

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onMenuActionClick: (ProfileMenuAction) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    ProfileScreenContent(state = uiState, modifier = modifier, onMenuActionClick = onMenuActionClick)
}

@Composable
fun ProfileScreenContent(
    state: ProfileUiState,
    modifier: Modifier = Modifier,
    onMenuActionClick: (ProfileMenuAction) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FoccaBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(FoccaEmber, FoccaGold)),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.name.take(1), color = Color(0xFF1A0E08), style = FoccaScreenTitleStyle.copy(fontSize = 19.sp))
            }
            Column {
                Text(text = state.name, style = FoccaScreenTitleStyle.copy(fontSize = 17.sp), color = FoccaText)
                Text(text = state.memberSince, style = FoccaEyebrowStyle, color = FoccaTextFaint, modifier = Modifier.padding(top = 2.dp))
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatBox(value = "${state.totalWorkouts}", label = stringResource(R.string.profile_total_workouts_label), modifier = Modifier.weight(1f))
            StatBox(value = "🔥 ${state.currentStreak}", label = stringResource(R.string.label_current_streak), modifier = Modifier.weight(1f))
        }

        FoccaCard(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = 16.dp)) {
            state.menuActions.forEachIndexed { index, action ->
                ProfileMenuRow(action, onClick = { onMenuActionClick(action) })
                if (index != state.menuActions.lastIndex) {
                    HorizontalDivider(color = FoccaLine, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
private fun StatBox(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(color = FoccaSurface, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(text = value, style = FoccaScreenTitleStyle.copy(fontSize = 21.sp), color = FoccaText)
        Text(text = label, style = FoccaMicroStyle.copy(fontSize = 10.5.sp), color = FoccaTextFaint, modifier = Modifier.padding(top = 2.dp))
    }
}

@Composable
private fun ProfileMenuRow(action: ProfileMenuAction, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 13.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(color = FoccaSurface2, shape = RoundedCornerShape(9.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = action.icon, fontSize = 14.sp)
            }
            Text(text = stringResource(action.labelRes), style = FoccaMicroStyle.copy(fontSize = 13.sp), color = FoccaText)
        }
        Text(text = "›", style = FoccaMicroStyle, color = FoccaTextFaint)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent(state = ProfileUiState())
}
