package com.project.colombiapp.feature.home

import com.project.colombiapp.core.database.FinanceDao
import com.project.colombiapp.core.database.FinanceEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val financeDao: FinanceDao
) {

    fun getAllFinance(): Flow<List<FinanceEntity>> {
        return financeDao.getAllFinance()
    }

}