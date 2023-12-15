package com.example.expensetracker.expense_feature.domain.use_cases.write_database

import com.example.expensetracker.expense_feature.domain.repository.TransactionRepository
import javax.inject.Inject

class EraseTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
){

    operator fun invoke(){
        repository.eraseTransaction()
    }
}