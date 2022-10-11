package com.example.hotelapp.Screens

sealed class Screens(val route :String)
{
    object Room :Screens(
        route = "RoomScreen"
    )

    object RoomDetailsScreen : Screens(
        route = "RoomDetailsScreen"
    )

    object UserScreenInformation : Screens(
        route = "UserScreenInformation"
    )

}
