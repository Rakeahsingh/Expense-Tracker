package com.example.expensetracker.expense_feature.presentation.welcome_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.expense_feature.data.local.entity.AccountDto
import com.example.expensetracker.utils.Account
import com.example.expensetracker.expense_feature.domain.model.CurrencyModel
import com.example.expensetracker.expense_feature.domain.use_cases.GetCurrencyUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_database.InsertAccountUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_datastore.EditCurrencyUseCase
import com.example.expensetracker.expense_feature.domain.use_cases.write_datastore.EditOnBoardingKeyUseCase
import com.example.expensetracker.expense_feature.presentation.welcome_screen.components.OnBoardingPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val editOnBoardingKeyUseCase: EditOnBoardingKeyUseCase,
    private val editCurrencyUseCase: EditCurrencyUseCase,
    private val insertAccountUseCase: InsertAccountUseCase,
    getCurrencyUseCase: GetCurrencyUseCase
): ViewModel() {

    val listOfPages: State<List<OnBoardingPage>> = mutableStateOf(
        listOf(
            OnBoardingPage.FirstPage,
            OnBoardingPage.SecondPage,
            OnBoardingPage.ThirdPage
        )
    )

    var countryCurrencies = mutableStateOf(emptyMap<Char, List<CurrencyModel>>())
        private set

    init {
        countryCurrencies.value = getCurrencyUseCase().groupBy {
            it.country[0]
        }
    }

    fun saveOnBoardingState(complete: Boolean){
        viewModelScope.launch {
            editOnBoardingKeyUseCase(complete = complete)
        }
    }

    fun saveCurrency(currency: String){
        viewModelScope.launch {
            editCurrencyUseCase(currency)
        }
    }

    fun createAccounts(){
        viewModelScope.launch {
            insertAccountUseCase.invoke(
                listOf(
                    AccountDto(1,Account.CASH.title, 0.0, 0.0, 0.0 ),
                    AccountDto(2,Account.BANK.title, 0.0, 0.0, 0.0 ),
                    AccountDto(3,Account.CARD.title, 0.0, 0.0, 0.0 )
                )
            )
        }
    }


}