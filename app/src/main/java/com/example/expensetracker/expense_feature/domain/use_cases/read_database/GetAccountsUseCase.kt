package com.example.expensetracker.expense_feature.domain.use_cases.read_database

import com.example.expensetracker.expense_feature.data.local.entity.AccountDto
import com.example.expensetracker.expense_feature.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: TransactionRepository
){

    operator fun invoke(): Flow<List<AccountDto>>{
        return repository.getAccounts()
    }
}