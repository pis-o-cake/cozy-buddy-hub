package piece.of.cake.cozybuddy.navigation

sealed class Screen(val route: String) {
    data object Clock : Screen("clock")
    data object Home : Screen("home")
    data object Settings : Screen("settings")
}