package com.project.colombiapp.feature.new_expense

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.colombiapp.R
import com.project.colombiapp.core.Categories
import com.project.colombiapp.ui.components.MyCalculator
import com.project.colombiapp.ui.components.MyChooseCategoryButton
import com.project.colombiapp.ui.components.MyNoteTextField
import com.project.colombiapp.ui.theme.GraySecondary
import com.project.colombiapp.ui.theme.GreenIncome
import com.project.colombiapp.ui.theme.RedExpense
import com.project.colombiapp.ui.theme.RedWelcome
import java.text.NumberFormat
import java.time.LocalDate

@Composable
fun MyNewExpenseScreen(
    isCategory: Boolean,
    category: String,
    navController: NavHostController = rememberNavController(),
    newExpenseViewModel: NewExpenseViewModel = hiltViewModel()
) {
    val isExpenseCategoryVisible: Boolean by newExpenseViewModel.isExpenseCategoryVisible.observeAsState(
        false
    )
    val amount: Int by newExpenseViewModel.amount.observeAsState(0)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NewExpenseTopAppBar(
            isExpenseCategoryVisible,
            hideExpenseCategory = { newExpenseViewModel.hideExpenseCategory() },
            onCancelClick = { navController.popBackStack() }
        )

        NewExpenseInformation(
            amount,
            isExpenseCategoryVisible
        ) { newExpenseViewModel.onDeleteDigitClick() }

        if (isExpenseCategoryVisible) {
            ExpenseCategorySelected(Modifier.weight(1f), newExpenseViewModel, navController, amount)
        } else {
            NewExpenseCalculator(
                Modifier.weight(1f),
                newExpenseViewModel,
                isCategory,
                category,
                amount,
                navController
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(GraySecondary))
    }

    BackHandler {
        if (isExpenseCategoryVisible) {
            newExpenseViewModel.hideExpenseCategory()
        } else {
            navController.popBackStack()
        }
    }
}


@Composable
fun NewExpenseCalculator(
    modifier: Modifier,
    newExpenseViewModel: NewExpenseViewModel,
    isCategory: Boolean,
    category: String,
    amount: Int,
    navController: NavHostController
) {
    val noteText: String by newExpenseViewModel.noteText.observeAsState("")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        MyNoteTextField(noteText) { newExpenseViewModel.onNoteTextChange(it) }

        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 12.dp)
        ) {
            MyCalculator(
                modifier = Modifier,
                onNumberClick = { newExpenseViewModel.numberItemClick(it) },
                onSignClick = { newExpenseViewModel.signItemClick(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (isCategory) {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, GreenIncome),
                    colors = CardDefaults.cardColors(containerColor = RedExpense),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable {
                            newExpenseViewModel.insertExpenseDirect(category, amount)
                            navController.popBackStack()
                        }
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Add to '$category'",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }

            } else {
                MyChooseCategoryButton(
                    containerColor = RedExpense,
                    textColor = Color.White,
                    onButtonClick = { newExpenseViewModel.showExpenseCategory() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun ExpenseCategorySelected(
    modifier: Modifier,
    newExpenseViewModel: NewExpenseViewModel,
    navController: NavHostController,
    amount: Int
) {
    val expenseCategoryList = newExpenseViewModel.expenseCategoryList
    val expenseCategorySelected: Categories by newExpenseViewModel.expenseCategorySelected.observeAsState(
        Categories.Home
    )
    val isCategoriesDropExpanded: Boolean by newExpenseViewModel.isCategoriesDropExpanded.observeAsState(
        false
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(text = "Select the expense category:", color = GraySecondary)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = expenseCategorySelected.name,
                onValueChange = { },
                label = {
                    Text(text = "Choose category")
                },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { newExpenseViewModel.showCategoriesDrop() },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = GraySecondary,
                    disabledTextColor = GraySecondary,
                    disabledLabelColor = GraySecondary
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = GraySecondary
                    )
                }
            )
            DropdownMenu(
                expanded = isCategoriesDropExpanded,
                onDismissRequest = { newExpenseViewModel.hideCategoriesDrop() },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                expenseCategoryList.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name, color = GraySecondary) },
                        onClick = {
                            newExpenseViewModel.hideCategoriesDrop()
                            newExpenseViewModel.onExpenseCategorySelected(it)
                        },
                        modifier = Modifier.height(30.dp)
                    )
                }
            }
        }

        Button(
            onClick = {
                newExpenseViewModel.insertExpense(expenseCategorySelected, amount)
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedWelcome)
        ) {
            Text(
                text = "Save",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}


@Composable
fun NewExpenseInformation(amount: Int, isExpenseCategoryVisible: Boolean, deleteDigit: () -> Unit) {
    val date = LocalDate.now()
    val moneyFormat = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${date.dayOfWeek}, ${date.month} ${date.dayOfMonth}",
                color = GraySecondary
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(if (isExpenseCategoryVisible) RedWelcome else RedExpense)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Text(text = "USD", color = Color.White, fontWeight = FontWeight.Light)
            }
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(50.dp)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = moneyFormat.format(amount),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.width(4.dp))

            if (!isExpenseCategoryVisible) {
                IconButton(
                    onClick = {
                        deleteDigit()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun NewExpenseTopAppBar(
    isExpenseCategoryVisible: Boolean,
    hideExpenseCategory: () -> Unit,
    onCancelClick: () -> Boolean
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(if (isExpenseCategoryVisible) GraySecondary else RedExpense)
            .padding(8.dp)
    ) {
        TextButton(
            onClick = {
                if (isExpenseCategoryVisible) hideExpenseCategory() else onCancelClick()
            },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = if (isExpenseCategoryVisible) "Back" else "Cancel",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Text(
            text = if (isExpenseCategoryVisible) "Expenses categories" else "New expense",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview
@Composable
private fun MyPreview() {
    MyNewExpenseScreen(isCategory = false, category = "Category")
}