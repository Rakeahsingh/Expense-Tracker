package com.example.expensetracker.expense_feature.presentation.home_screen

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expensetracker.expense_feature.presentation.home_screen.components.AddEntryChooser
import com.example.expensetracker.expense_feature.presentation.home_screen.components.Header
import com.example.expensetracker.expense_feature.presentation.home_screen.components.ListPlaceholder
import com.example.expensetracker.expense_feature.presentation.home_screen.components.TabButton
import com.example.expensetracker.utils.LocalSpacing
import com.example.expensetracker.utils.TabButton

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val dailyTransaction by viewModel.dailyTransaction.collectAsState()
    val monthlyTransaction by viewModel.monthlyTransaction.collectAsState()
    val currentTabButton by viewModel.tabButton.collectAsState()

    val lazyListState = rememberLazyListState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        sheetContent = { AddEntryChooser(
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            navController = navController
        ) },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContentColor = MaterialTheme.colors.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header content
            Header(bottomSheetScaffoldState = bottomSheetScaffoldState)

            // Tab button
            TabButton()

            Spacer(modifier = Modifier.height(spacing.medium))

            Text(
                text = "Transactions",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold
            )

            Divider(modifier = Modifier.padding(horizontal = spacing.large))

            // Daily Transactions
            AnimatedVisibility(
                visible = currentTabButton == TabButton.TODAY
            ) {
                if (dailyTransaction.isEmpty()){
                    ListPlaceholder()
                }
                LazyColumn(
                    state = lazyListState,
                    contentPadding = PaddingValues(
                        start = spacing.medium,
                        end = spacing.medium,
                        top = spacing.small
                    ),
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                ){

                }


            }

            // Monthly Transactions
            AnimatedVisibility(
                visible = currentTabButton == TabButton.MONTHLY
            ) {
                if (dailyTransaction.isEmpty()){
                    ListPlaceholder()
                }

            }

        }

    }
}