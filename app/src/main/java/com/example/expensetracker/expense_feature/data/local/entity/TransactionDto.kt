package com.example.expensetracker.expense_feature.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expensetracker.expense_feature.domain.model.Transaction
import java.util.Date


@Entity(tableName = "transaction_table")
data class TransactionDto(
    @PrimaryKey
    @ColumnInfo(name = "timeStamp")
    val date: Date,

    @ColumnInfo(name = "entry_date")
    val dateOfEntry: String,

    @ColumnInfo(name = "account")
    val account: String,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "transaction_type")
    val transactionType: String,

    @ColumnInfo(name = "transaction_title")
    val title: String
){

    fun toTransaction(): Transaction{
        return Transaction(date, dateOfEntry, account, amount, category, transactionType, title)
    }

}
