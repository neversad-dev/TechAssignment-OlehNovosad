package com.neversad.paymentapp.feature.receipt

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.neversad.paymentapp.core.domain.common.Failure
import com.neversad.paymentapp.core.domain.transaction.TransactionRepository
import com.neversad.paymentapp.core.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ReceiptState {
    data object Loading : ReceiptState
    data class Success(val transaction: Transaction) : ReceiptState
    data class Error(val failure: Failure) : ReceiptState
}

sealed interface ReceiptAction {
    data object NavigateBack : ReceiptAction
}


@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val transactionId = savedStateHandle.toRoute<Receipt>().transactionId
    private var getTransactionJob: Job? = null

    private val _state: MutableStateFlow<ReceiptState> =
        MutableStateFlow(ReceiptState.Loading)
    val state = _state
        .onStart {
            getTransaction()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private fun getTransaction() {
        getTransactionJob?.cancel()
        getTransactionJob = viewModelScope.launch {
            transactionRepository.getTransaction(transactionId)
                .onSuccess { transaction ->
                    _state.update {
                        ReceiptState.Success(transaction)
                    }
                }
                .onFailure { failure ->
                    _state.update {
                        ReceiptState.Error(failure)
                    }
                }
        }

    }

}