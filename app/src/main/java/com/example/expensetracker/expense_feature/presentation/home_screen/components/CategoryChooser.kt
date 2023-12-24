package com.example.expensetracker.expense_feature.presentation.home_screen.components


import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.R
import com.example.expensetracker.expense_feature.presentation.home_screen.HomeViewModel
import com.example.expensetracker.utils.Category
import com.example.expensetracker.utils.LocalSpacing
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryChooser(
    expenseItem: Array<Category> = Category.entries.toTypedArray()
) {

    val spacing = LocalSpacing.current

    FlowRow(
        crossAxisSpacing = spacing.small,
        modifier = Modifier.padding(
            top = spacing.medium,
            start = spacing.medium,
            end = spacing.medium,
            bottom = spacing.medium
        )
    ) {
        expenseItem.forEach { category ->
            CategoryTag(category = category)
        }
    }
}

@Composable
fun CategoryTag(
    category: Category,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val selectedCategory by viewModel.category.collectAsState()
    var isSelected = selectedCategory.title == category.title

    TextButton(modifier = Modifier.padding(end = spacing.small),
        onClick = {
        viewModel.selectCategory(category)
        isSelected = selectedCategory.title == category.title
    },
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(
            vertical = spacing.small,
            horizontal = spacing.medium
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected)
            category.bgRes.copy(alpha = 0.95f)
            else MaterialTheme.colors.surface,
            contentColor = if (isSelected)
            category.colorRes
            else MaterialTheme.colors.onSurface
        )
    ) {
        Icon(
            painter =
            if (!isSelected){ painterResource(id = R.drawable.add_cart ) }
            else
                painterResource(id = category.iconRes),
            contentDescription = category.title
        )
        
        Spacer(modifier = Modifier.width(spacing.small))

        Text(
            text = category.title,
            style = MaterialTheme.typography.button
        )
    }

}