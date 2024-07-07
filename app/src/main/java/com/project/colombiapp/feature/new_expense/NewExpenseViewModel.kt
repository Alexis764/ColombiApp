package com.project.colombiapp.feature.new_expense

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.colombiapp.core.Categories
import com.project.colombiapp.core.Categories.Alcohol
import com.project.colombiapp.core.Categories.Clothes
import com.project.colombiapp.core.Categories.Health
import com.project.colombiapp.core.Categories.Home
import com.project.colombiapp.core.Categories.Market
import com.project.colombiapp.core.Categories.Pets
import com.project.colombiapp.core.Categories.Phone
import com.project.colombiapp.core.Categories.Presents
import com.project.colombiapp.core.Categories.Restaurant
import com.project.colombiapp.core.Categories.Shoes
import com.project.colombiapp.core.Categories.Sports
import com.project.colombiapp.core.Categories.Transport
import com.project.colombiapp.core.database.FinanceEntity
import com.project.colombiapp.ui.components.Operations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewExpenseViewModel @Inject constructor(
    private val newExpenseRepository: NewExpenseRepository
) : ViewModel() {

    private val _isExpenseCategoryVisible = MutableLiveData<Boolean>()
    val isExpenseCategoryVisible: LiveData<Boolean> = _isExpenseCategoryVisible

    fun showExpenseCategory() {
        _isExpenseCategoryVisible.value = true
    }

    fun hideExpenseCategory() {
        _isExpenseCategoryVisible.value = false
    }


    //NewExpenseCalculator
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


    //ExpenseCategorySelected
    private val _expenseCategoryList = mutableStateListOf<Categories>()
    val expenseCategoryList: List<Categories> = _expenseCategoryList

    private val _expenseCategorySelected = MutableLiveData(Home)
    val expenseCategorySelected: LiveData<Categories> = _expenseCategorySelected

    private val _isCategoriesDropExpanded = MutableLiveData<Boolean>()
    val isCategoriesDropExpanded: LiveData<Boolean> = _isCategoriesDropExpanded

    fun onExpenseCategorySelected(categories: Categories) {
        _expenseCategorySelected.value = categories
    }

    fun showCategoriesDrop() {
        _isCategoriesDropExpanded.value = true
    }

    fun hideCategoriesDrop() {
        _isCategoriesDropExpanded.value = false
    }


    fun insertExpense(categories: Categories, amount: Int) {
        viewModelScope.launch {
            newExpenseRepository.insertFinance(
                FinanceEntity(
                    isIncome = false,
                    amount = amount,
                    category = categories
                )
            )
        }
    }


    fun insertExpenseDirect(category: String, amount: Int) {
        viewModelScope.launch {
            val categories = when (category) {
                "Home" -> Home
                "Transport" -> Transport
                "Restaurant" -> Restaurant
                "Alcohol" -> Alcohol
                "Clothes" -> Clothes
                "Shoes" -> Shoes
                "Phone" -> Phone
                "Presents" -> Presents
                "Pets" -> Pets
                "Health" -> Health
                "Sports" -> Sports
                "Market" -> Market
                else -> Home
            }

            newExpenseRepository.insertFinance(
                FinanceEntity(
                    isIncome = false,
                    amount = amount,
                    category = categories
                )
            )
        }
    }


    init {
        _expenseCategoryList.add(Home)
        _expenseCategoryList.add(Transport)
        _expenseCategoryList.add(Restaurant)
        _expenseCategoryList.add(Alcohol)
        _expenseCategoryList.add(Clothes)
        _expenseCategoryList.add(Shoes)
        _expenseCategoryList.add(Phone)
        _expenseCategoryList.add(Presents)
        _expenseCategoryList.add(Pets)
        _expenseCategoryList.add(Health)
        _expenseCategoryList.add(Sports)
        _expenseCategoryList.add(Market)
    }

}