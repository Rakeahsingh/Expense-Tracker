package com.example.expensetracker.expense_feature.presentation.navigation.components

import com.example.expensetracker.R
import com.example.expensetracker.expense_feature.presentation.navigation.Screens

data class NavBarItemHolder(
    val title: String,
    val icon: Int,
    val route: String
)

fun provideBottomNavItem() = listOf(
    NavBarItemHolder(
        "Home",
        R.drawable.main_home,
        Screens.HomeScreen.route
    ),


    NavBarItemHolder(
        "Transaction",
    R.drawable.donut,
    Screens.InsightScreen.route
    ),


    NavBarItemHolder(
        "Account",
        R.drawable.account,
        Screens.AccountScreen.route
    ),


    NavBarItemHolder(
        "Setting",
        R.drawable.setting,
        Screens.SettingScreen.route
    )


)
