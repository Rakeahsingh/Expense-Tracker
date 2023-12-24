package com.example.expensetracker.expense_feature.presentation.home_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.ui.theme.Amber500
import com.example.expensetracker.utils.LocalSpacing
import com.example.expensetracker.utils.TransactionType

@Composable
fun InfoBanner(
    show: Boolean,
    transactionType: TransactionType
) {

    val spacing = LocalSpacing.current

    AnimatedVisibility(
        visible = show,
        enter = slideInVertically (
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically (
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium),
            color = Amber500,
            shape = RoundedCornerShape(spacing.large),
            elevation = 1.dp
        ) {
            Text(
                text = "Invalid ${transactionType.title} Amount",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(spacing.large)
            )

        }
    }

}