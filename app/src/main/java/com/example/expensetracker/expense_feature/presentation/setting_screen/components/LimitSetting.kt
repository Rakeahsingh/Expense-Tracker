package com.example.expensetracker.expense_feature.presentation.setting_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.expense_feature.presentation.home_screen.amountFormat
import com.example.expensetracker.expense_feature.presentation.setting_screen.SettingViewModel
import com.example.expensetracker.utils.LocalSpacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LimitSetting(
    modelBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    viewModel: SettingViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {

    val spacing = LocalSpacing.current
    val currencyCode by viewModel.currency.collectAsState()
    val expenseLimit by viewModel.expenseLimit.collectAsState()

    TextButton(onClick = {
        onItemClick(1)
        scope.launch {
            modelBottomSheetState.show()
        }
    },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.DarkGray.copy(alpha = 0.1f),
            contentColor = MaterialTheme.colors.onSurface
        ),
        contentPadding = PaddingValues(
            horizontal = spacing.medium,
            vertical = 20.dp
        ),
        modifier = Modifier.fillMaxSize()
            .padding(
                horizontal = spacing.medium,
                vertical = spacing.small
            )
    ) {

        Text(
            text = "Expense Limit",
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(2f)
            )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = currencyCode + expenseLimit.toString().amountFormat(),
                style = MaterialTheme.typography.subtitle2
            )
        }

    }

}