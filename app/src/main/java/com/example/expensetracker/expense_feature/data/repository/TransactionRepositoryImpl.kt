package com.example.expensetracker.expense_feature.data.repository

import com.example.expensetracker.expense_feature.data.local.TransactionDao
import com.example.expensetracker.expense_feature.data.local.entity.AccountDto
import com.example.expensetracker.expense_feature.data.local.entity.TransactionDto
import com.example.expensetracker.expense_feature.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
): TransactionRepository{
    override suspend fun insertTransaction(dailyExpense: TransactionDto) {
        dao.insertTransaction(dailyExpense)
    }

    override suspend fun insertAccount(account: List<AccountDto>) {
        dao.insertAccount(account)
    }

    override fun getDailyTransaction(entryDate: String): Flow<List<TransactionDto>> {
        return dao.getDailyTransaction(entryDate)
    }

    override fun getTransactionByAccount(accountType: String): Flow<List<TransactionDto>> {
        return dao.getTransactionByAccount(accountType)
    }

    override fun getAccount(account: String): Flow<AccountDto> {
        return dao.getAccount(account)
    }

    override fun getAccounts(): Flow<List<AccountDto>> {
       return dao.getAccounts()
    }

    override fun getAllTransactions(): Flow<List<TransactionDto>> {
        return dao.getAllTransactions()
    }

    override fun eraseTransaction() {
        dao.eraseTransaction()
    }

    override fun getCurrentDayExpTransaction(): Flow<List<TransactionDto>> {
        return dao.getCurrentDayExpTransaction()
    }

    override fun getWeeklyExpTransaction(): Flow<List<TransactionDto>> {
        return dao.getWeeklyExpTransaction()
    }

    override fun getMonthlyExpTransaction(): Flow<List<TransactionDto>> {
        return dao.getMonthlyExpTransaction()
    }

    override fun get3DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.get3DayTransaction(transactionType)
    }

    override fun get7DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.get7DayTransaction(transactionType)
    }

    override fun get14DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.get14DayTransaction(transactionType)
    }

    override fun getStartOfMonthTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.getStartOfMonthTransaction(transactionType)
    }

    override fun getLastMonthTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.getLastMonthTransaction(transactionType)
    }

    override fun getTransactionByType(transactionType: String): Flow<List<TransactionDto>> {
        return dao.getTransactionByType(transactionType)
    }
}