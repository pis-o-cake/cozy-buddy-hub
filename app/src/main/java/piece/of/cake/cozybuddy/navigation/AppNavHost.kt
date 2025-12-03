package piece.of.cake.cozybuddy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import piece.of.cake.cozybuddy.feature.clock.ui.ClockScreen
import piece.of.cake.cozybuddy.feature.home.ui.HomeScreen
import piece.of.cake.cozybuddy.feature.settings.ui.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Clock.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Clock.route) {
            ClockScreen(
                onScreenClick = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToClock = {
                    navController.popBackStack(Screen.Clock.route, inclusive = false)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
