package com.project.colombiapp.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FinanceDao {

    @Query("SELECT * FROM FinanceEntity")
    fun getAllFinance(): Flow<List<FinanceEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFinance(financeEntity: FinanceEntity)

}