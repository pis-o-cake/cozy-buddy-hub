package piece.of.cake.cozybuddy.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _navigateToClock = MutableSharedFlow<Unit>()
    val navigateToClock: SharedFlow<Unit> = _navigateToClock.asSharedFlow()

    private var timeoutJob: Job? = null

    init {
        startTimeout()
    }

    fun resetTimeout() {
        startTimeout()
    }

    fun onBackPressed() {
        viewModelScope.launch {
            _navigateToClock.emit(Unit)
        }
    }

    private fun startTimeout(timeoutMs: Long = 10_000L) {
        timeoutJob?.cancel()
        timeoutJob = viewModelScope.launch {
            delay(timeoutMs)
            _navigateToClock.emit(Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timeoutJob?.cancel()
    }
}
