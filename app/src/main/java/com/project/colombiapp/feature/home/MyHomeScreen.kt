package com.project.colombiapp.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import com.project.colombiapp.core.database.FinanceEntity
import com.project.colombiapp.navigation.Routes
import com.project.colombiapp.ui.theme.BlueSecondary
import com.project.colombiapp.ui.theme.GraySecondary
import com.project.colombiapp.ui.theme.GreenIncome
import com.project.colombiapp.ui.theme.GreenIncomeAlpha
import com.project.colombiapp.ui.theme.RedExpense
import com.project.colombiapp.ui.theme.RedWelcome
import com.project.colombiapp.ui.theme.RedWelcomeLight
import java.text.NumberFormat
import java.time.LocalDate

@Composable
fun MyHomeScreen(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HomeTopAppBar()
        HomeBody(Modifier.weight(1f), navController, homeViewModel)
        HomeBottomBar(navController)
    }
}


@Composable
fun HomeBottomBar(navController: NavHostController) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(4.dp, RedExpense),
                modifier = Modifier
                    .size(120.dp)
                    .clickable {
                        navController.navigate(Routes.NewExpenseScreen.createRoute(false, "not"))
                    }
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "-",
                        fontSize = 70.sp,
                        color = RedExpense,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(4.dp, GreenIncome),
                modifier = Modifier
                    .size(120.dp)
                    .clickable {
                        navController.navigate(Routes.NewIncomeScreen.route)
                    }
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "+",
                        fontSize = 70.sp,
                        color = GreenIncome,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(GraySecondary))
    }
}


@Composable
fun HomeBody(modifier: Modifier, navController: NavHostController, homeViewModel: HomeViewModel) {
    val month = LocalDate.now().month
    val moneyFormat = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
    }

    val incomeList = homeViewModel.incomeList
    val expenseList = homeViewModel.expenseList
    val isShowCategoryDetailVisible: Boolean by homeViewModel.isShowCategoryDetailVisible.observeAsState(
        false
    )
    val categoryDetailInfo by homeViewModel.categoryDetailInfo.observeAsState(
        FinanceEntity(
            isIncome = false,
            amount = 0,
            category = Categories.Home
        )
    )

    var incomeTotal = 0
    incomeList.forEach { incomeTotal += it.amount }

    var expenseTotal = 0
    expenseList.forEach { expenseTotal += it.amount }

    val balance = incomeTotal - expenseTotal

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = month.name,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = GraySecondary
        )
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CategoryItem(
                image = R.drawable.home,
                category = Categories.Home,
                navController,
                homeViewModel,
                onLongPress = { homeViewModel.showCategoryDetail(it) }
            )
            CategoryItem(
                image = R.drawable.taxi,
                category = Categories.Transport,
                navController,
                homeViewModel,
                onLongPress = { homeViewModel.showCategoryDetail(it) }
            )
            CategoryItem(
                image = R.drawable.fork,
                category = Categories.Restaurant,
                navController,
                homeViewModel,
                onLongPress = { homeViewModel.showCategoryDetail(it) }
            )
            CategoryItem(
                image = R.drawable.alcohol,
                category = Categories.Alcohol,
                navController,
                homeViewModel,
                onLongPress = { homeViewModel.showCategoryDetail(it) }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(250.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                CategoryItem(
                    image = R.drawable.bag,
                    category = Categories.Market,
                    navController,
                    homeViewModel,
                    onLongPress = { homeViewModel.showCategoryDetail(it) }
                )
                CategoryItem(
                    image = R.drawable.balls,
                    category = Categories.Sports,
                    navController,
                    homeViewModel,
                    onLongPress = { homeViewModel.showCategoryDetail(it) }
                )
            }

            Card(
                shape = CircleShape,
                border = BorderStroke(
                    30.dp,
                    if (isShowCategoryDetailVisible) BlueSecondary else GreenIncome
                ),
                modifier = Modifier.size(180.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isShowCategoryDetailVisible) GreenIncome else Color.Transparent
                )
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isShowCategoryDetailVisible) {
                        Text(
                            text = categoryDetailInfo.category.name,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = moneyFormat.format(categoryDetailInfo.amount),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 14.sp
                        )

                    } else {
                        Text(
                            text = moneyFormat.format(incomeTotal),
                            fontWeight = FontWeight.Bold,
                            color = GreenIncome,
                            fontSize = 16.sp
                        )
                        Text(
                            text = moneyFormat.format((expenseTotal * (-1))),
                            fontWeight = FontWeight.SemiBold,
                            color = RedExpense,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                CategoryItem(
                    image = R.drawable.shirt,
                    category = Categories.Clothes,
                    navController,
                    homeViewModel,
                    onLongPress = { homeViewModel.showCategoryDetail(it) }
                )
                CategoryItem(
                    image = R.drawable.shoes,
                    category = Categories.Shoes,
                    navController,
                    homeViewModel,
                    onLongPress = { homeViewModel.showCategoryDetail(it) }
                )
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CategoryItem(
                image = R.drawable.healt,
                category = Categories.Health,
                navController,
                homeViewModel,
                onLongPress = { homeViewModel.showCategoryDetail(it) }
            )
            CategoryItem(
                image = R.drawable.dog,
                category = Categories.Pets,
                navController,
                homeViewModel,
                onLongPress = { homeViewModel.showCategoryDetail(it) }
            )
            CategoryItem(
                image = R.drawable.gitf_pink,
                category = Categories.Presents,
                navController,
                homeViewModel,
                onLongPress = { homeViewModel.showCategoryDetail(it) }
            )
            CategoryItem(
                image = R.drawable.phone,
                category = Categories.Phone,
                navController,
                homeViewModel,
                onLongPress = { homeViewModel.showCategoryDetail(it) }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(if (balance >= 0) GreenIncome else RedExpense)
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Balance: ${moneyFormat.format(balance)}",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null,
                    tint = GreenIncome,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}


@Composable
fun CategoryItem(
    image: Int,
    category: Categories,
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    onLongPress: (Categories) -> Unit
) {
    var showCircle by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .size(65.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        if (tryAwaitRelease()) {
                            homeViewModel.hideCategoryDetail()
                            showCircle = false
                        }
                    },
                    onLongPress = {
                        showCircle = true
                        onLongPress(category)
                    },
                    onTap = {
                        navController.navigate(
                            Routes.NewExpenseScreen.createRoute(true, category.name)
                        )
                    }
                )
            }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            if (showCircle) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(GreenIncomeAlpha)
                )
            }
        }
    }
}


@Composable
fun HomeTopAppBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(RedWelcome)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.padding(horizontal = 12.dp)) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.size(60.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_app),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            Text(
                text = "Financial control",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(RedWelcomeLight)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(12.dp)
                    .background(RedWelcomeLight)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(12.dp)
                    .background(RedWelcomeLight)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .height(12.dp)
                    .background(RedWelcomeLight)
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun MyPreview() {
    MyHomeScreen()
}