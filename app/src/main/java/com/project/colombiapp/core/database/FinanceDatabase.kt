package com.project.colombiapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FinanceEntity::class], version = 1)
abstract class FinanceDatabase : RoomDatabase() {

    abstract fun getFinanceDao(): FinanceDao

}