package com.neversad.paymentapp.feature.pinpad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neversad.paymentapp.core.domain.common.Failure
import com.neversad.paymentapp.core.domain.transaction.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MAX_AMOUNT = "99999999"

object InvalidAmount : Failure("Invalid amount")

data class PinPadState(
    val isLoading: Boolean = false,
    val failure: Failure? = null,
    val amount: String = "",
    val isAmountValid: Boolean = false,
)

sealed interface PinPadAction {
    data class EnterDigit(
        val digit: String,
    ) : PinPadAction

    data object ClearAmount : PinPadAction

    data object Submit : PinPadAction

    data object ClearFailure : PinPadAction
}

sealed interface PinPadEffect {
    data class NavigateToReceipt(
        val transactionId: String,
    ) : PinPadEffect
}

@HiltViewModel
class PinPadViewModel
    @Inject
    constructor(
        private val transactionRepository: TransactionRepository,
    ) : ViewModel() {
        private val _state = MutableStateFlow(PinPadState())
        val state: StateFlow<PinPadState> = _state.asStateFlow()

        private val _effect = MutableSharedFlow<PinPadEffect?>()
        val effect: SharedFlow<PinPadEffect?> = _effect.asSharedFlow()

        fun onAction(action: PinPadAction) {
            when (action) {
                is PinPadAction.EnterDigit -> handleDigitEntered(action.digit)
                is PinPadAction.Submit -> handleSubmit()
                is PinPadAction.ClearFailure -> clearFailure()
                is PinPadAction.ClearAmount -> clearAmount()
            }
        }

        private fun handleDigitEntered(digit: String) {
            viewModelScope.launch {
                _state.update { currentState ->
                    var newAmount = currentState.amount + digit
                    if (newAmount.length > MAX_AMOUNT.length) {
                        newAmount = MAX_AMOUNT
                    }
                    currentState.copy(
                        amount = newAmount,
                        isAmountValid = newAmount.isNotBlank(),
                    )
                }
            }
        }

        private fun clearAmount() {
            viewModelScope.launch {
                _state.update {
                    PinPadState()
                }
            }
        }

        private fun handleSubmit() {
            if (!_state.value.isAmountValid) {
                _state.update { it.copy(failure = InvalidAmount) }
                return
            } else {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, failure = null) }

                    val amount = _state.value.amount
                    transactionRepository
                        .performTransaction(amount)
                        .onSuccess { transaction ->
                            _effect.emit(PinPadEffect.NavigateToReceipt(transaction.id))
                            delay(1000) // wait for transition to complete
                            _state.update { PinPadState() }
                        }.onFailure { failure ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    failure = failure,
                                )
                            }
                        }
                }
            }
        }

        private fun clearFailure() {
            viewModelScope.launch {
                _state.update { it.copy(failure = null) }
            }
        }
    }

val PinPadState.formattedAmount: String
    get() =
        if (amount.length <= 2) {
            "0.${amount.padStart(2, '0')}"
        } else {
            val wholePart = amount.substring(0, amount.length - 2)
            val decimalPart = amount.substring(amount.length - 2)
            val formattedWholePart =
                wholePart
                    .reversed()
                    .chunked(3)
                    .joinToString(",")
                    .reversed()
            "$formattedWholePart.$decimalPart"
        }
