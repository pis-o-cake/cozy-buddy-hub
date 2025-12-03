package piece.of.cake.cozybuddy.feature.clock.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import piece.of.cake.cozybuddy.core.theme.AppColors
import piece.of.cake.cozybuddy.core.theme.AppTheme
import piece.of.cake.cozybuddy.core.theme.AppThemeProvider
import piece.of.cake.cozybuddy.core.theme.AppTypography
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.min

@Composable
fun ClockScreen(
    viewModel: ClockViewModel = hiltViewModel(),
    onScreenClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ClockScreenContent(
        currentTime = uiState.currentTime,
        fontSizeRatio = uiState.fontSizeRatio,
        colonMinAlpha = uiState.colonMinAlpha,
        onScreenClick = onScreenClick
    )
}

@Composable
private fun ClockScreenContent(
    currentTime: LocalTime,
    fontSizeRatio: Float,
    colonMinAlpha: Float,
    onScreenClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val colors = AppTheme.colors

    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val minScreenSize = min(screenWidthDp.value, screenHeightDp.value).dp

    val fontSize = with(density) {
        (minScreenSize * fontSizeRatio).toSp()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onScreenClick
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedDigitalClock(
            time = currentTime,
            fontSize = fontSize,
            colonMinAlpha = colonMinAlpha
        )
    }
}

@Composable
private fun AnimatedDigitalClock(
    time: LocalTime,
    fontSize: TextUnit,
    colonMinAlpha: Float,
    letterSpacingRatio: Float = 0.067f,
    colonPaddingRatio: Float = 0.067f
) {
    val hour = time.format(DateTimeFormatter.ofPattern("HH"))
    val minute = time.format(DateTimeFormatter.ofPattern("mm"))

    val letterSpacing = fontSize * letterSpacingRatio
    val colonPadding = fontSize.value.dp * colonPaddingRatio

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedTimeUnit(
            value = hour,
            fontSize = fontSize,
            letterSpacing = letterSpacing
        )

        BlinkingColon(
            fontSize = fontSize,
            padding = colonPadding,
            minAlpha = colonMinAlpha
        )

        AnimatedTimeUnit(
            value = minute,
            fontSize = fontSize,
            letterSpacing = letterSpacing
        )
    }
}

@Composable
private fun AnimatedTimeUnit(
    value: String,
    fontSize: TextUnit,
    letterSpacing: TextUnit,
    animationDurationMs: Int = 300
) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography

    AnimatedContent(
        targetState = value,
        transitionSpec = {
            (slideInVertically { it } + fadeIn(
                animationSpec = tween(animationDurationMs)
            )).togetherWith(
                slideOutVertically { -it } + fadeOut(
                    animationSpec = tween(animationDurationMs)
                )
            ).using(
                SizeTransform(clip = false)
            )
        },
        label = "time_animation"
    ) { targetValue ->
        Text(
            text = targetValue,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = typography,
            color = colors.primaryText,
            letterSpacing = letterSpacing
        )
    }
}

@Composable
private fun BlinkingColon(
    fontSize: TextUnit,
    padding: Dp,
    minAlpha: Float,
    blinkDurationMs: Int = 3000
) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val infiniteTransition = rememberInfiniteTransition(label = "colon_blink")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = minAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(blinkDurationMs, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Text(
        text = ":",
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        fontFamily = typography,
        color = colors.primaryText.copy(alpha = alpha),
        modifier = Modifier.padding(horizontal = padding)
    )
}

// ===== Previews =====

@Preview(
    name = "Dark Theme - Jua Font",
    showBackground = true,
    device = "spec:width=1920dp,height=1200dp,dpi=240"
)
@Composable
private fun PreviewDarkTheme() {
    AppThemeProvider(
        colorScheme = AppColors.dark,
        fontFamily = AppTypography.jua
    ) {
        ClockScreenContent(
            currentTime = LocalTime.of(14, 30),
            fontSizeRatio = 0.45f,
            colonMinAlpha = 0.3f
        )
    }
}

@Preview(
    name = "Dark Theme - Pretendard Font",
    showBackground = true,
    device = "spec:width=1920dp,height=1200dp,dpi=240"
)
@Composable
private fun PreviewDarkThemePretendard() {
    AppThemeProvider(
        colorScheme = AppColors.dark,
        fontFamily = AppTypography.pretendard
    ) {
        ClockScreenContent(
            currentTime = LocalTime.of(14, 30),
            fontSizeRatio = 0.45f,
            colonMinAlpha = 0.3f
        )
    }
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    device = "spec:width=1920dp,height=1200dp,dpi=240"
)
@Composable
private fun PreviewLightTheme() {
    AppThemeProvider(colorScheme = AppColors.light) {
        ClockScreenContent(
            currentTime = LocalTime.of(14, 30),
            fontSizeRatio = 0.45f,
            colonMinAlpha = 0.3f
        )
    }
}
