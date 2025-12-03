package piece.of.cake.cozybuddy

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import piece.of.cake.cozybuddy.core.ui.theme.AppColors
import piece.of.cake.cozybuddy.core.ui.theme.AppThemeProvider
import piece.of.cake.cozybuddy.core.ui.theme.AppTypography
import piece.of.cake.cozybuddy.feature.home.ui.HomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOrientation()
        setContent { App() }
    }

    private fun setupOrientation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) return
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}

@Composable
private fun App() {
    MaterialTheme {
        AppThemeProvider(
            colorScheme = AppColors.dark,
            fontFamily = AppTypography.pretendard
        ) {
            HomeScreen()
        }
    }
}
