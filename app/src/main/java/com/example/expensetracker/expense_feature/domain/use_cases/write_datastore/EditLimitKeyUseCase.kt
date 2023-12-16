package com.example.expensetracker.expense_feature.domain.use_cases.write_datastore

import com.example.expensetracker.expense_feature.domain.repository.DataStoreRepository
import javax.inject.Inject

class EditLimitKeyUseCase @Inject constructor(
    private val repository: DataStoreRepository
){

    suspend operator fun invoke(enabled:Boolean){
        repository.writeLimitKeyToDataStore(enabled)
    }
}