package com.example.expensetracker.expense_feature.presentation.setting_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.expense_feature.presentation.navigation.Screens
import com.example.expensetracker.utils.LocalSpacing


@Composable
fun CurrencySetting(
    currency: String,
    navController: NavController
) {

    val spacing = LocalSpacing.current

    TextButton(onClick = {
        navController.navigate("${Screens.CurrencyScreen.route}/${true}")
    },
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = spacing.small,
                horizontal = spacing.medium
            ),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.DarkGray.copy(alpha = 0.1f),
            contentColor = MaterialTheme.colors.onSurface
        ),
        contentPadding = PaddingValues(
            horizontal = spacing.medium,
            vertical = 20.dp
        )
    ) {
        Text(
            text = "Currency",
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(2f)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = currency,
                style = MaterialTheme.typography.subtitle2
            )

            Icon(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = null,
                modifier = Modifier.then(Modifier.size(16.dp))
            )
        }

    }

}