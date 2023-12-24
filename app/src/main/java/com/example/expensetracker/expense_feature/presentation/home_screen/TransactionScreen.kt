package com.example.expensetracker.expense_feature.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.core.Constant
import com.example.expensetracker.core.UiEvent
import com.example.expensetracker.expense_feature.presentation.home_screen.components.AccountTag
import com.example.expensetracker.expense_feature.presentation.home_screen.components.CategoryChooser
import com.example.expensetracker.expense_feature.presentation.home_screen.components.InfoBanner
import com.example.expensetracker.expense_feature.presentation.home_screen.components.KeypadComponent
import com.example.expensetracker.ui.theme.Amber500
import com.example.expensetracker.ui.theme.Red200
import com.example.expensetracker.ui.theme.expenseGradient
import com.example.expensetracker.ui.theme.incomeGradient
import com.example.expensetracker.utils.Account
import com.example.expensetracker.utils.Category
import com.example.expensetracker.utils.LocalSpacing
import com.example.expensetracker.utils.TransactionType
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
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
    val keypadBottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    val title by remember { mutableStateOf(viewModel.transactionTitle) }
    val titleFieldValue = TextFieldValue(title.collectAsState().value)
    val currencyCode by viewModel.selectedCurrencyCode.collectAsState()
    val showInfoBanner by viewModel.showInfoBanner.collectAsState()
    val limitKey by viewModel.limitKey.collectAsState()
    val expenseAmount by viewModel.transactionAmount.collectAsState()
    val limitInfoWarning by viewModel.limitAlert.collectAsState(initial = UiEvent.NoAlert())


    BottomSheetScaffold(
        sheetContent = {
            KeypadComponent(
                bottomSheetScaffoldState = keypadBottomSheetState
            ) {
                viewModel.setTransaction(it)
            }
        },
        scaffoldState = keypadBottomSheetState,
        sheetPeekHeight = 0.dp,
        sheetContentColor = MaterialTheme.colors.background
    ) {
        LaunchedEffect(key1 = transactionPos){
            if (transactionPos != -1){
                viewModel.displayTransaction(transactionDate, transactionPos, transactionStatus)
            }
            viewModel.displayExpenseLimitWarning()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(bottom = it.calculateBottomPadding())
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = spacing.medium,
                            end = spacing.medium,
                            top = spacing.small
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Add Transaction",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.weight(2f)
                    )

                    IconButton(onClick = {
                        viewModel.apply {
                            if (transactionPos == -1){
                                setCurrentTime(Calendar.getInstance().time)
                                if (transactionType == TransactionType.INCOME){
                                    insertDailyTransaction(
                                        date.value,
                                        transactionAmount.value.toDouble(),
                                        category.value.title,
                                        Constant.INCOME, transactionTitle.value
                                    ){
                                        navController.navigateUp()
                                    }
                                }else{
                                    insertDailyTransaction(
                                        date.value,
                                        transactionAmount.value.toDouble(),
                                        category.value.title,
                                        Constant.EXPENSE, transactionTitle.value
                                    ){
                                        navController.navigateUp()
                                    }
                                }
                            }else{
                                updateTransaction(
                                    transactionDate,
                                    transactionPos,
                                    transactionStatus
                                ){
                                    navController.navigateUp()
                                }
                            }
                        }
                    },
                        modifier = Modifier
                            .scale(0.8f)
                            .background(incomeGradient, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.enter),
                            contentDescription = "enter",
                            tint = MaterialTheme.colors.surface,
                            modifier = Modifier.scale(0.8f)
                        )
                    }

                    IconButton(onClick = {
                        navController.popBackStack()
                    },
                        modifier = Modifier
                            .scale(0.8f)
                            .padding(start = spacing.medium)
                            .background(expenseGradient, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.remove),
                            contentDescription = "remove",
                            tint = MaterialTheme.colors.surface,
                            modifier = Modifier.scale(0.8f)
                        )
                    }

                }

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    InfoBanner(
                        show = showInfoBanner,
                        transactionType = transactionType
                    )

                    TextField(
                        value = titleFieldValue.text,
                        onValueChange = {
                            viewModel.setTransactionTitle(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = spacing.small,
                                start = spacing.medium,
                                end = spacing.medium,
                                bottom = spacing.medium
                            ),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.W600),
                        placeholder = {
                            Text(
                                text = if (transactionType == TransactionType.INCOME)
                                "Income Title"
                                else "Expense Title",
                                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.W600)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colors.primary,
                            cursorColor = MaterialTheme.colors.primary,
                            backgroundColor = Color.LightGray
                        )
                    )

                    Text(
                        text = if (transactionType == TransactionType.INCOME)
                        "Add Fund"
                        else "Pay With",
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W300),
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(
                                vertical = spacing.small,
                                horizontal = spacing.medium
                            )
                            .align(Alignment.Start)
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(
                                horizontal = spacing.medium,
                                vertical = spacing.small
                            )
                            .align(Alignment.Start)
                    ){
                        items(Account.entries){ account ->
                            AccountTag(account = account)
                        }
                    }

                    TextButton(onClick = {
                        scope.launch {
                            keyBoardController?.hide()
                            if (keypadBottomSheetState.bottomSheetState.isCollapsed)
                                keypadBottomSheetState.bottomSheetState.expand()
                            else keypadBottomSheetState.bottomSheetState.collapse()
                        }
                    },
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(
                                start = spacing.medium,
                                top = spacing.small
                            ),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(Amber500.copy(alpha = 0.8f)),
                        contentPadding = PaddingValues(
                            horizontal = spacing.medium,
                            vertical = spacing.small
                        ),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 12.dp
                        )
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.W300,
                                        fontSize = 24.sp
                                    )
                                ){
                                    append(currencyCode)
                                    append(expenseAmount.amountFormat())
                                }
                            },
                            color = MaterialTheme.colors.surface
                        )

                    }

                    if (limitKey){
                        if (limitInfoWarning is UiEvent.Alert){
                            Spacer(modifier = Modifier.height(spacing.small))
                            Row (
                                modifier = Modifier
                                    .padding(
                                        horizontal = spacing.medium
                                    )
                                    .align(Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.info_warning),
                                    contentDescription = "warning",
                                    tint = Red200
                                )
                                CompositionLocalProvider(
                                    LocalContentAlpha provides ContentAlpha.disabled
                                ) {
                                    Text(
                                        text = (limitInfoWarning as UiEvent.Alert).info,
                                        style = MaterialTheme.typography.caption
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(spacing.medium))

                    Text(
                        text = "Choose Category",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSurface,
                        letterSpacing = TextUnit(0.2f, TextUnitType.Sp),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(
                                vertical = spacing.small,
                                horizontal = spacing.medium
                            )
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium)
                    )

                    Spacer(modifier = Modifier.height(spacing.small))

                    CategoryChooser()

                }

            }

        }

    }


}