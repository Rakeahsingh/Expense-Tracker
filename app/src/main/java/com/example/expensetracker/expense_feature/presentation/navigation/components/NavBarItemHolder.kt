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
        "",
        R.drawable.main_home,
        Screens.HomeScreen.route
    ),


    NavBarItemHolder(
        "",
    R.drawable.donut,
    Screens.InsightScreen.route
    ),


    NavBarItemHolder(
        "",
        R.drawable.account,
        Screens.AccountScreen.route
    ),


    NavBarItemHolder(
        "",
        R.drawable.setting,
        Screens.SettingScreen.route
    )


)
