package com.example.expensetracker.expense_feature.domain.use_cases.read_database

import com.example.expensetracker.expense_feature.data.local.entity.TransactionDto
import com.example.expensetracker.expense_feature.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeeklyExpTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
){

    operator fun invoke(): Flow<List<TransactionDto>>{
        return repository.getWeeklyExpTransaction()
    }
}