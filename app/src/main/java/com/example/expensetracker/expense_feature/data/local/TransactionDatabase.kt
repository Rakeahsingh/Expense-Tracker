package com.example.expensetracker.expense_feature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.expense_feature.data.local.converter.DateConverter
import com.example.expensetracker.expense_feature.data.local.entity.AccountDto
import com.example.expensetracker.expense_feature.data.local.entity.TransactionDto


@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [AccountDto::class, TransactionDto::class],
    version = 1,
    exportSchema = true
)
abstract class TransactionDatabase : RoomDatabase(){

    abstract val dao: TransactionDao

}