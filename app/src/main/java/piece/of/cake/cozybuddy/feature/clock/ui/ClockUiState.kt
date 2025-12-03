package piece.of.cake.cozybuddy.feature.clock.ui

import java.time.LocalTime

data class ClockUiState(
    val currentTime: LocalTime = LocalTime.now(),
    val fontSizeRatio: Float = 0.45f,
    val colonMinAlpha: Float = 0.3f
)
