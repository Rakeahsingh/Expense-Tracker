package com.example.expensetracker.expense_feature.domain.use_cases.write_database

import com.example.expensetracker.expense_feature.data.local.entity.TransactionDto
import com.example.expensetracker.expense_feature.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertNewTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
){

    suspend operator fun invoke(dailyExpense: TransactionDto){
        repository.insertTransaction(dailyExpense)
    }
}