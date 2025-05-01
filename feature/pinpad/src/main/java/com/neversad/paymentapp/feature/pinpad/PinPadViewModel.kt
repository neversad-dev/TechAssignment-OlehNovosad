package com.neversad.paymentapp.feature.pinpad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neversad.paymentapp.core.domain.TransactionRepository
import com.neversad.paymentapp.core.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PinPadState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val amount: String = "",
    val isAmountValid: Boolean = false,
    val transaction: Transaction? = null
)

sealed interface PinPadAction {
    data class EnterDigit(val digit: String) : PinPadAction
    data object Submit : PinPadAction
}

sealed interface PinPadEffect {
    data object NavigateToReceipt : PinPadEffect
}

@HiltViewModel
class PinPadViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PinPadState())
    val state: StateFlow<PinPadState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PinPadEffect?>()
    val effect: SharedFlow<PinPadEffect?> = _effect.asSharedFlow()

    fun onAction(action: PinPadAction) {
        when (action) {
            is PinPadAction.EnterDigit -> handlePinDigitEntered(action.digit)
            is PinPadAction.Submit -> handlePinSubmitted()
        }
    }

    private fun handlePinDigitEntered(digit: String) {
        viewModelScope.launch {
            _state.update { currentState ->
                val newAmount = currentState.amount + digit
                currentState.copy(
                    amount = newAmount,
                    isAmountValid = newAmount.isNotBlank()
                )
            }
        }
    }

    private fun handlePinSubmitted() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            try {
                val amount = _state.value.amount.toDoubleOrNull() ?: 0.0
                val transaction = transactionRepository.performTransaction(amount)
                
                _state.update { it.copy(
                    isLoading = false,
                    transaction = transaction
                ) }
                
                _effect.emit(PinPadEffect.NavigateToReceipt)
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                ) }
            }
        }
    }
}