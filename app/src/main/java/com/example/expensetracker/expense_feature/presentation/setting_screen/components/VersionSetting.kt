package com.example.expensetracker.expense_feature.presentation.setting_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.expensetracker.utils.LocalSpacing

@Composable
fun VersionSetting() {

    val spacing = LocalSpacing.current
    val context = LocalContext.current

    TextButton(onClick = {  },
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
            text = "Version",
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(2f)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            val packageManager = context.packageManager.getPackageInfo("com.example.expensetracker", 0)
            Text(
                text = packageManager.versionName,
                style = MaterialTheme.typography.subtitle2
            )
        }

    }

}