package piece.of.cake.cozybuddy.feature.settings.ui

import piece.of.cake.cozybuddy.core.theme.FontOption
import piece.of.cake.cozybuddy.core.theme.ThemeOption

data class SettingsUiState(
    val selectedFont: FontOption = FontOption.DEFAULT,
    val selectedTheme: ThemeOption = ThemeOption.DEFAULT,
    val fontOptions: List<FontOption> = FontOption.entries,
    val themeOptions: List<ThemeOption> = ThemeOption.entries
)
