package com.example.hotelapp.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route :String , val title :String , val icon :ImageVector)
{
    object Home : Screen(
        route = "home",
        title = "Home",
        icon  =  Icons.Default.Home
    )

    object Favorite : Screen(
        route = "favorite",
        title = "Favorite",
        icon  =  Icons.Default.Favorite
    )

    object Booked : Screen(
        route = "booked",
        title = "Booked",
        icon  =  Icons.Default.Book
    )

}
