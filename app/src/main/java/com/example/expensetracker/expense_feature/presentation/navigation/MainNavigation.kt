package com.example.expensetracker.expense_feature.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetracker.expense_feature.presentation.account_screen.AccountScreen
import com.example.expensetracker.expense_feature.presentation.home_screen.HomeScreen
import com.example.expensetracker.expense_feature.presentation.insight_screen.InsightScreen
import com.example.expensetracker.expense_feature.presentation.setting_screen.SettingScreen
import com.example.expensetracker.expense_feature.presentation.welcome_screen.CurrencyScreen
import com.example.expensetracker.expense_feature.presentation.welcome_screen.WelcomeScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(Screens.WelcomeScreen.route){
            WelcomeScreen(navController = navController)
        }

        composable(
            route = "${Screens.CurrencyScreen.route}/{setting}",
            arguments = listOf(
                navArgument("setting"){
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ){
            val setting = it.arguments?.getBoolean("setting")
            CurrencyScreen(
                navController = navController,
                setting = setting
            )
        }

        composable(Screens.HomeScreen.route){
            HomeScreen(navController = navController)
        }

        composable(Screens.InsightScreen.route){
            InsightScreen(navController = navController)
        }

        composable(Screens.AccountScreen.route){
            AccountScreen(navController = navController)
        }

        composable(Screens.SettingScreen.route){
            SettingScreen(navController = navController)
        }
    }

}