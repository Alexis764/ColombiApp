package com.project.colombiapp.feature.new_income

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.colombiapp.R
import com.project.colombiapp.core.Categories
import com.project.colombiapp.core.database.FinanceEntity
import com.project.colombiapp.ui.components.Operations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewIncomeViewModel @Inject constructor(
    private val newIncomeRepository: NewIncomeRepository
) : ViewModel() {

    private val _isIncomeCategoryVisible = MutableLiveData<Boolean>()
    val isIncomeCategoryVisible: LiveData<Boolean> = _isIncomeCategoryVisible

    fun showIncomeCategory() {
        _isIncomeCategoryVisible.value = true
    }

    fun hideIncomeCategory() {
        _isIncomeCategoryVisible.value = false
    }


    //NewIncomeCalculator
    private val _amount = MutableLiveData(0)
    val amount: LiveData<Int> = _amount

    private var amountAssistant = 0
    private lateinit var operations: Operations
    private var amountOperations = 0

    fun numberItemClick(number: Int) {
        val amountString = _amount.value.toString() + number
        _amount.value = amountString.toInt()
    }

    fun signItemClick(sign: Operations) {
        if (_amount.value!! != 0 || amountOperations >= 1) {
            when (sign) {
                Operations.Plus -> {
                    amountOperations++
                    operations = sign

                    if (amountOperations == 2) {
                        amountOperations--
                    } else {
                        amountAssistant = _amount.value!!
                    }

                    _amount.value = 0
                }

                Operations.Minus -> {
                    amountOperations++
                    operations = sign

                    if (amountOperations == 2) {
                        amountOperations--
                    } else {
                        amountAssistant = _amount.value!!
                    }

                    _amount.value = 0
                }

                Operations.Multiplication -> {
                    amountOperations++
                    operations = sign

                    if (amountOperations == 2) {
                        amountOperations--
                    } else {
                        amountAssistant = _amount.value!!
                    }

                    _amount.value = 0
                }

                Operations.Division -> {
                    amountOperations++
                    operations = sign

                    if (amountOperations == 2) {
                        amountOperations--
                    } else {
                        amountAssistant = _amount.value!!
                    }

                    _amount.value = 0
                }

                Operations.Equals -> {
                    if (::operations.isInitialized) {
                        if (_amount.value!! > 0) {
                            _amount.value = when (operations) {
                                Operations.Plus -> {
                                    amountAssistant + _amount.value!!
                                }

                                Operations.Minus -> {
                                    amountAssistant - _amount.value!!
                                }

                                Operations.Multiplication -> {
                                    amountAssistant * _amount.value!!
                                }

                                Operations.Division -> {
                                    amountAssistant / _amount.value!!
                                }

                                Operations.Equals -> {
                                    0
                                }
                            }
                            amountOperations = 0
                            amountAssistant = 0
                        }
                    }
                }
            }
        }
    }

    fun onDeleteDigitClick() {
        val isNegative = _amount.value!! < 0
        val amountString = if (isNegative)
            (_amount.value!! * (-1)).toString()
        else
            _amount.value.toString()

        if (amountString.length == 1) {
            _amount.value = 0

        } else {
            val amountSubString = amountString.substring(0, amountString.length - 1)
            _amount.value = if (isNegative)
                (amountSubString.toInt() * (-1))
            else
                amountSubString.toInt()
        }
    }


    private val _noteText = MutableLiveData<String>()
    val noteText: LiveData<String> = _noteText

    fun onNoteTextChange(value: String) {
        if (value.length <= 62) {
            _noteText.value = value
        }
    }


    //IncomeCategorySelected
    private val _incomeCategoryList = mutableStateListOf<NewIncomeCategory>()
    val incomeCategoryList: List<NewIncomeCategory> = _incomeCategoryList

    fun onIncomeCategorySelectedChange(newIncomeCategory: NewIncomeCategory) {
        val categoryIndex = _incomeCategoryList.indexOf(newIncomeCategory)

        var i = 0
        while (i < _incomeCategoryList.size) {
            _incomeCategoryList[i] = _incomeCategoryList[i].copy(isSelected = false)
            i++
        }

        _incomeCategoryList[categoryIndex] =
            _incomeCategoryList[categoryIndex].copy(isSelected = true)
    }

    fun insertIncome(category: NewIncomeCategory, amount: Int) {
        viewModelScope.launch {
            newIncomeRepository.insertFinance(
                FinanceEntity(
                    isIncome = true,
                    amount = amount,
                    category = category.name
                )
            )
        }
    }

    init {
        _incomeCategoryList.add(NewIncomeCategory(R.drawable.salary, Categories.Salary, true))
        _incomeCategoryList.add(NewIncomeCategory(R.drawable.money, Categories.Deposit, false))
        _incomeCategoryList.add(NewIncomeCategory(R.drawable.pig, Categories.Savings, false))
    }

}