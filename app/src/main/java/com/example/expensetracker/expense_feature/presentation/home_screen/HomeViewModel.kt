package com.example.expensetracker.expense_feature.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.core.Constant
import com.example.expensetracker.core.UiEvent
import com.example.expensetracker.expense_feature.data.local.entity.TransactionDto
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
import com.example.expensetracker.utils.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
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

    var selectedCurrencyCode = MutableStateFlow(String())
        private set

    var limitAlert = MutableSharedFlow<UiEvent>(replay = 1)
        private set

    var limitKey = MutableStateFlow(false)
        private set

    init {
        val currentDate = getDateUseCase()
        formattedDate.value = getFormattedDateUseCase(currentTime.value)
        date.value = currentDate
        currencyFormat()

        viewModelScope.launch {
            getLimitDurationUseCase().collect{
                duration.value = it
            }
        }

        viewModelScope.launch {
            getLimitKeyUseCase().collectLatest {
                limitKey.value = it
            }
        }

        viewModelScope.launch {
            when(duration.value){
                0 -> {
                    getCurrentDayExpTransactionUseCase().collect{
                        val trx = it.map {
                            it.toTransaction()
                        }
                        currentExpenseAmount.value = calculateTransaction(trx.map { it.amount })
                    }
                }
                1 -> {
                    getWeeklyExpTransactionUseCase().collect{
                        val trx = it.map {
                            it.toTransaction()
                        }
                        currentExpenseAmount.value = calculateTransaction(trx.map { it.amount })
                    }
                }
                else -> {
                    getMonthlyExpTransactionUseCase().collect{
                        val trx = it.map { it.toTransaction() }
                        currentExpenseAmount.value = calculateTransaction(trx.map { it.amount })
                    }
                }
            }
        }

        viewModelScope.launch {
            getDailyTransactionUseCase(currentDate).collect{
                it.let { expense ->
                    dailyTransaction.value = expense.map { dailyExpense ->
                        dailyExpense.toTransaction()
                    }.reversed()
                }
            }
        }

        viewModelScope.launch {
            getAllTransactionUseCase().collect{
                it.let {
                    val allSortedTrx = it.map {
                        it.toTransaction()
                    }.reversed()
                    monthlyTransaction.value = allSortedTrx.groupBy { monthlyExpense ->
                        getFormattedDateUseCase(monthlyExpense.date)
                    }
                }
            }
        }

        viewModelScope.launch {
            getAccountsUseCase().collect{
                val accounts = it.map { it.toAccount() }
                val income = calculateTransaction(accounts.map { it.income })
                val expense = calculateTransaction(accounts.map { it.expense })

                totalIncome.value = income
                totalExpense.value = expense
            }
        }


    }

    private fun calculateTransaction(transaction: List<Double>): Double{
        return transaction.sumOf {
            it
        }
    }

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

    fun insertDailyTransaction(
        date: String,
        amount: Double,
        category: String,
        transactionType: String,
        transactionTitle: String,
        navigateBack: () -> Unit
    ){
        viewModelScope.launch {
            if (amount <= 0.0){
                showInfoBanner.value = true
                delay(2000)
                showInfoBanner.value = false
                return@launch
            }

            val newTransaction = TransactionDto(
                currentTime.value,
                date,
                account.value.title,
                amount,
                category,
                transactionType,
                transactionTitle
            )
            insertNewTransactionUseCase(newTransaction)

            if (transactionType == Constant.INCOME){
                val currentAccount = getAccountUseCase(account.value.title).first()
                val newIncomeAmount = currentAccount.income + amount
                val balance = newIncomeAmount - currentAccount.balance

                currentAccount.income = newIncomeAmount
                currentAccount.balance = balance
                insertAccountUseCase(listOf(currentAccount))
            }else{
                val currentAccount = getAccountUseCase(account.value.title).first()
                val newExpenseAmount = currentAccount.expense + amount
                val balance = currentAccount.income - newExpenseAmount

                currentAccount.expense = newExpenseAmount
                currentAccount.balance = balance
                insertAccountUseCase(listOf(currentAccount))
            }
            withContext(Main){
                navigateBack()
            }
        }
    }


    fun setTransaction(amount: String){
        val value = transactionAmount.value
        val whole = value.substring(0, value.indexOf("."))

        if (amount == "."){
            isDecimal.value = true
            return
        }

        if (isDecimal.value){
            if (decimal.length == 2){
               decimal = decimal.substring(0, decimal.length - 1) + amount
            }else{
                decimal += amount
            }
            val newDecimal = decimal.toDouble() / 100.0
            transactionAmount.value = String.format("%.2f", whole.toDouble() + newDecimal)
            return
        }

        if (whole == "0"){
            transactionAmount.value = String.format("%.2f", amount.toDouble())
        }else{
            transactionAmount.value = String.format("%.2f", (whole + amount).toDouble())
        }
    }

    fun backSpace(){
        val value = transactionAmount.value
        var whole = value.substring(0, value.indexOf("."))

        if (value == "0.00"){
            return
        }

        if (isDecimal.value){
            decimal = if (decimal.length == 2){
                decimal.substring(0, decimal.length - 1)
            }else{
                isDecimal.value = false
                "0"
            }
            val newDecimal = decimal.toDouble() / 100.0
            transactionAmount.value = String.format("%.2f", whole.toDouble() + newDecimal)
            decimal = String()
            return
        }
        whole = if (whole.length - 1 == 0)
            "0"
        else
            whole.substring(0, whole.length - 1)
        transactionAmount.value = String.format("%.2f", whole.toDouble())
    }

    fun displayTransaction(
        transactionDate: String?,
        transactionPos: Int?,
        transactionStatus: Int?
    ){
        if (transactionPos != -1 && transactionStatus != -1){
            val transaction = if (transactionStatus == 0){
                dailyTransaction.value[transactionPos!!]
            }else{
                transactionDate?.let {
                    monthlyTransaction.value[it]!![transactionPos!!]
                }
            }

            setTransactionTitle(transaction!!.title)
            currentTime.value = transaction.date
            Account.entries.forEach { account ->
                if (account.title == transaction.title){
                    selectAccount(account)
                    return@forEach
                }
            }
            transactionAmount.value = transaction.amount.toString()
            Category.entries.forEach { category ->
                if (category.title == transaction.category){
                    selectCategory(category)
                    return@forEach
                }
            }
        }
    }

    fun updateTransaction(
        transactionDate: String?,
        transactionPos: Int?,
        transactionStatus: Int?,
        navigateBack: () -> Unit
    ){
        if (transactionPos != -1 && transactionStatus != -1){
            val transaction = if (transactionStatus == 0){
                dailyTransaction.value[transactionPos!!]
            }else{
                transactionDate?.let {
                    monthlyTransaction.value[it]!![transactionPos!!]
                }
            }

            viewModelScope.launch {
                if (transactionAmount.value.toDouble() != transaction!!.amount){
                    val currentAccount = getAccountUseCase(account.value.title).first()
                    if (transaction.transactionType == TransactionType.INCOME.title){
                        currentAccount.income = currentAccount.income - transaction.amount
                        currentAccount.income = currentAccount.income + transactionAmount.value.toDouble()
                        currentAccount.balance = currentAccount.income - currentAccount.expense
                    }else{
                        currentAccount.expense = currentAccount.expense - transaction.amount
                        currentAccount.expense = currentAccount.expense + transactionAmount.value.toDouble()
                        currentAccount.balance = currentAccount.income - currentAccount.expense
                    }
                    insertAccountUseCase(listOf(currentAccount))
                }

                val updateTransaction = TransactionDto(
                    transaction.date,
                    transaction.dateOfEntry,
                    account.value.title,
                    transactionAmount.value.toDouble(),
                    category.value.title,
                    transaction.transactionType,
                    transactionTitle.value
                )
                insertNewTransactionUseCase(updateTransaction)
                withContext(Main){
                    navigateBack()
                }
            }
        }
    }

    fun displayExpenseLimitWarning(){
        viewModelScope.launch {
            getExpenseLimitUseCase().collectLatest { expenseAmount ->
                if (expenseAmount <= 0) return@collectLatest
                val threshold = 0.8 * expenseAmount
                when{
                    currentExpenseAmount.value > expenseAmount -> {
                        val expenseOverflow = (currentExpenseAmount.value - expenseAmount).toString().amountFormat()
                        val info = "${selectedCurrencyCode.value} $expenseOverflow Over Specified Limit"
                        limitAlert.emit(UiEvent.Alert(info))
                    }
                    currentExpenseAmount.value > threshold -> {
                        val expenseAvailable = (expenseAmount - currentExpenseAmount.value).toString().amountFormat()
                        val info = "${selectedCurrencyCode.value} $expenseAvailable Away from specified limit"
                        limitAlert.emit(UiEvent.Alert(info))
                    }
                    else -> limitAlert.emit(UiEvent.NoAlert())
                }
            }
        }
    }

    private fun currencyFormat(){
        viewModelScope.launch {
            getCurrencyUseCase().collect{ selectedCurrency ->
                selectedCurrencyCode.value = selectedCurrency
            }
        }
    }


}

fun String.amountFormat(): String {
    val amountFormatter = DecimalFormat("#,##0.00")
    return " " + amountFormatter.format(this.toDouble())
}
