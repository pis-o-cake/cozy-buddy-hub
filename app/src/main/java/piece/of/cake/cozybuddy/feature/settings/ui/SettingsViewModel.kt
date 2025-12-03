package piece.of.cake.cozybuddy.feature.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import piece.of.cake.cozybuddy.core.datastore.SettingsDataStore
import piece.of.cake.cozybuddy.core.theme.FontOption
import piece.of.cake.cozybuddy.core.theme.ThemeOption
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        observeSettings()
    }

    private fun observeSettings() {
        combine(
            settingsDataStore.font,
            settingsDataStore.theme
        ) { fontKey, themeKey ->
            Pair(FontOption.fromKey(fontKey), ThemeOption.fromKey(themeKey))
        }.onEach { (font, theme) ->
            _uiState.update {
                it.copy(selectedFont = font, selectedTheme = theme)
            }
        }.launchIn(viewModelScope)
    }

    fun onFontSelected(font: FontOption) {
        viewModelScope.launch {
            settingsDataStore.setFont(font.key)
        }
    }

    fun onThemeSelected(theme: ThemeOption) {
        viewModelScope.launch {
            settingsDataStore.setTheme(theme.key)
        }
    }
}
