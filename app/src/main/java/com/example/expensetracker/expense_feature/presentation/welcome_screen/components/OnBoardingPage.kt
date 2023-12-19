package com.example.expensetracker.expense_feature.presentation.welcome_screen.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import com.example.expensetracker.R

sealed class OnBoardingPage(
    @SuppressLint("SupportAnnotationUsage") @DrawableRes
    val icon: Int,
    val title: String,
    val description: String
) {

    object FirstPage: OnBoardingPage(
        R.drawable.entry,
        "Add Entries",
        "Keep Track your All Income and Expense Here"
    )

    object SecondPage: OnBoardingPage(
        R.drawable.insight,
        "Check insights",
        "Detailed weekly and monthly charts based on your entries"
    )

    object ThirdPage: OnBoardingPage(
        R.drawable.decision,
        "Make Right decisions",
        "Control your money flow and stay on top of your game"
    )

}