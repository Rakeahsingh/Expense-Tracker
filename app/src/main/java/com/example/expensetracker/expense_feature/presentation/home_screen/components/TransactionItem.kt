package com.example.expensetracker.expense_feature.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.core.Constant
import com.example.expensetracker.expense_feature.domain.model.Transaction
import com.example.expensetracker.expense_feature.presentation.home_screen.HomeViewModel
import com.example.expensetracker.expense_feature.presentation.home_screen.amountFormat
import com.example.expensetracker.ui.theme.GreenAlpha700
import com.example.expensetracker.ui.theme.Red500
import com.example.expensetracker.utils.Category
import com.example.expensetracker.utils.LocalSpacing
import com.example.expensetracker.utils.spacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionItem(
    transaction: Transaction,
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: () -> Unit,
    cornerRadius: Dp = 24.dp
) {

    val spacing = LocalSpacing.current
    val currencyCode by viewModel.selectedCurrencyCode.collectAsState()
    val category = getCategory(transaction.category)

    Card(
        onClick = {
            onItemClick()
        },
        backgroundColor = Color.DarkGray.copy(alpha = 0.1f),
        elevation = 0.dp,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = spacing.medium),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.small
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = category.title,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            category.bgRes,
                            shape = RoundedCornerShape(cornerRadius)
                        )
                        .padding(
                            vertical = spacing.small,
                            horizontal = spacing.medium
                        ),
                    color = category.colorRes,
                    letterSpacing = TextUnit(1.1f, TextUnitType.Sp)
                )

                Text(
                    text = transaction.account,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(cornerRadius)
                        )
                        .padding(
                            vertical = spacing.small,
                            horizontal = spacing.medium
                        ),
                    color = Color.Black,
                    letterSpacing = TextUnit(1.1f, TextUnitType.Sp)
                )

            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    painter = painterResource(id = category.iconRes),
                    contentDescription = "transaction",
                    tint = Color.Black,
                    modifier = Modifier
                        .background(
                            Color.DarkGray.copy(alpha = 0.2f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(18.dp)
                )

                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    if (transaction.title.isNotEmpty()) {
                        Text(
                            text = transaction.title,
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                    }

                        Text(
                            text = currencyCode + "${transaction.amount}".amountFormat(),
                            color = if (transaction.transactionType == Constant.INCOME)
                                GreenAlpha700
                            else Red500.copy(alpha = 0.75f),
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W600),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                }
            }
        }
    }


}


fun getCategory(title: String): Category{
    var result: Category = Category.FOOD_DRINK
    Category.entries.forEach {
        if (it.title == title)
            result = it
    }
    return result
}