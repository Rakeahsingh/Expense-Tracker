package com.example.expensetracker.expense_feature.presentation.navigation

sealed class Screens(val route: String) {

    object WelcomeScreen: Screens("welcome")
    object CurrencyScreen: Screens("currency")

    object HomeScreen: Screens("home")
    object TransactionScreen: Screens("transaction")
    object InsightScreen: Screens("insight")
    object AccountScreen: Screens("account")
    object AccountDetailScreen: Screens("account_detail")
    object SettingScreen: Screens("setting")
}