package com.example.expensetracker.expense_feature.presentation.home_screen

import androidx.lifecycle.ViewModel
import com.example.expensetracker.core.UiEvent
import com.example.expensetracker.expense_feature.domain.model.Transaction
import com.example.expensetracker.expense_feature.domain.use_cases.GetDateUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.GetFormattedDateUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetCurrencyUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetExpenseLimitUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetLimitDurationUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetLimitKeyUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetAccountUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetAccountsUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetAllTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetCurrentDayExpTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetDailyTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetMonthlyExpTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.read_database.GetWeeklyExpTransactionUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_database.InsertAccountUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_database.InsertNewTransactionUseCase
import com.example.expensetracker.utils.Account
import com.example.expensetracker.utils.Category
import com.example.expensetracker.utils.TabButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDateUseCase: GetDateUseCase,
    private val getFormattedDateUseCase: GetFormattedDateUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getAllTransactionUseCase: GetAllTransactionUseCase,
    private val getCurrentDayExpTransactionUseCase: GetCurrentDayExpTransactionUseCase,
    private val getDailyTransactionUseCase: GetDailyTransactionUseCase,
    private val getWeeklyExpTransactionUseCase: GetWeeklyExpTransactionUseCase,
    private val getMonthlyExpTransactionUseCase: GetMonthlyExpTransactionUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getExpenseLimitUseCase: GetExpenseLimitUseCase,
    private val getLimitDurationUseCase: GetLimitDurationUseCase,
    private val getLimitKeyUseCase: GetLimitKeyUseCase,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val insertNewTransactionUseCase: InsertNewTransactionUseCase
): ViewModel() {

    private var decimal: String = String()
    private var isDecimal = MutableStateFlow(false)
    private var duration = MutableStateFlow(0)

    var tabButton = MutableStateFlow(TabButton.TODAY)
        private set

    var category = MutableStateFlow(Category.FOOD_DRINK)
        private set

    var account = MutableStateFlow(Account.CASH)
        private set

    var transactionAmount = MutableStateFlow("0.00")
        private set

    var dailyTransaction = MutableStateFlow(emptyList<Transaction>())
        private set

    var monthlyTransaction = MutableStateFlow(mapOf<String, List<Transaction>>())
        private set

    var currentExpenseAmount = MutableStateFlow(0.0)
        private set

    var transactionTitle = MutableStateFlow(String())
        private set

    var showInfoBanner = MutableStateFlow(false)
        private set

    var totalIncome = MutableStateFlow(0.0)
        private set

    var totalExpense = MutableStateFlow(0.0)
        private set

    var formattedDate = MutableStateFlow(String())
        private set

    var date = MutableStateFlow(String())
        private set

    var currentTime = MutableStateFlow(Calendar.getInstance().time)
        private set

    var selectedCountryCode = MutableStateFlow(String())
        private set

    var limitAlert = MutableSharedFlow<UiEvent>(replay = 1)
        private set

    var limitKey = MutableStateFlow(String())
        private set


    fun selectTabButton(button: TabButton){
        tabButton.value = button
    }

    fun selectCategory(category: Category){
        this.category.value = category
    }

    fun selectAccount(account: Account){
        this.account.value = account
    }

    fun setTransactionTitle(title: String){
        transactionTitle.value = title
    }

    fun setCurrentTime(time: Date){
        currentTime.value = time
    }

}