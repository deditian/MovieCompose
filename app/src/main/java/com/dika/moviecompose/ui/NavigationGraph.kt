package com.dika.moviecompose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dika.moviecompose.ui.home.HomeScreenComposable
import com.dika.moviecompose.ui.home.HomeViewModel

@Composable
fun NavigationGraph(homeViewModel: HomeViewModel, navController: NavHostController, onBottomBarVisibilityChanged: (Boolean) -> Unit) {
    NavHost(navController, startDestination = BottomNavigationItems.HomeScreen.route) {
//        composable(Routes.Welcome.route) {
//            onBottomBarVisibilityChanged(false)
//            Welcome(navController = navController)
//        }
        composable(BottomNavigationItems.HomeScreen.route) {
            onBottomBarVisibilityChanged(true)
            HomeScreenComposable(homeViewModel).HomeScreen()
        }
        composable(BottomNavigationItems.Screen2.route) {
            onBottomBarVisibilityChanged(true)
            Screen2()
        }


    }
}