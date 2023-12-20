package com.example.expensetracker.utils

import androidx.compose.ui.graphics.Color
import com.example.expensetracker.R
import com.example.expensetracker.ui.theme.food_drink
import com.example.expensetracker.ui.theme.healthBg
import com.example.expensetracker.ui.theme.homeBg
import com.example.expensetracker.ui.theme.schBg
import com.example.expensetracker.ui.theme.vehicleBg

enum class Category(
    val title: String,
    val iconRes: Int,
    val bgRes: Color,
    val colorRes: Color = Color.White
) {

    FOOD_DRINK("food_drink", R.drawable.food_drink, food_drink, Color.Black),
    VEHICLE("vehicle", R.drawable.vehicle, vehicleBg),
    SCHOOL("school", R.drawable.school, schBg),
    HOME("house", R.drawable.home, homeBg, Color.Black),
    HEALTH("health", R.drawable.health, healthBg)

}