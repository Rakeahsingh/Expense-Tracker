package com.example.expensetracker.expense_feature.presentation.home_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.expense_feature.presentation.home_screen.HomeViewModel
import com.example.expensetracker.ui.theme.giftBg
import com.example.expensetracker.utils.LocalSpacing
import com.example.expensetracker.utils.TabButton

@Composable
fun TabButton(
    tabs: Array<TabButton> = TabButton.entries.toTypedArray(),
    cornerRadius: Dp = 24.dp,
    onButtonClick: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val selectedButton by viewModel.tabButton.collectAsState()

    Surface(
        modifier = Modifier.padding(
            top = spacing.small,
            start = spacing.medium,
            end = spacing.medium
        ),
        color = Color.DarkGray.copy(alpha = 0.1f),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = spacing.medium,
                    end = spacing.medium
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            tabs.forEach { tab ->
                val backgroundColor by animateColorAsState(
                    if (selectedButton == tab) giftBg
                    else Color.Transparent,
                    animationSpec = tween(500, easing = LinearOutSlowInEasing),
                    label = ""
                )
                val textColor by animateColorAsState(
                    if (selectedButton == tab) MaterialTheme.colors.surface
                    else MaterialTheme.colors.onSurface,
                    animationSpec = tween(500, easing = LinearOutSlowInEasing),
                    label = ""
                )

                TextButton(onClick = {
                    viewModel.selectTabButton(tab)
                    onButtonClick()
                },
                    modifier = Modifier
                        .padding(
                            vertical = spacing.extraSmall
                        )
                        .weight(1f),
                    shape = RoundedCornerShape(cornerRadius),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = backgroundColor,
                        contentColor = textColor
                    )
                ) {

                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(
                            vertical = spacing.extraSmall,
                            horizontal = spacing.small
                        )
                            .align(Alignment.CenterVertically)
                    )

                }

            }

        }

    }


}