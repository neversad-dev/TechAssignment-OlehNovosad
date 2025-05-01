package com.neversad.paymentapp.feature.pinpad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PinPadState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val pin: String = "",
    val isPinValid: Boolean = false
)

sealed interface PinPadEvent {
    data class OnPinDigitEntered(val digit: String) : PinPadEvent
    object OnPinSubmitted : PinPadEvent
    object OnClearPin : PinPadEvent
}

sealed interface PinPadEffect {
    object NavigateToReceipt : PinPadEffect
}

@HiltViewModel
class PinPadViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(PinPadState())
    val state: StateFlow<PinPadState> = _state.asStateFlow()

    private val _effect = MutableStateFlow<PinPadEffect?>(null)
    val effect: StateFlow<PinPadEffect?> = _effect.asStateFlow()

    fun onEvent(event: PinPadEvent) {
        when (event) {
            is PinPadEvent.OnPinDigitEntered -> handlePinDigitEntered(event.digit)
            is PinPadEvent.OnPinSubmitted -> handlePinSubmitted()
            is PinPadEvent.OnClearPin -> handleClearPin()
        }
    }

    private fun handlePinDigitEntered(digit: String) {
        viewModelScope.launch {
            _state.update { currentState ->
                val newPin = currentState.pin + digit
                currentState.copy(
                    pin = newPin,
                    isPinValid = newPin.length == 4
                )
            }
        }
    }

    private fun handlePinSubmitted() {
        viewModelScope.launch {
            if (_state.value.isPinValid) {
                _effect.value = PinPadEffect.NavigateToReceipt
            }
        }
    }

    private fun handleClearPin() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    pin = "",
                    isPinValid = false
                )
            }
        }
    }
} 