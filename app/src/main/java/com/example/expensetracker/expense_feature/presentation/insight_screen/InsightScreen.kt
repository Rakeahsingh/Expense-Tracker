package com.example.expensetracker.expense_feature.presentation.insight_screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.utils.Category

@Composable
fun InsightScreen(
    navController: NavController,
    viewModel: InsightViewModel = hiltViewModel()
) {

    val filterTransaction by viewModel.filterTransaction.collectAsState()
    val currencyCode by viewModel.selectedCurrencyCode.collectAsState()

    val total = filterTransaction.sumOf {
        it.amount
    }

    val groupData = filterTransaction.groupBy {
        it.category
    }

    val filteredCategory = mutableListOf<Category>()
    groupData.forEach { data ->
        Category.entries.forEach  cat@{
            if (data.key == it.title){
                filteredCategory.add(it)
                return@cat
            }
        }
    }

    val amountList = groupData.map {
        it.value.sumOf { trx ->
            trx.amount
        }
    }

    val totalTrx = amountList.map {
        it.toFloat()
    }.sum()

    val percentProgress = amountList.map {
        it.toFloat() * 100 / totalTrx
    }

    Text(text = "Insight screen")
}