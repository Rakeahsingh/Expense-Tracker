package com.example.expensetracker.expense_feature.domain.model

data class CurrencyModel(
    val country: String = String(),
    val currencyCode: String = String(),
    val currencySymbol: String = String()
)
