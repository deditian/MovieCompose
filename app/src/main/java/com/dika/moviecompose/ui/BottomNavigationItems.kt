package com.dika.moviecompose.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItems(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object Screen1 : BottomNavigationItems(
        route = "popular",
        title = "Popular",
        icon = Icons.Outlined.Home
    )
    object Screen2 : BottomNavigationItems(
        route = "now_playing",
        title = "Now Playing",
        icon = Icons.Outlined.AddCircle
    )
}