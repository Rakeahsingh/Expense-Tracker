package com.example.expensetracker.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.expensetracker.expense_feature.data.local.TransactionDatabase
import com.example.expensetracker.expense_feature.data.repository.DataStoreRepositoryImpl
import com.example.expensetracker.expense_feature.data.repository.TransactionRepositoryImpl
import com.example.expensetracker.expense_feature.domain.repository.DataStoreRepository
import com.example.expensetracker.expense_feature.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTransactionRepository(db: TransactionDatabase): TransactionRepository{
        return TransactionRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository{
        return DataStoreRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideExpenseDatabase(app: Application): TransactionDatabase{
        return Room.databaseBuilder(
            app,
            TransactionDatabase::class.java,
            "expense_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: TransactionDatabase) = db.dao

}