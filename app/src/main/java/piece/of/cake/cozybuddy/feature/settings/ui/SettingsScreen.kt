package piece.of.cake.cozybuddy.feature.settings.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import piece.of.cake.cozybuddy.R
import piece.of.cake.cozybuddy.core.theme.AppColors
import piece.of.cake.cozybuddy.core.theme.AppTheme
import piece.of.cake.cozybuddy.core.theme.AppThemeProvider
import piece.of.cake.cozybuddy.core.theme.FontOption
import piece.of.cake.cozybuddy.core.theme.ThemeOption

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        onNavigateBack()
    }

    SettingsScreenContent(
        uiState = uiState,
        onBackClick = onNavigateBack,
        onFontSelected = viewModel::onFontSelected,
        onThemeSelected = viewModel::onThemeSelected
    )
}

@Composable
private fun SettingsScreenContent(
    uiState: SettingsUiState,
    onBackClick: () -> Unit = {},
    onFontSelected: (FontOption) -> Unit = {},
    onThemeSelected: (ThemeOption) -> Unit = {}
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

        // 설정 내용
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 80.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Settings",
                fontSize = 32.sp,
                color = colors.primaryText
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingsDropdown(
                label = "Font",
                selectedValue = uiState.selectedFont.displayName,
                options = uiState.fontOptions.map { it.displayName },
                onOptionSelected = { displayName ->
                    val font = uiState.fontOptions.find { it.displayName == displayName }
                    font?.let { onFontSelected(it) }
                }
            )

            SettingsDropdown(
                label = "Theme",
                selectedValue = uiState.selectedTheme.displayName,
                options = uiState.themeOptions.map { it.displayName },
                onOptionSelected = { displayName ->
                    val theme = uiState.themeOptions.find { it.displayName == displayName }
                    theme?.let { onThemeSelected(it) }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDropdown(
    label: String,
    selectedValue: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    val colors = AppTheme.colors
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = colors.secondaryText
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .width(200.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colors.primaryText,
                    unfocusedTextColor = colors.primaryText,
                    focusedBorderColor = colors.accent,
                    unfocusedBorderColor = colors.secondaryText
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(colors.surface)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                color = colors.primaryText
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// ===== Previews =====

@Preview(
    name = "Settings Screen - Dark Theme",
    showBackground = true,
    device = "spec:width=1920dp,height=1200dp,dpi=240"
)
@Composable
private fun PreviewSettingsScreenDark() {
    AppThemeProvider(colorScheme = AppColors.dark) {
        SettingsScreenContent(
            uiState = SettingsUiState()
        )
    }
}

@Preview(
    name = "Settings Screen - Light Theme",
    showBackground = true,
    device = "spec:width=1920dp,height=1200dp,dpi=240"
)
@Composable
private fun PreviewSettingsScreenLight() {
    AppThemeProvider(colorScheme = AppColors.light) {
        SettingsScreenContent(
            uiState = SettingsUiState()
        )
    }
}
