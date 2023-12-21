package com.example.expensetracker.expense_feature.presentation.home_screen.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.expensetracker.R
import com.example.expensetracker.expense_feature.presentation.navigation.Screens
import com.example.expensetracker.ui.theme.incomeGradient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEntryChooser(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    navController: NavHostController
) {

    val scope = rememberCoroutineScope()
    val progress = bottomSheetScaffoldState.bottomSheetState.progress
    val expendRotation by animateFloatAsState(
        targetValue = -360 * progress,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = Spring.StiffnessLow), label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = {
                scope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                navController.navigate("${Screens.TransactionScreen.route}/0")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.income),
                    contentDescription = "add entry",
                    tint = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .size(48.dp, 48.dp)
                        .rotate(expendRotation)
                        .background(incomeGradient, CircleShape)
                        .padding(8.dp)
                )
            }
            Text(
                text = "Add Income",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = {
                scope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                navController.navigate("${Screens.TransactionScreen.route}/1")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.expense),
                    contentDescription = "expense",
                    tint = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .size(48.dp, 48.dp)
                        .rotate(expendRotation)
                        .background(incomeGradient, CircleShape)
                        .padding(8.dp)
                )
            }
            Text(
                text = "Add Expense",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

}