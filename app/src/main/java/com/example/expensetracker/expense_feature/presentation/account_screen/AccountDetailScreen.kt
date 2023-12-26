package com.example.expensetracker.expense_feature.presentation.account_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.expense_feature.presentation.home_screen.components.ListPlaceholder
import com.example.expensetracker.expense_feature.presentation.home_screen.components.TransactionItem
import com.example.expensetracker.utils.LocalSpacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountDetailScreen(
    accountName: String?,
    viewModel: AccountViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val transactions by viewModel.transaction.collectAsState()
    if (accountName != null){
        viewModel.getTransaction(accountName)
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.padding(
            top = spacing.small
        )
    ) {

        if (transactions.isEmpty()){
            ListPlaceholder()
        }

        LazyColumn(
            contentPadding = PaddingValues(
                top = spacing.small,
                start = spacing.medium,
                end = spacing.medium
            )
        ){
            item {
                Text(
                    text = "Transactions",
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colors.onBackground
                )

                Text(
                    text = accountName!!,
                    style = MaterialTheme.typography.h3.copy(
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colors.onBackground
                )
            }

            transactions.forEach { (date, allTrx) ->
                stickyHeader {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            vertical = spacing.small
                        )
                    ) {

                        Text(
                            text = date,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )

                    }
                }

                itemsIndexed(allTrx){ _, transaction ->
                    TransactionItem(
                        transaction = transaction,
                        onItemClick = { /*TODO*/ }
                    )
                }
            }

        }

    }

}