package com.project.colombiapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.colombiapp.R
import com.project.colombiapp.ui.theme.GraySecondary
import com.project.colombiapp.ui.theme.GreenIncome


@Composable
fun MyNoteTextField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(text = "Add note")
        },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.comment_green),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedPlaceholderColor = GraySecondary,
            unfocusedPlaceholderColor = GraySecondary,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = GraySecondary
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        maxLines = 2
    )
}


@Composable
fun MyCalculator(
    modifier: Modifier = Modifier,
    onNumberClick: (Int) -> Unit,
    onSignClick: (Operations) -> Unit
) {
    Column(
        modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorItem("1") { onNumberClick(1) }
            CalculatorItem("2") { onNumberClick(2) }
            CalculatorItem("3") { onNumberClick(3) }
            CalculatorItem("+") { onSignClick(Operations.Plus) }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorItem("4") { onNumberClick(4) }
            CalculatorItem("5") { onNumberClick(5) }
            CalculatorItem("6") { onNumberClick(6) }
            CalculatorItem("-") { onSignClick(Operations.Minus) }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorItem("7") { onNumberClick(7) }
            CalculatorItem("8") { onNumberClick(8) }
            CalculatorItem("9") { onNumberClick(9) }
            CalculatorItem("*") { onSignClick(Operations.Multiplication) }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorItem(".") { }
            CalculatorItem("0") { onNumberClick(0) }
            CalculatorItem("=") { onSignClick(Operations.Equals) }
            CalculatorItem("/") { onSignClick(Operations.Division) }
        }
    }
}

@Composable
fun CalculatorItem(digit: String, onCalculatorItemClick: () -> Unit) {
    var isPress by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, GreenIncome),
        colors = CardDefaults.cardColors(containerColor = if (isPress) GreenIncome else Color.Transparent),
        modifier = Modifier
            .width(80.dp)
            .height(60.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPress = true
                        onCalculatorItemClick()

                        if (tryAwaitRelease()) {
                            isPress = false
                        }
                    }
                )
            }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = digit,
                color = GraySecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}


@Composable
fun MyChooseCategoryButton(
    containerColor: Color,
    textColor: Color,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, GreenIncome),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = modifier
            .height(60.dp)
            .clickable { onButtonClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Choose category",
                color = textColor,
                fontSize = 20.sp
            )
        }
    }
}

























