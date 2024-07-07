package com.project.colombiapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.colombiapp.feature.home.MyHomeScreen
import com.project.colombiapp.feature.new_expense.MyNewExpenseScreen
import com.project.colombiapp.feature.new_income.MyNewIncomeScreen
import com.project.colombiapp.feature.splash.MySplashScreen
import com.project.colombiapp.feature.welcome.MyWelcomeScreen

@Composable
fun MyNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SplashScreen.route) {
        composable(Routes.SplashScreen.route) {
            MySplashScreen(navController)
        }

        composable(Routes.WelcomeScreen.route) {
            MyWelcomeScreen(navController)
        }

        composable(Routes.HomeScreen.route) {
            MyHomeScreen(navController)
        }

        composable(Routes.NewIncomeScreen.route) {
            MyNewIncomeScreen(navController)
        }

        composable(
            Routes.NewExpenseScreen.route,
            arguments = listOf(
                navArgument("isCategory") { type = NavType.BoolType },
                navArgument("category") { type = NavType.StringType }
            )
        ) {
            val isCategory = it.arguments?.getBoolean("isCategory") ?: false
            val category = it.arguments?.getString("category").orEmpty()

            MyNewExpenseScreen(isCategory, category, navController)
        }

    }
}