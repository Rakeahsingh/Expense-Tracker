package com.example.expensetracker.expense_feature.presentation.account_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.expense_feature.presentation.account_screen.components.AccountItem
import com.example.expensetracker.expense_feature.presentation.navigation.Screens
import com.example.expensetracker.utils.LocalSpacing

@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val accounts by viewModel.allAccounts.collectAsState()
    val currency by viewModel.selectedCurrencyCode.collectAsState()

    Surface(
       color = MaterialTheme.colors.background
    ) {

        LazyColumn{

            item {
                Text(
                    text = "Accounts",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.W700),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = spacing.small,
                            start = spacing.medium,
                            end = spacing.medium
                        )
                )
            }

            items(accounts){ account ->
               AccountItem(
                   account = account,
                   currency = currency
               ){
                   navController.navigate("${Screens.AccountDetailScreen.route}/$it")
               }
            }
        }

    }

}