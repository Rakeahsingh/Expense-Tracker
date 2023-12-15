package com.example.expensetracker.expense_feature.domain.use_cases.read_database

import com.example.expensetracker.expense_feature.data.local.entity.TransactionDto
import com.example.expensetracker.expense_feature.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Get3DayTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    operator fun invoke(transactionType: String): Flow<List<TransactionDto>> {
       return transactionRepository.get3DayTransaction(transactionType)
    }

}