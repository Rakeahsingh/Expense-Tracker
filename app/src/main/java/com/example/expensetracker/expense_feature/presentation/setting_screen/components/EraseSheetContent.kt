package com.example.expensetracker.expense_feature.presentation.setting_screen.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.expense_feature.presentation.setting_screen.SettingViewModel
import com.example.expensetracker.ui.theme.GreenAlpha700
import com.example.expensetracker.ui.theme.Red500
import com.example.expensetracker.utils.LocalSpacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EraseSheetContent(
    modalBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    viewModel: SettingViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
    ) {

        Text(
            text = "ERASE ALL DATA",
            style = MaterialTheme.typography.subtitle2
        )

        Text(
            text = "You're about to erase all transactions on this app. This cannot be reversed",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            color = Color.DarkGray.copy(alpha = 0.5f),
            modifier = Modifier.padding(
                top = spacing.medium
            )
        )

        TextButton(onClick = {
            scope.launch {
                modalBottomSheetState.hide()
            }
        },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Red500,
                contentColor = MaterialTheme.colors.onSurface
            ),
            contentPadding = PaddingValues(
                vertical = 16.dp
            )
        ) {
            Text(
                text = "Cancel",
                style = MaterialTheme.typography.button
            )

        }

        TextButton(onClick = {
            scope.launch {
                viewModel.eraseTransaction()
                modalBottomSheetState.hide()
                Toast.makeText(context, "Erased All Data!", Toast.LENGTH_SHORT).show()
            }
        },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = GreenAlpha700,
                contentColor = MaterialTheme.colors.onSurface
            ),
            contentPadding = PaddingValues(
                vertical = 16.dp
            )
        ) {
            Text(
                text = "Continue",
                style = MaterialTheme.typography.button
            )

        }

    }

}