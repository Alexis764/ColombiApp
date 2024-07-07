package com.project.colombiapp.feature.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.colombiapp.core.Categories
import com.project.colombiapp.core.database.FinanceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val financeList = mutableStateListOf<FinanceEntity>()

    private val _incomeList = mutableStateListOf<FinanceEntity>()
    val incomeList: List<FinanceEntity> = _incomeList

    private val _expenseList = mutableStateListOf<FinanceEntity>()
    val expenseList: List<FinanceEntity> = _expenseList

    init {
        viewModelScope.launch {
            homeRepository.getAllFinance().collect { databaseList ->
                financeList.clear()
                financeList.addAll(databaseList)

                _incomeList.clear()
                _incomeList.addAll(financeList.filter { it.isIncome })

                _expenseList.clear()
                _expenseList.addAll(financeList.filter { !it.isIncome })
            }
        }
    }


    private val _isShowCategoryDetailVisible = MutableLiveData<Boolean>()
    val isShowCategoryDetailVisible: LiveData<Boolean> = _isShowCategoryDetailVisible

    private val _categoryDetailInfo = MutableLiveData<FinanceEntity>()
    val categoryDetailInfo: LiveData<FinanceEntity> = _categoryDetailInfo

    fun showCategoryDetail(categories: Categories) {
        val categoryInfo: List<FinanceEntity> = _expenseList.filter { it.category == categories }

        if (categoryInfo.isNotEmpty()) {
            var categoryTotal = 0
            categoryInfo.forEach { categoryTotal += it.amount }

            _categoryDetailInfo.value = FinanceEntity(
                isIncome = false, amount = categoryTotal, category = categories
            )

        } else {
            _categoryDetailInfo.value = FinanceEntity(
                isIncome = false, amount = 0, category = categories
            )
        }

        _isShowCategoryDetailVisible.value = true
    }

    fun hideCategoryDetail() {
        _isShowCategoryDetailVisible.value = false
    }

}