package piece.of.cake.cozybuddy

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import piece.of.cake.cozybuddy.core.datastore.SettingsDataStore
import piece.of.cake.cozybuddy.core.theme.AppColors
import piece.of.cake.cozybuddy.core.theme.AppThemeProvider
import piece.of.cake.cozybuddy.core.theme.AppTypography
import piece.of.cake.cozybuddy.core.theme.FontOption
import piece.of.cake.cozybuddy.core.theme.ThemeOption
import piece.of.cake.cozybuddy.navigation.AppNavHost
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOrientation()
        setupFullScreen()
        setContent {
            App(settingsDataStore = settingsDataStore)
        }
    }

    private fun setupOrientation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) return
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun setupFullScreen() {
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

@Composable
private fun App(settingsDataStore: SettingsDataStore) {
    val navController = rememberNavController()

    val fontKey by settingsDataStore.font.collectAsStateWithLifecycle(initialValue = FontOption.DEFAULT.key)
    val themeKey by settingsDataStore.theme.collectAsStateWithLifecycle(initialValue = ThemeOption.DEFAULT.key)

    val fontFamily = AppTypography.fromOption(FontOption.fromKey(fontKey))
    val colorScheme = AppColors.fromOption(ThemeOption.fromKey(themeKey))

    MaterialTheme {
        AppThemeProvider(
            colorScheme = colorScheme,
            fontFamily = fontFamily
        ) {
            AppNavHost(navController = navController)
        }
    }
}
