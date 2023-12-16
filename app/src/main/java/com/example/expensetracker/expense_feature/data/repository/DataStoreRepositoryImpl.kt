package com.example.expensetracker.expense_feature.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.expensetracker.expense_feature.domain.repository.DataStoreRepository
import com.example.expensetracker.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "expense_key_store")
class DataStoreRepositoryImpl @Inject constructor(
    context: Context
): DataStoreRepository {

    private val dataStore = context.dataStore
    private val onBoardingKey = booleanPreferencesKey(Constant.WELCOME_KEY)
    private val limitKey = booleanPreferencesKey(Constant.LIMIT_KEY)
    private val selectedCurrency = stringPreferencesKey(Constant.CURRENCY_KEY)
    private val expenseLimit = doublePreferencesKey(Constant.EXPENSE_LIMIT_KEY)
    private val limitDuration = intPreferencesKey(Constant.LIMIT_DURATION)

    override suspend fun writeOnBoardingKeyToDataStore(complete: Boolean) {
        dataStore.edit { store ->
            store[onBoardingKey] = complete
        }
    }

    override suspend fun readOnBoardingKeyFromDataStore(): Flow<Boolean> {
        val preferences = dataStore.data
        return flow {
            preferences.collect{ pref ->
                emit(pref[onBoardingKey] ?: false)
            }
        }
    }

    override suspend fun writeCurrencyToDataStore(currency: String) {
        dataStore.edit { store ->
            store[selectedCurrency] = currency
        }
    }

    override suspend fun readCurrencyFromDataStore(): Flow<String> {
        val preferences = dataStore.data
        return flow {
            preferences.collect{ pref ->
                emit(pref[selectedCurrency] ?: String())
            }
        }
    }

    override suspend fun writeExpenseLimitToDataStore(amount: Double) {
        dataStore.edit { store ->
            store[expenseLimit] = amount
        }
    }

    override suspend fun readExpenseLimitFromDataStore(): Flow<Double> {
        val preferences = dataStore.data
        return flow {
            preferences.collect{ pref ->
                emit(pref[expenseLimit] ?: 0.0)
            }
        }
    }

    override suspend fun writeLimitKeyToDataStore(enabled: Boolean) {
        dataStore.edit { store ->
            store[limitKey] = enabled
        }
    }

    override suspend fun readLimitKeyFromDataStore(): Flow<Boolean> {
        val preferences = dataStore.data
        return flow {
            preferences.collect{ pref ->
                emit(pref[limitKey] ?: false)
            }
        }
    }

    override suspend fun writeLimitDurationToDataStore(duration: Int) {
        dataStore.edit { store ->
            store[limitDuration] = duration
        }
    }

    override suspend fun readLimitDurationFromDataStore(): Flow<Int> {
        val preferences = dataStore.data
        return flow {
            preferences.collect{ pref ->
                emit(pref[limitDuration] ?: 0)
            }
        }
    }

    override suspend fun eraseDataStore() {
        dataStore.edit {
            it.remove(limitKey)
            it.remove(limitDuration)
            it.remove(expenseLimit)
        }
    }
}