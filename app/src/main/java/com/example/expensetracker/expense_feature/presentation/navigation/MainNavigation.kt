package com.example.expensetracker.expense_feature.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetracker.expense_feature.presentation.account_screen.AccountDetailScreen
import com.example.expensetracker.expense_feature.presentation.account_screen.AccountScreen
import com.example.expensetracker.expense_feature.presentation.home_screen.HomeScreen
import com.example.expensetracker.expense_feature.presentation.home_screen.TransactionScreen
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

        composable(
            route = "${Screens.TransactionScreen.route}/{tag}?trxKey={trxKey}&trxPos={trxPos}&trxStatus={trxStatus}",
            arguments = listOf(
                navArgument("tag"){
                  type = NavType.IntType
                  defaultValue = 0
                },
                navArgument("trxKey"){
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                },
                navArgument("trxPos"){
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("trxStatus"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            TransactionScreen(
                navController = navController,
                transactionTag = it.arguments?.getInt("tag"),
                transactionDate = it.arguments?.getString("trxKey"),
                transactionPos = it.arguments?.getInt("trxPos"),
                transactionStatus = it.arguments?.getInt("trxStatus")
            )
        }

        composable(Screens.InsightScreen.route){
            InsightScreen(navController = navController)
        }

        composable(Screens.AccountScreen.route){
            AccountScreen(navController = navController)
        }

        composable(
            route = "${Screens.AccountDetailScreen.route}/{accountType}",
            arguments = listOf(
                navArgument("accountType"){
                    type = NavType.StringType
                    defaultValue = "Cash"
                    nullable = true
                }
            )
        ){
            val accountName = it.arguments?.getString("accountType")
            AccountDetailScreen(accountName = accountName)
        }

        composable(Screens.SettingScreen.route){
            SettingScreen(navController = navController)
        }
    }

}