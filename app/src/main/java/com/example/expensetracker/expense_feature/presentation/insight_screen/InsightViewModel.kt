package com.example.expensetracker.expense_feature.presentation.insight_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.expense_feature.domain.model.Transaction
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetCurrencyUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.Get14DayTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.Get3DayTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.Get7DayTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetAllTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetLastMonthTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetStartOfMonthTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetTransactionByTypeUseCase
import com.example.expensetracker.utils.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val get3DayTransactionUseCase: Get3DayTransactionUseCase,
    private val get7DayTransactionUseCase: Get7DayTransactionUseCase,
    private val get14DayTransactionUseCase: Get14DayTransactionUseCase,
    private val getStartOfMonthTransactionUseCase: GetStartOfMonthTransactionUseCase,
    private val getLastMonthTransactionUseCase: GetLastMonthTransactionUseCase,
    private val getTransactionByTypeUseCase: GetTransactionByTypeUseCase
) : ViewModel() {

    private var _tabButton = MutableStateFlow(TransactionType.INCOME)
    val tabButton: StateFlow<TransactionType> = _tabButton

    private var _filterTransaction = MutableStateFlow(emptyList<Transaction>())
    val filterTransaction: StateFlow<List<Transaction>> = _filterTransaction

    var selectedCurrencyCode = MutableStateFlow(String())
        private set

    fun selectTabButton(tab: TransactionType){
        _tabButton.value = tab
        getFilteredTransaction()
    }


    init {
        getFilteredTransaction()
        currencyFormat()
    }

    private fun currencyFormat(){
        viewModelScope.launch {
            getCurrencyUseCase().collect{ selectCurrency ->
                selectedCurrencyCode.value = selectCurrency
            }
        }
    }

    fun getFilteredTransaction(duration: Int = 5){
        viewModelScope.launch {
            if (_tabButton.value == TransactionType.INCOME)
                filterTransaction(duration, TransactionType.INCOME.title)
            else filterTransaction(duration, TransactionType.EXPENSE.title)
        }
    }

    private suspend fun filterTransaction(
        duration: Int,
        transactionType: String = TransactionType.INCOME.title
    ){
        when(duration){
            0 -> {
                get3DayTransactionUseCase(transactionType).collectLatest{ result ->
                    _filterTransaction.value = result.map {
                        it.toTransaction()
                    }
                }
            }
            1 -> {
                get7DayTransactionUseCase(transactionType).collectLatest{ result ->
                    _filterTransaction.value = result.map {
                        it.toTransaction()
                    }
                }
            }
            2 -> {
                get14DayTransactionUseCase(transactionType).collectLatest{ result ->
                    _filterTransaction.value = result.map {
                        it.toTransaction()
                    }
                }
            }
            3 -> {
                getStartOfMonthTransactionUseCase(transactionType).collectLatest{ result ->
                    _filterTransaction.value = result.map {
                        it.toTransaction()
                    }
                }
            }
            4 -> {
                getLastMonthTransactionUseCase(transactionType).collectLatest { result ->
                    _filterTransaction.value = result.map {
                        it.toTransaction()
                    }
                }
            }
            5 -> {
                getTransactionByTypeUseCase(transactionType).collectLatest { result ->
                    _filterTransaction.value = result.map {
                        it.toTransaction()
                    }
                }
            }
        }
    }

}