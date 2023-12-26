package com.example.expensetracker.expense_feature.presentation.insight_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.expense_feature.presentation.home_screen.amountFormat
import com.example.expensetracker.expense_feature.presentation.home_screen.components.ListPlaceholder
import com.example.expensetracker.expense_feature.presentation.insight_screen.components.DonutChart
import com.example.expensetracker.expense_feature.presentation.insight_screen.components.InsightItem
import com.example.expensetracker.expense_feature.presentation.insight_screen.components.InsightTabBar
import com.example.expensetracker.utils.Category
import com.example.expensetracker.utils.LocalSpacing

@Composable
fun InsightScreen(
    navController: NavController,
    viewModel: InsightViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
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

    var expendedState by remember { mutableStateOf(false) }
    val limitDuration by remember {
        mutableStateOf(
            listOf(
                "Last 3 Days", "Last 7 Days", "Last 14 Days", "This Month", "Last Month", "All"
            )
        )
    }

    var selectedDuration by remember{
        mutableStateOf(limitDuration.last())
    }

    Surface(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(
                start = spacing.medium,
                end = spacing.medium,
                top = spacing.small
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .clickable {
                        expendedState = !expendedState
                    }
                    .padding(spacing.medium),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedDuration,
                    style = MaterialTheme.typography.subtitle1
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "dropDown menu",
                    tint = MaterialTheme.colors.onSurface
                )

                DropdownMenu(
                    expanded = expendedState,
                    onDismissRequest = { expendedState = false }
                ) {
                    limitDuration.forEachIndexed { index, label ->
                        DropdownMenuItem(onClick = {
                            selectedDuration = label
                            viewModel.getFilteredTransaction(index)
                            expendedState = false
                        }
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.subtitle2,
                                color = if (selectedDuration == label)
                                MaterialTheme.colors.primary
                                else Color.Gray
                            )
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(spacing.small))

            InsightTabBar()

            Spacer(modifier = Modifier.height(spacing.large))

            Text(
                text = "Total",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Start,
                letterSpacing = TextUnit(1.1f, TextUnitType.Sp),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = currencyCode + total.toString().amountFormat(),
                style = MaterialTheme.typography.h3.copy(fontSize = 20.sp),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.fillMaxWidth()
            )

            if (filterTransaction.isNotEmpty())
                DonutChart(
                    filteredCategories = filteredCategory,
                    percentProgress = percentProgress
                )

            LazyColumn{

                itemsIndexed(filteredCategory){index, category ->
                    val amount = groupData[category.title]?.sumOf {
                        it.amount
                    }
                    InsightItem(
                        cat = category,
                        currencyCode = currencyCode,
                        amount = amount!!,
                        percent = percentProgress[index]
                    )
                }
            }

            if (filterTransaction.isEmpty()){
                ListPlaceholder(
                    "No transaction. Tap the '+' button on the home menu to get started."
                )
            }

        }

    }


}