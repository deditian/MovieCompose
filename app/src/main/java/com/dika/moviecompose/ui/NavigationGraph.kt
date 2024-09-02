package com.dika.moviecompose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController, onBottomBarVisibilityChanged: (Boolean) -> Unit) {
    NavHost(navController, startDestination = BottomNavigationItems.HomeScreen.route) {
//        composable(Routes.Welcome.route) {
//            onBottomBarVisibilityChanged(false)
//            Welcome(navController = navController)
//        }
        composable(BottomNavigationItems.HomeScreen.route) {
            onBottomBarVisibilityChanged(true)
            HomeScreen()
        }
        composable(BottomNavigationItems.Screen2.route) {
            onBottomBarVisibilityChanged(true)
            Screen2()
        }


    }
}