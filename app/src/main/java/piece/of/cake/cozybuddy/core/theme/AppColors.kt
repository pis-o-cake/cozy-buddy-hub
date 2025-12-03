package piece.of.cake.cozybuddy.core.theme

import androidx.compose.ui.graphics.Color

data class AppColorScheme(
    val background: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val accent: Color,
    val surface: Color
)

object AppColors {
    val dark = AppColorScheme(
        background = Color(0xFF1D1D1D),
        primaryText = Color.White,
        secondaryText = Color(0xFFB0B0B0),
        accent = Color(0xFF4A90E2),
        surface = Color(0xFF1A1A1A)
    )

    val light = AppColorScheme(
        background = Color.White,
        primaryText = Color.Black,
        secondaryText = Color(0xFF666666),
        accent = Color(0xFF2196F3),
        surface = Color(0xFFF5F5F5)
    )

    val default = dark

    fun fromOption(option: ThemeOption): AppColorScheme {
        return when (option) {
            ThemeOption.DARK -> dark
            ThemeOption.LIGHT -> light
        }
    }
}

enum class ThemeOption(val key: String, val displayName: String) {
    DARK("dark", "Dark"),
    LIGHT("light", "Light");

    companion object {
        val DEFAULT = DARK
        fun fromKey(key: String) = entries.find { it.key == key } ?: DEFAULT
    }
}
