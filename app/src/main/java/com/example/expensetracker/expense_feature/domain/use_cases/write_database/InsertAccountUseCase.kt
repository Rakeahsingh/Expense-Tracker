package com.example.expensetracker.expense_feature.domain.use_cases.write_database

import com.example.expensetracker.expense_feature.data.local.entity.AccountDto
import com.example.expensetracker.expense_feature.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertAccountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(account: List<AccountDto>){
        repository.insertAccount(account)
    }
}