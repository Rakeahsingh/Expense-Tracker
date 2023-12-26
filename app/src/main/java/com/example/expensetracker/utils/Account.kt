package com.example.expensetracker.utils

import androidx.compose.ui.graphics.Color
import com.example.expensetracker.R
import com.example.expensetracker.ui.theme.food_drink
import com.example.expensetracker.ui.theme.giftBg
import com.example.expensetracker.ui.theme.healthBg
import com.example.expensetracker.ui.theme.leisureBg
import com.example.expensetracker.ui.theme.subBg

enum class Account(
    val title : String,
    val iconRes: Int,
    val color: Color
) {

    CASH("cash", R.drawable.cash, giftBg),
    BANK("bank", R.drawable.bank, subBg),
    CARD("card", R.drawable.credit_card, healthBg)

}