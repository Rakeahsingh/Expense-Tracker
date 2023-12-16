package com.example.expensetracker.expense_feature.domain.use_cases.write_datastore

import com.example.expensetracker.expense_feature.domain.repository.DataStoreRepository
import javax.inject.Inject

class EditOnBoardingKeyUseCase @Inject constructor(
    private val repository: DataStoreRepository
){

    suspend operator fun invoke(complete: Boolean){
        repository.writeOnBoardingKeyToDataStore(complete)
    }
}