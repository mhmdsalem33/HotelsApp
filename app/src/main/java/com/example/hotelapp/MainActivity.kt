package com.example.hotelapp

import com.example.hotelapp.Screens.Navigation
import com.example.hotelapp.Screens.Screen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hotelapp.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagerApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotelAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyNavigation()
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MyNavigation()
{
    val navController =  rememberNavController()
    val items         =  listOf(Screen.Home  , Screen.Favorite , Screen.Booked)
    Scaffold(
        bottomBar = {
            BottomNavigation (
              //  modifier         = Modifier.border(border = BorderStroke(width = 1.dp , Color.LightGray)),
                backgroundColor  = Color.White,
            ){
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach{ screen ->
                    BottomNavigationItem(
                        icon =  {
                            Icon(
                            imageVector        = screen.icon,
                            contentDescription = "Icons" ,
                            tint = if (currentRoute == screen.route) baseColor else TextColor ) },
                       // label    = { Text(text       = screen.title)},
                        selected = currentRoute == screen.route,
                        onClick  = {
                            navController.navigate(screen.route){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop  =  true
                                restoreState     =  true
                            }
                        })
                }
            }
        }
    ) { innerPadding ->
        Navigation(navController = navController , modifier = Modifier.padding(innerPadding) )
    }
}
