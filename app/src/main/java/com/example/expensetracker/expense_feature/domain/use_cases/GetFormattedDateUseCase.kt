package com.example.expensetracker.expense_feature.domain.use_cases

import android.text.format.DateFormat
import java.util.Date
import javax.inject.Inject

open class GetFormattedDateUseCase @Inject constructor() {

    open operator fun invoke(date: Date):String{
       return getFormattedDate(date)
    }

    private fun getFormattedDate(date: Date): String{
        val dayWeek = DateFormat.format("EEE",date)
        val day = DateFormat.format("dd",date)
        val monthAttr = DateFormat.format("MMM", date)

        return "$dayWeek, $day, $monthAttr"
    }
}