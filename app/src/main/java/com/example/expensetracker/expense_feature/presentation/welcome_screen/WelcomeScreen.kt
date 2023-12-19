package com.example.expensetracker.expense_feature.presentation.welcome_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.expense_feature.presentation.navigation.Screens
import com.example.expensetracker.expense_feature.presentation.welcome_screen.components.GetStartButton
import com.example.expensetracker.expense_feature.presentation.welcome_screen.components.PagerScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {

    val pages by viewModel.listOfPages
    val pagerState = rememberPagerState()

    Surface(
        color = MaterialTheme.colors.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HorizontalPager(
                count = pages.size,
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(10f)
            ) { pageCount ->
                PagerScreen(page = pages[pageCount])
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                indicatorWidth = 18.dp,
                indicatorHeight = 4.dp,
                activeColor = MaterialTheme.colors.primary,
                inactiveColor = Color.LightGray,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            )
            
            GetStartButton(pageState = pagerState, modifier = Modifier.weight(2f)){
                navController.popBackStack()
                navController.navigate("${Screens.CurrencyScreen.route}/${false}")
            }

        }

    }

}