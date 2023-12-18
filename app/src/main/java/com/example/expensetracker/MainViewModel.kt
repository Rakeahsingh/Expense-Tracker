package com.example.expensetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore.GetOnBoardingKeyUseCase
import com.example.expensetracker.expense_feature.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getOnBoardingKeyUseCase: GetOnBoardingKeyUseCase
): ViewModel(){

    var isLoading = MutableStateFlow(true)
        private set
    var startDestination = MutableStateFlow(Screens.WelcomeScreen.route)
        private set

    init {
        viewModelScope.launch {
            getOnBoardingKeyUseCase().collect{ completed ->
                if (completed)
                    startDestination.value = Screens.HomeScreen.route

            }
          isLoading.value = false
        }
    }

}


