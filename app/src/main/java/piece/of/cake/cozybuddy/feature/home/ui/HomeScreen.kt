package piece.of.cake.cozybuddy.feature.home.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import piece.of.cake.cozybuddy.R
import piece.of.cake.cozybuddy.core.theme.AppColors
import piece.of.cake.cozybuddy.core.theme.AppTheme
import piece.of.cake.cozybuddy.core.theme.AppThemeProvider

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToClock: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {
    val navigateToClock = viewModel.navigateToClock

    LaunchedEffect(Unit) {
        navigateToClock.collect {
            onNavigateToClock()
        }
    }

    BackHandler {
        viewModel.onBackPressed()
    }

    HomeScreenContent(
        onBackClick = { viewModel.onBackPressed() },
        onSettingsClick = onNavigateToSettings
    )
}

@Composable
private fun HomeScreenContent(
    onBackClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val colors = AppTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        // 좌측 상단 - 뒤로가기
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = colors.primaryText
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }

        // 우측 상단 - 설정
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = colors.primaryText
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "Settings",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

// ===== Previews =====

@Preview(
    name = "Home Screen - Dark Theme",
    showBackground = true,
    device = "spec:width=1920dp,height=1200dp,dpi=240"
)
@Composable
private fun PreviewHomeScreenDark() {
    AppThemeProvider(colorScheme = AppColors.dark) {
        HomeScreenContent()
    }
}

@Preview(
    name = "Home Screen - Light Theme",
    showBackground = true,
    device = "spec:width=1920dp,height=1200dp,dpi=240"
)
@Composable
private fun PreviewHomeScreenLight() {
    AppThemeProvider(colorScheme = AppColors.light) {
        HomeScreenContent()
    }
}
