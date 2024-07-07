package com.project.colombiapp.navigation

sealed class Routes(val route: String) {
    data object SplashScreen : Routes("splashScreen")

    data object WelcomeScreen : Routes("welcomeScreen")

    data object HomeScreen : Routes("homeScreen")

    data object NewIncomeScreen : Routes("newIncomeScreen")

    data object NewExpenseScreen : Routes("newExpenseScreen/{isCategory}/{category}") {
        fun createRoute(isCategory: Boolean, category: String): String {
            return "newExpenseScreen/$isCategory/$category"
        }
    }
}