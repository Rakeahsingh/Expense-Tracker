package com.example.expensetracker.expense_feature.presentation.welcome_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@Composable
fun PagerScreen(
    page: OnBoardingPage
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = page.icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.6f)
        )

        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = page.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = page.description,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(.7f)
        )

    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GetStartButton(
    pageState: PagerState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(.8f)
    ){
        AnimatedVisibility(
            modifier = modifier
                .fillMaxWidth(),
            visible = pageState.currentPage == 2
        ) {

            Button(onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                ),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(text = "Get Started",
                    style = MaterialTheme.typography.button)
            }

        }

    }
}


@Composable
@Preview(showBackground = true)
fun FirstPagePreview() {
    PagerScreen(page = OnBoardingPage.FirstPage)
}

@Composable
@Preview(showBackground = true)
fun SecondPagePreview() {
    PagerScreen(page = OnBoardingPage.SecondPage)
}

@Composable
@Preview(showBackground = true)
fun ThirdPagePreview() {
    PagerScreen(page = OnBoardingPage.ThirdPage)
}