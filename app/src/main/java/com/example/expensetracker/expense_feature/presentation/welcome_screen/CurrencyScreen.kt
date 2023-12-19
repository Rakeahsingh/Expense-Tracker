package com.example.expensetracker.expense_feature.presentation.welcome_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.expense_feature.domain.model.CurrencyModel
import com.example.expensetracker.expense_feature.presentation.navigation.Screens
import com.example.expensetracker.utils.LocalSpacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CurrencyScreen(
    navController: NavController,
    setting: Boolean?,
    viewModel: WelcomeViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val currencies by viewModel.countryCurrencies
    var selectedCountry by remember {
        mutableStateOf(CurrencyModel())
    }
    val scope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        sheetContent = {
                       ContinueButton(
                           navController = navController,
                           currency = selectedCountry,
                           setting = setting,
                           viewModel = viewModel
                       )
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp
    ) {

        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = it.calculateBottomPadding()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(elevation = 1.dp) {
                    Text(
                        text = "Set Currency",
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.W700),
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = spacing.medium,
                                end = spacing.medium,
                                top = spacing.small
                            )
                        )
                }

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(bottom = it.calculateBottomPadding())
                ){
                    currencies.forEach { (firstChar, list) ->
                        stickyHeader {
                            Surface(
                                color = MaterialTheme.colors.background
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(
                                        start = spacing.medium,
                                        end = spacing.medium,
                                        top = spacing.medium
                                    )
                                ) {
                                    Text(
                                        text = firstChar.toString(),
                                        style = MaterialTheme.typography.subtitle1,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                        )
                                }
                            }
                        }

                        items(list){ currency ->
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = spacing.medium,
                                        vertical = spacing.small
                                    )
                                    .clickable {
                                        selectedCountry = if (selectedCountry != currency) {
                                            scope.launch {
                                                bottomSheetScaffoldState.bottomSheetState.expand()
                                            }
                                            currency
                                        } else {
                                            scope.launch {
                                                bottomSheetScaffoldState.bottomSheetState.collapse()
                                            }
                                            CurrencyModel()
                                        }
                                    }
                            ){

                                TextButton(onClick = {
                                    selectedCountry = if (selectedCountry != currency){
                                        scope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        }
                                        currency
                                    }else{
                                        scope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                        CurrencyModel()
                                    }
                                },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(20.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (selectedCountry == currency)
                                        MaterialTheme.colors.primary
                                        else Color.DarkGray.copy(alpha = 0.1f),

                                        contentColor = if (selectedCountry == currency)
                                        contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                                        else MaterialTheme.colors.onSurface
                                    )
                                ) {

                                    Text(text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.W600,
                                                fontFamily = FontFamily.Serif,
                                                fontSize = 14.sp
                                            )
                                        ){
                                            append(currency.country.uppercase())
                                        }

                                        withStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Normal,
                                                color = Color.DarkGray.copy(alpha = 0.5f),
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily.Serif
                                            )
                                        ){
                                            append("(${currency.currencyCode})")
                                        }

                                    },
                                        modifier = Modifier.fillMaxWidth(),
                                        style = MaterialTheme.typography.subtitle2,
                                        textAlign = TextAlign.Center
                                        )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


@Composable
fun ContinueButton(
    navController: NavController,
    currency: CurrencyModel,
    setting: Boolean?,
    viewModel: WelcomeViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = spacing.medium,
                vertical = spacing.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ){

        Button(onClick = {
            viewModel.saveCurrency(currency.currencyCode)
            if (setting!!){
                navController.popBackStack()
            }else{
                navController.popBackStack()
                viewModel.saveOnBoardingState(complete = true)
                viewModel.createAccounts()
                navController.navigate(Screens.HomeScreen.route)
            }
        },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
            ),
            contentPadding = PaddingValues(vertical = 12.dp)

        ) {
            Text(text = "SET",
                style = MaterialTheme.typography.button)
        }
    }

}