package com.example.expensetracker.expense_feature.presentation.account_screen

import android.text.format.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.expense_feature.domain.model.Account
import com.example.expensetracker.expense_feature.domain.model.Transaction
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetCurrencyUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetAccountsUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetTransactionByAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTransactionByAccountUseCase: GetTransactionByAccountUseCase
): ViewModel() {

    var transaction = MutableStateFlow(mapOf<String, List<Transaction>>())
        private set

    var allAccounts = MutableStateFlow(emptyList<Account>())
        private set

    var selectedCurrencyCode = MutableStateFlow(String())
        private set

    init {
        currencyFormat()
        viewModelScope.launch {
            getAccountsUseCase().collect{ accountDto ->
                val account = accountDto.map { it.toAccount() }
                allAccounts.value = account
            }
        }
    }

    fun getTransaction(accountType: String){
        viewModelScope.launch {
            getTransactionByAccountUseCase(accountType).collect{ allTrx ->
                allTrx.let {
                    val newTrx = it.map { trxDto ->
                        trxDto.toTransaction()
                    }.reversed()
                    transaction.value = newTrx.groupBy { trxDate ->
                        getFormattedDate(trxDate.date)
                    }
                }
            }
        }
    }


    private fun currencyFormat(){
        viewModelScope.launch {
            getCurrencyUseCase().collect { selectedCurrency ->
                selectedCurrencyCode.value = selectedCurrency
            }
        }
    }

    fun getFormattedDate(date: Date): String{
        val dayOfWeek = DateFormat.format("EEE", date)
        val day = DateFormat.format("dd", date)
        val dayOfMonth = DateFormat.format("MMM", date)

        return "$dayOfWeek $day, $dayOfMonth"
    }

}