package com.project.colombiapp.feature.welcome

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.colombiapp.R
import com.project.colombiapp.navigation.Routes
import com.project.colombiapp.ui.theme.RedWelcome
import com.project.colombiapp.ui.theme.YellowText

@Composable
fun MyWelcomeScreen(
    navController: NavHostController = rememberNavController(),
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RedWelcome)
    ) {
        CloseButton(Modifier.align(Alignment.TopEnd)) {
            welcomeViewModel.setFirstTime()
            navController.navigate(Routes.HomeScreen.route) {
                popUpTo(Routes.WelcomeScreen.route) {
                    inclusive = true
                }
            }
        }

        WelcomeBody(Modifier.align(Alignment.Center))

        WelcomeBottom(Modifier.align(Alignment.BottomCenter))
    }
}


@Composable
fun WelcomeBottom(modifier: Modifier) {
    Row(
        modifier = modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Powered by", color = Color.White, fontSize = 20.sp)

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "RapiCredit",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}


@Composable
fun WelcomeBody(modifier: Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This is where\ncontrol of your money",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(72.dp))

        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.size(150.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.gift),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Become your own\nbudget hero",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = YellowText,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        Column {
            Text(text = "-Organize your expenses by category", color = Color.White)
            Text(text = "-Record income to a category", color = Color.White)
            Text(text = "-View income and expense history", color = Color.White)
        }
    }
}


@Composable
fun CloseButton(modifier: Modifier, closeButtonClick: () -> Unit) {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .padding(12.dp)
            .size(30.dp)
            .clickable { closeButtonClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = RedWelcome)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun MyPreview() {
    MyWelcomeScreen()
}