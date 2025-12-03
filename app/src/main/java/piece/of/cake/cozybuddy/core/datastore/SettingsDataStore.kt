package piece.of.cake.cozybuddy.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import piece.of.cake.cozybuddy.core.theme.FontOption
import piece.of.cake.cozybuddy.core.theme.ThemeOption
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore @Inject constructor(
    private val context: Context
) {
    private object Keys {
        val FONT = stringPreferencesKey("font")
        val THEME = stringPreferencesKey("theme")
    }

    val font: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[Keys.FONT] ?: FontOption.DEFAULT.key
    }

    val theme: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[Keys.THEME] ?: ThemeOption.DEFAULT.key
    }

    suspend fun setFont(font: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.FONT] = font
        }
    }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.THEME] = theme
        }
    }
}
