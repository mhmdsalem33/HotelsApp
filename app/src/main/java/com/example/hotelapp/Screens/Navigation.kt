package com.example.hotelapp.Screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hotelapp.viewmodels.SharedViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier)
{

    val sharedMvvm : SharedViewModel = viewModel()

        NavHost(navController = navController, startDestination = Screen.Home.route){

            composable(Screen.Home.route)
            {
                HomeScreen(navController)
            }
            composable(Screen.Favorite.route)
            {
                FavoriteScreen()
            }
            composable(Screen.Booked.route)
            {
                BookedScreen()
            }

            composable(
                Screens.Room.route + "?id={id}" + "&name={name}" +"&type={type}" ,
                arguments = listOf(
                    navArgument(name = "id")
                    {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument(name = "name")
                    {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument(name = "type")
                    {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            )

            {
                RoomScreen(
                    id   = it.arguments?.getString("id")   ,
                    name = it.arguments?.getString("name") ,
                    type = it.arguments?.getString("type") ,
                    navController = navController,
                    sharedMvvm    = sharedMvvm
                )
            }

            composable(Screens.RoomDetailsScreen.route)
            {
                RoomDetailScreen( sharedMvvm    = sharedMvvm , navController = navController)
            }

            composable(Screens.UserScreenInformation.route)
            {
                UserScreenInformation(sharedMvvm    = sharedMvvm)
            }

        }





}