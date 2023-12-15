package com.example.expensetracker.expense_feature.domain.use_cases.read_dataStore

import com.example.expensetracker.expense_feature.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenseLimitUseCase @Inject constructor(
    private val repository: DataStoreRepository
){

    suspend operator fun invoke(): Flow<Double>{
        return repository.readExpenseLimitFromDataStore()
    }
}