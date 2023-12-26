package com.example.expensetracker.expense_feature.presentation.account_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.expense_feature.domain.model.Account
import com.example.expensetracker.expense_feature.presentation.home_screen.amountFormat
import com.example.expensetracker.utils.LocalSpacing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountItem(
    account: Account,
    currency: String,
    onItemClick: (String) -> Unit
) {

    val spacing = LocalSpacing.current

    Card(
        onClick = {
            onItemClick(account.account)
        },
        backgroundColor = Color.DarkGray.copy(alpha = 0.1f),
        elevation = 0.dp,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = spacing.medium,
                start = spacing.medium,
                end = spacing.medium
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = spacing.small
                )
        ) {
            Text(
                text = "Balance",
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(
                    start = spacing.medium
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        letterSpacing = 0.4.sp
                    )
                ) {
                    append(currency)
                }
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.ExtraLight,
                        fontSize = 28.sp,
                        letterSpacing = 0.25.sp
                    )
                ) {
                    append(account.amount.toString().amountFormat())
                }
            }, modifier = Modifier.padding(start = spacing.medium))

            val color = when (account.account) {
                com.example.expensetracker.utils.Account.CASH.title -> com.example.expensetracker.utils.Account.CASH.color
                com.example.expensetracker.utils.Account.CARD.title -> com.example.expensetracker.utils.Account.CARD.color
                else -> com.example.expensetracker.utils.Account.BANK.color
            }

            Surface(
                color = color,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacing.small)
                    ) {
                        Icon(
                            painter = if (account.account == com.example.expensetracker.utils.Account.CASH.title){
                                                          painterResource(id = com.example.expensetracker.utils.Account.CASH.iconRes)
                            }else if(account.account == com.example.expensetracker.utils.Account.BANK.title){
                                painterResource(id = com.example.expensetracker.utils.Account.BANK.iconRes)
                            }
                            else painterResource(id = com.example.expensetracker.utils.Account.CARD.iconRes),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(spacing.medium))

                        Text(
                            text = account.account,
                            style = MaterialTheme.typography.caption,
                            color = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium)
                    ) {
                        Text(text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.4.sp,
                                    color = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                                )
                            ) {
                                append(currency)
                            }
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Thin,
                                    fontSize = 18.sp,
                                    letterSpacing = 0.25.sp,
                                    color = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                                )
                            ) {
                                append(account.income.toString().amountFormat())
                            }
                        })

                        Text(text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Thin,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.4.sp,
                                    color = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                                )
                            ) {
                                append(currency)
                            }
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Thin,
                                    fontSize = 18.sp,
                                    letterSpacing = 0.25.sp,
                                    color = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                                )
                            ) {
                                append(account.expense.toString().amountFormat())
                            }
                        })
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium)
                    ) {
                        Text(
                            text = "INCOME",
                            style = MaterialTheme.typography.overline,
                            color = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
                            modifier = Modifier.padding(
                                bottom = spacing.small
                            )
                        )

                        Text(
                            text = "EXPENSE",
                            style = MaterialTheme.typography.overline,
                            color = contentColorFor(backgroundColor = MaterialTheme.colors.primary),
                            modifier = Modifier.padding(
                                bottom = spacing.small
                            )
                        )
                    }
                }
            }
        }
    }
}