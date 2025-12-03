package piece.of.cake.cozybuddy.feature.clock.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ClockViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ClockUiState())
    val uiState: StateFlow<ClockUiState> = _uiState.asStateFlow()

    init {
        startClock()
    }

    private fun startClock(intervalMs: Long = 1000L) {
        viewModelScope.launch {
            while (true) {
                delay(intervalMs)
                _uiState.update { it.copy(currentTime = LocalTime.now()) }
            }
        }
    }
}
