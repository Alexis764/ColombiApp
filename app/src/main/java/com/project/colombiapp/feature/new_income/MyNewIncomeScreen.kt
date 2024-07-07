package com.project.colombiapp.feature.new_income

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.project.colombiapp.ui.components.MyCalculator
import com.project.colombiapp.ui.components.MyChooseCategoryButton
import com.project.colombiapp.ui.components.MyNoteTextField
import com.project.colombiapp.ui.theme.GraySecondary
import com.project.colombiapp.ui.theme.GreenIncome
import java.text.NumberFormat
import java.time.LocalDate

@Composable
fun MyNewIncomeScreen(
    navController: NavHostController = rememberNavController(),
    newIncomeViewModel: NewIncomeViewModel = hiltViewModel()
) {
    val isIncomeCategoryVisible: Boolean by newIncomeViewModel.isIncomeCategoryVisible.observeAsState(
        false
    )
    val amount: Int by newIncomeViewModel.amount.observeAsState(0)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NewIncomeTopAppBar(
            isIncomeCategoryVisible,
            hideIncomeCategory = { newIncomeViewModel.hideIncomeCategory() },
            onCancelClick = { navController.popBackStack() }
        )

        NewIncomeInformation(amount) { newIncomeViewModel.onDeleteDigitClick() }

        if (isIncomeCategoryVisible) {
            IncomeCategorySelected(Modifier.weight(1f), newIncomeViewModel, navController, amount)
        } else {
            NewIncomeCalculator(Modifier.weight(1f), newIncomeViewModel)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(GraySecondary))
    }

    BackHandler {
        if (isIncomeCategoryVisible) {
            newIncomeViewModel.hideIncomeCategory()
        } else {
            navController.popBackStack()
        }
    }
}


@Composable
fun NewIncomeCalculator(modifier: Modifier, newIncomeViewModel: NewIncomeViewModel) {
    val noteText: String by newIncomeViewModel.noteText.observeAsState("")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        MyNoteTextField(noteText) { newIncomeViewModel.onNoteTextChange(it) }

        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 12.dp)
        ) {
            MyCalculator(
                modifier = Modifier,
                onNumberClick = { newIncomeViewModel.numberItemClick(it) },
                onSignClick = { newIncomeViewModel.signItemClick(it) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            MyChooseCategoryButton(
                containerColor = Color.Transparent,
                textColor = GraySecondary,
                onButtonClick = { newIncomeViewModel.showIncomeCategory() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun IncomeCategorySelected(
    modifier: Modifier,
    newIncomeViewModel: NewIncomeViewModel,
    navController: NavHostController,
    amount: Int
) {
    val incomeCategoryList = newIncomeViewModel.incomeCategoryList

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(text = "Select the income category:", color = GraySecondary)
            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(incomeCategoryList) {
                    IncomeCategoryItem(it) { newIncomeCategory ->
                        newIncomeViewModel.onIncomeCategorySelectedChange(newIncomeCategory)
                    }
                }
            }
        }

        Button(
            onClick = {
                newIncomeViewModel.insertIncome(incomeCategoryList.find { it.isSelected }!!, amount)
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenIncome)
        ) {
            Text(
                text = "Register Income",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun IncomeCategoryItem(
    newIncomeCategory: NewIncomeCategory,
    onItemClick: (NewIncomeCategory) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, GraySecondary),
        modifier = Modifier
            .size(100.dp)
            .clickable { onItemClick(newIncomeCategory) },
        colors = CardDefaults.cardColors(
            containerColor = if (newIncomeCategory.isSelected) GreenIncome else Color.Transparent
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = newIncomeCategory.image),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = newIncomeCategory.name.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = if (newIncomeCategory.isSelected) Color.White else GraySecondary
                )
            }
        }
    }
}


@Composable
fun NewIncomeInformation(amount: Int, deleteDigit: () -> Unit) {
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
                .background(GreenIncome)
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


@Composable
fun NewIncomeTopAppBar(
    isIncomeCategoryVisible: Boolean,
    hideIncomeCategory: () -> Unit,
    onCancelClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(GreenIncome)
            .padding(8.dp)
    ) {
        TextButton(
            onClick = {
                if (isIncomeCategoryVisible) hideIncomeCategory() else onCancelClick()
            },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = if (isIncomeCategoryVisible) "Back" else "Cancel",
                fontWeight = if (isIncomeCategoryVisible) FontWeight.Normal else FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Text(
            text = "New income",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview(showSystemUi = true)
@Composable
private fun MyPreview() {
    MyNewIncomeScreen()
}