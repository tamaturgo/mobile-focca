package com.tamaturgo.focca.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.tamaturgo.focca.R

// PRD section 7.3 type roles, backed by the bundled variable font files under
// res/font/. Each family is loaded as a single Font entry at its default
// instance (no FontVariation.Settings — that API crashed typeface resolution
// for these variable files in both @Preview and on-device rendering); Compose
// synthesizes bold/semibold weights on top of it when a TextStyle asks for one,
// which is visually close enough to the PRD scale for this design pass.
val SpaceGroteskFamily = FontFamily(Font(R.font.space_grotesk))
val InterFamily = FontFamily(Font(R.font.inter))
val JetBrainsMonoFamily = FontFamily(Font(R.font.jetbrains_mono))

// PRD section 7.3 approximate scale (mobile). Not forced into Material's
// Typography slot names since the scale doesn't map cleanly onto them.
val FoccaHeroNumberStyle = TextStyle(
    fontFamily = SpaceGroteskFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 40.sp,
    lineHeight = 44.sp
)

val FoccaScreenTitleStyle = TextStyle(
    fontFamily = SpaceGroteskFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 21.sp,
    lineHeight = 26.sp,
    letterSpacing = (-0.01).em
)

val FoccaCardNameStyle = TextStyle(
    fontFamily = SpaceGroteskFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 14.5.sp,
    lineHeight = 19.sp
)

val FoccaBodyStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 18.sp
)

val FoccaBodyEmphasisStyle = FoccaBodyStyle.copy(fontWeight = FontWeight.SemiBold)

val FoccaEyebrowStyle = TextStyle(
    fontFamily = JetBrainsMonoFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 14.sp,
    letterSpacing = 0.1.em
)

val FoccaMicroStyle = TextStyle(
    fontFamily = JetBrainsMonoFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 9.5.sp,
    lineHeight = 12.sp,
    letterSpacing = 0.04.em
)

val FoccaButtonLabelStyle = TextStyle(
    fontFamily = SpaceGroteskFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 14.5.sp,
    letterSpacing = 0.01.em,
    textAlign = TextAlign.Center
)

// Material typography slots that already fit the PRD scale reuse Focca's
// styles; every other slot keeps the Material 3 default.
val Typography = Typography(
    bodyLarge = FoccaBodyStyle,
    titleLarge = FoccaScreenTitleStyle,
    labelSmall = FoccaEyebrowStyle
)
