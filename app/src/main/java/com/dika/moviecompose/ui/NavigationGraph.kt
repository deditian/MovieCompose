package com.dika.moviecompose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController, onBottomBarVisibilityChanged: (Boolean) -> Unit) {
    NavHost(navController, startDestination = BottomNavigationItems.Screen1.route) {
//        composable(Routes.Welcome.route) {
//            onBottomBarVisibilityChanged(false)
//            Welcome(navController = navController)
//        }
        composable(BottomNavigationItems.Screen1.route) {
            onBottomBarVisibilityChanged(true)
            Screen1()
        }
        composable(BottomNavigationItems.Screen2.route) {
            onBottomBarVisibilityChanged(true)
            Screen2()
        }


    }
}