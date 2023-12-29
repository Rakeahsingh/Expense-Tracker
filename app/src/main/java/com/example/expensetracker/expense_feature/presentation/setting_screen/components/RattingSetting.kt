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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.expensetracker.ui.theme.Amber500
import com.example.expensetracker.utils.LocalSpacing

@Composable
fun RattingSetting() {

    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val packageName = context.packageName

    TextButton(onClick = {
//        try {
//            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
//        }catch (e: ActivityNotFoundException){
//            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
//        }
    },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.DarkGray.copy(alpha = 0.1f),
            contentColor = MaterialTheme.colors.onSurface
        ),
        contentPadding = PaddingValues(
            vertical = 20.dp,
            horizontal = spacing.medium
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = spacing.small,
                horizontal = spacing.medium
            )
    ) {

        Text(
            text = "Rate Us",
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(2f)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

            Icon(
                imageVector = Icons.Filled.StarRate,
                contentDescription = null,
                tint = Amber500 ,
                modifier = Modifier.then(Modifier.size(20.dp))
            )

            Icon(
                imageVector = Icons.Filled.StarRate,
                contentDescription = null,
                tint = Amber500 ,
                modifier = Modifier.then(Modifier.size(20.dp))
            )

            Icon(
                imageVector = Icons.Filled.StarRate,
                contentDescription = null,
                tint = Amber500 ,
                modifier = Modifier.then(Modifier.size(20.dp))
            )

//            Icon(
//                imageVector = Icons.Filled.StarRate,
//                contentDescription = null,
//                tint = Amber500 ,
//                modifier = Modifier.then(Modifier.size(20.dp))
//            )

            Icon(
                imageVector = Icons.Filled.StarHalf,
                contentDescription = null,
                tint = Amber500 ,
                modifier = Modifier.then(Modifier.size(20.dp))
            )

//            Icon(
//                painter = painterResource(id = R.drawable.edit),
//                contentDescription = null,
//                modifier = Modifier.then(Modifier.size(16.dp))
//            )
        }

    }

}