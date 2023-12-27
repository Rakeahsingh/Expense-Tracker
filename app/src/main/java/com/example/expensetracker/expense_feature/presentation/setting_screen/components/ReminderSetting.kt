package com.example.expensetracker.expense_feature.presentation.setting_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.expense_feature.presentation.setting_screen.SettingViewModel
import com.example.expensetracker.utils.LocalSpacing

@Composable
fun ReminderSetting(
    viewModel: SettingViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val reminderLimit by viewModel.reminderLimit.collectAsState()

    TextButton(onClick = { },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.DarkGray.copy(alpha = 0.1f),
            contentColor = MaterialTheme.colors.onSurface
        ),
        contentPadding = PaddingValues(
            horizontal = spacing.medium,
            vertical = 15.dp
        ),
        modifier = Modifier.fillMaxSize()
            .padding(
                horizontal = spacing.medium,
                vertical = spacing.small
            )
    ) {

        Text(
            text = "Limit Reminder",
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )

        Switch(
            modifier = Modifier.padding(end = spacing.medium),
            checked = reminderLimit,
            onCheckedChange = {
                viewModel.editLimitKey(enabled = it)
            }
        )

    }

}