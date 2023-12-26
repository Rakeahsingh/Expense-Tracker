package com.example.expensetracker.expense_feature.presentation.insight_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.expense_feature.presentation.insight_screen.InsightViewModel
import com.example.expensetracker.ui.theme.GreenAlpha700
import com.example.expensetracker.ui.theme.Red500
import com.example.expensetracker.utils.LocalSpacing
import com.example.expensetracker.utils.TransactionType

@Composable
fun InsightTabBar(
    viewModel: InsightViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val selectedTabButton by viewModel.tabButton.collectAsState()

    Surface(
        color = Color.DarkGray.copy(alpha = 0.1f),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = spacing.medium,
                    end = spacing.medium
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){

            InsightBar(
                modifier = Modifier.weight(1f),
                title = "Income",
                backgroundColor = animateColorAsState(
                    targetValue = if (selectedTabButton == TransactionType.INCOME)
                     GreenAlpha700 else Color.Transparent, label = ""
                ).value,
                textColor = if (selectedTabButton == TransactionType.INCOME)
                Color.White else Color.Black,
                tabButtonClick = {
                    viewModel.selectTabButton(TransactionType.INCOME)
                }
            )

            InsightBar(
                modifier = Modifier.weight(1f),
                title = "Expense",
                backgroundColor = animateColorAsState(
                    targetValue = if (selectedTabButton == TransactionType.EXPENSE)
                        Red500 else Color.Transparent, label = ""
                ).value,
                textColor = if (selectedTabButton == TransactionType.EXPENSE)
                    Color.White else Color.Black,
                tabButtonClick = {
                    viewModel.selectTabButton(TransactionType.EXPENSE)
                }
            )

        }
    }

}

@Composable
fun InsightBar(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    textColor: Color,
    tabButtonClick: () -> Unit
) {

    val spacing = LocalSpacing.current

    TextButton(onClick = {
        tabButtonClick()
    },
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = textColor
        ),
        modifier = modifier.padding(vertical = spacing.small)
    ) {
        Text(
            text = title,
            color = textColor,
            style = MaterialTheme.typography.subtitle1,
            modifier = modifier
                .padding(
                    vertical = spacing.extraSmall,
                    horizontal = spacing.small
                )
                .align(Alignment.CenterVertically)
        )
    }
}