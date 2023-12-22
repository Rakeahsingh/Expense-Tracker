package com.example.expensetracker.expense_feature.presentation.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.expensetracker.R
import com.example.expensetracker.utils.LocalSpacing

@Composable
fun ListPlaceholder(
    label: String = "No Transaction has been made so Far. Tap the '+' Icon to Get Started"
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = spacing.medium,
                top = spacing.medium,
                end = spacing.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(id = R.drawable.blank_list),
            contentDescription = "blank list",
            tint = MaterialTheme.colors.onBackground
        )

        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

    }

}