package com.project.colombiapp.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideFinanceDatabase(@ApplicationContext context: Context): FinanceDatabase {
        return Room.databaseBuilder(context, FinanceDatabase::class.java, "financeDatabase").build()
    }

    @Provides
    fun provideFinanceDao(financeDatabase: FinanceDatabase): FinanceDao {
        return financeDatabase.getFinanceDao()
    }

}