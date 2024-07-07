package com.project.colombiapp.feature.new_income

import com.project.colombiapp.core.database.FinanceDao
import com.project.colombiapp.core.database.FinanceEntity
import javax.inject.Inject

class NewIncomeRepository @Inject constructor(
    private val financeDao: FinanceDao
) {

    suspend fun insertFinance(financeEntity: FinanceEntity) {
        financeDao.insertFinance(financeEntity)
    }

}