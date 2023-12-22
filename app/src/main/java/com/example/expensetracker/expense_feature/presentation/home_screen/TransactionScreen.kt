package com.example.expensetracker.expense_feature.presentation.home_screen

import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.utils.LocalSpacing
import com.example.expensetracker.utils.TransactionType

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun TransactionScreen(
    navController: NavController,
    transactionTag: Int?,
    transactionDate: String?,
    transactionPos: Int?,
    transactionStatus: Int?,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val scope = rememberCoroutineScope()
    val keyBoardController = LocalSoftwareKeyboardController.current

    val transactionType = TransactionType.entries[transactionTag!!]
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    Text(text = "Transaction Screen")


}