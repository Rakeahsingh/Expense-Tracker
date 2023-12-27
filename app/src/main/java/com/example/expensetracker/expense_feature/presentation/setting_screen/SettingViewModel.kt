package com.example.expensetracker.expense_feature.presentation.setting_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.expense_feature.data.local.entity.AccountDto
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetCurrencyUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetExpenseLimitUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetLimitDurationUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetLimitKeyUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_database.EraseTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_database.InsertAccountUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_datastore.EditExpenseLimitUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_datastore.EditLimitDurationUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_datastore.EditLimitKeyUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_datastore.EraseDataStoreUseCase
import com.example.expensetracker.utils.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val eraseTransactionUseCase: EraseTransactionUseCase,
    private val getExpenseLimitUseCase: GetExpenseLimitUseCase,
    private val editExpenseLimitUseCase: EditExpenseLimitUseCase,
    private val getLimitKeyUseCase: GetLimitKeyUseCase,
    private val editLimitKeyUseCase: EditLimitKeyUseCase,
    private val getLimitDurationUseCase: GetLimitDurationUseCase,
    private val editLimitDurationUseCase: EditLimitDurationUseCase,
    private val eraseDataStoreUseCase: EraseDataStoreUseCase
): ViewModel() {

    var currency = MutableStateFlow(String())
        private set

    var expenseLimit = MutableStateFlow(0.0)
        private set

    var expenseLimitDuration = MutableStateFlow(0)
        private set

    var reminderLimit = MutableStateFlow(false)
        private set


    init {
        viewModelScope.launch {
            getCurrencyUseCase().collectLatest {
                currency.value = it
            }
        }

        viewModelScope.launch {
            getExpenseLimitUseCase().collectLatest {
                expenseLimit.value = it
            }
        }

        viewModelScope.launch {
            getLimitDurationUseCase().collectLatest {
                expenseLimitDuration.value = it
            }
        }

        viewModelScope.launch {
            getLimitKeyUseCase().collectLatest {
                reminderLimit.value = it
            }
        }
    }


    fun eraseTransaction(){
        viewModelScope.launch {
            insertAccountUseCase(listOf(
                AccountDto(1, Account.CASH.title, 0.0, 0.0, 0.0),
                AccountDto(1, Account.CARD.title, 0.0, 0.0, 0.0),
                AccountDto(1, Account.BANK.title, 0.0, 0.0, 0.0)
            ))
            // erase all transaction
            eraseTransactionUseCase()

            // erase datastore
            eraseDataStoreUseCase()
        }
    }

    fun editLimitKey(enabled: Boolean){
        viewModelScope.launch {
            editLimitKeyUseCase(enabled)
        }
    }

    fun editExpenseLimit(amount: Double){
        viewModelScope.launch {
            editExpenseLimitUseCase(amount)
        }
    }

    fun editLimitDuration(duration: Int){
        viewModelScope.launch {
            editLimitDurationUseCase(duration)
        }
    }


}