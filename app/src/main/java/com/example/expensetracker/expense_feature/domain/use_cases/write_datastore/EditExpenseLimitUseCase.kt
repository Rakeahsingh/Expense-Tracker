package com.example.expensetracker.expense_feature.domain.use_cases.write_datastore

import com.example.expensetracker.expense_feature.domain.repository.DataStoreRepository
import javax.inject.Inject

class EditExpenseLimitUseCase @Inject constructor(
    private val repository: DataStoreRepository
){

    suspend operator fun invoke(amount: Double){
        repository.writeExpenseLimitToDataStore(amount)
    }

}