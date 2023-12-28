package com.example.expensetracker.expense_feature.presentation.setting_screen.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.expensetracker.expense_feature.domain.use_cases.write_datastore.EditExpenseLimitUseCase


class LimitResetWorker(
    context: Context,
    workParams: WorkerParameters,
    val editExpenseLimitUseCase: EditExpenseLimitUseCase
): CoroutineWorker(context, workParams) {
    override suspend fun doWork(): Result {
        editExpenseLimitUseCase(0.0)
        return Result.success()
    }
}