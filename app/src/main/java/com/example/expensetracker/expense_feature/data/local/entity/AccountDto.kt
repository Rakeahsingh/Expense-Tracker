package com.example.expensetracker.expense_feature.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expensetracker.expense_feature.domain.model.Account


@Entity(tableName = "accounts_table")
data class AccountDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "account")
    val accountType: String,

    @ColumnInfo(name= "balance")
    val balance: Double,

    @ColumnInfo(name = "income")
    val income: Double,

    @ColumnInfo(name = "expense")
    val expense: Double
){

    fun toAccount(): Account{
        return Account(accountType, balance, income, expense)
    }

}

