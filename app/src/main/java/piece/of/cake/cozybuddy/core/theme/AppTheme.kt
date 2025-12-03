package piece.of.cake.cozybuddy.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily

data class AppThemeData(
    val colorScheme: AppColorScheme,
    val fontFamily: FontFamily
)

private val LocalAppTheme = staticCompositionLocalOf {
    AppThemeData(
        colorScheme = AppColors.default,
        fontFamily = AppTypography.default
    )
}

object AppTheme {
    val colors: AppColorScheme
        @Composable
        get() = LocalAppTheme.current.colorScheme

    val typography: FontFamily
        @Composable
        get() = LocalAppTheme.current.fontFamily
}

@Composable
fun AppThemeProvider(
    colorScheme: AppColorScheme = AppColors.default,
    fontFamily: FontFamily = AppTypography.default,
    content: @Composable () -> Unit
) {
    val theme = AppThemeData(
        colorScheme = colorScheme,
        fontFamily = fontFamily
    )

    CompositionLocalProvider(
        LocalAppTheme provides theme,
        content = content
    )
}
