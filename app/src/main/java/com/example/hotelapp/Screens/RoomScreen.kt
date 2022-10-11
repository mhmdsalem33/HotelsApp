package com.example.hotelapp.Screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.hotelapp.R
import com.example.hotelapp.data.PhotosModel
import com.example.hotelapp.data.RoomModel
import com.example.hotelapp.ui.theme.*
import com.example.hotelapp.viewmodels.RoomScreenViewModel
import com.example.hotelapp.viewmodels.SearchViewModel
import com.example.hotelapp.viewmodels.SharedViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun RoomScreen(id:String? , name :String? , type :String? , navController: NavController , sharedMvvm :SharedViewModel )
{

    BackPress(navController = navController)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 56.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Text(
            text       =  name.toString(),
            color      =  baseColor,
            fontSize   =  22.sp
        )
        Text(
            text       = "Rooms" ,
            color      =  baseColor,
            fontSize   =  18.sp
        )

        Spacer(modifier =  Modifier.height(10.dp))
        val textSearch  =  rememberSaveable{ mutableStateOf("") }

        OutlinedTextField(
            value         =    textSearch.value ,
            onValueChange = {  textSearch.value = it },
            textStyle     =    Typography.h1,
            colors        =    TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor      = Color(0xFFFFFFFF),
                focusedBorderColor   = baseColor,
                unfocusedBorderColor = baseColor,
                textColor            = Color.Black,
                cursorColor          = baseColor,
                unfocusedLabelColor  = baseColor,
                focusedLabelColor    = baseColor,
                leadingIconColor     = baseColor


            ),
            label      = { Text(text = "Search for $name Rooms")},
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(10.dp))

        GetRoomData(
            hotelId       =  id.toString()      ,
            type          = type.toString()     ,
            searchText    = textSearch.value    ,
            navController = navController       ,
            sharedMvvm    = sharedMvvm)

       GetOfferRoomData(id.toString() , type = type.toString() , sharedMvvm = sharedMvvm , navController = navController)

    }
}


@Composable
fun GetRoomData(
    roomMvvm      :  RoomScreenViewModel = hiltViewModel() ,
    hotelId       :  String           ,
    type          :  String           ,
    searchText    :  String           ,
    navController :  NavController    ,
    sharedMvvm    :  SharedViewModel  ,
    searchMvvm    :  SearchViewModel = hiltViewModel()
)
{
         val context = LocalContext.current
         roomMvvm.getRoomData(hotelId , type)
         searchMvvm.searchInFirestore( id = hotelId , type = type   , searchText = searchText,  context = context )

    val data by roomMvvm.getRoomData.collectAsState(initial = emptyList())
    val searchData by searchMvvm.getSearchProductsData.collectAsState(initial =  emptyList())


    Log.d("testApp" , searchData.toString())

    LazyRow{
        if (searchData.isEmpty())
        {
            items(data)
            {
                RoomDesign(data = it , navController = navController , sharedMvvm = sharedMvvm)
            }
        }
        else
        {
            items(searchData) {
                RoomDesign(data = it , navController = navController , sharedMvvm = sharedMvvm)
            }
        }
    }
}

@Composable
fun RoomDesign(data : RoomModel , navController: NavController , sharedMvvm :SharedViewModel)
{

    Column(
        modifier = Modifier
            .padding(end = 10.dp)
            .width(250.dp)
            .clickable {
                sharedMvvm.viewPagerData(data)
                navController.navigate(Screens.RoomDetailsScreen.route)
                {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }

            }
    )
    {
        GlideImage(
            imageModel = data.imgUrl,
            modifier   = Modifier
                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier.fillMaxWidth()) {
            Text(
                text       =  data.name ,
                fontFamily =  landFont  ,
                fontWeight =  FontWeight.Bold,
                fontSize   =  15.sp   ,
                color      =  baseColor
            )
            if (data.status == "avalable")
            {
                Icon(
                    painter            = painterResource(id = R.drawable.ic_online),
                    contentDescription = "status of room",
                    tint               = online
                )
            }
            else
            {
                Icon(
                    painter            = painterResource(id = R.drawable.ic_offline),
                    contentDescription = "status of room",
                    tint               = offline
                )
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter             = painterResource(id = R.drawable.map),
                    contentDescription  = "Location",
                    tint                = baseColor,
                    modifier            = Modifier
                        .padding(bottom = 2.dp)
                        .size(12.dp)
                )
                Text(
                    text       =   data.location,
                    fontFamily =   Typography.h1.fontFamily,
                    fontSize   =   13.sp,
                    color      =   baseColor
                )
            }
            Row{
                Text(
                    text       =  "$",
                    fontSize   =  13.sp,
                    fontFamily =  Typography.h1.fontFamily,
                    color      =  baseColor
                )
                Text(
                    text       =  data.price.toString(),
                    fontFamily =  Typography.h1.fontFamily,
                    fontWeight =  FontWeight.Bold,
                    color      =  baseColor,
                    fontSize   =  14.sp,
                )
            }
        }

    }
}

@Composable
fun GetOfferRoomData( hotelId: String  , type: String, roomMvvm: RoomScreenViewModel = hiltViewModel() , sharedMvvm: SharedViewModel , navController: NavController )
{
                 roomMvvm.getOffersRoomData(hotelId = hotelId , type = type)
             val data by  roomMvvm.getOfferRoomLiveData.collectAsState()

     if (data.isNotEmpty())
     {
         data?.let {
             Offers(data = it.first(), sharedMvvm = sharedMvvm , navController = navController)
         }
     }
}

@Composable
fun Offers(data   :RoomModel , sharedMvvm: SharedViewModel , navController: NavController){
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .clickable {
                sharedMvvm.viewPagerData(data)
                navController.navigate(Screens.RoomDetailsScreen.route)
                {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
    ){
        Text(
            text       = "Offers",
            fontSize   = 15.sp,
            color      = baseColor,
            fontFamily = Typography.h1.fontFamily,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))

        GlideImage(
            imageModel = data.imgUrl ,
            Modifier
                .padding(bottom = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .height(200.dp)
            )
        Spacer(modifier = Modifier.height(5.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier.fillMaxWidth()) {
            Text(
                text       =  data.name ,
                fontFamily =  landFont  ,
                fontWeight =  FontWeight.Bold,
                fontSize   =  15.sp   ,
                color      =  baseColor
            )
          if (data.status == "avalable")
          {
              Icon(
                  painter            = painterResource(id = R.drawable.ic_online),
                  contentDescription = "status of room",
                  tint               = online
              )
          }
            else
          {
              Icon(
                  painter            = painterResource(id = R.drawable.ic_offline),
                  contentDescription = "status of room",
                  tint               = offline
              )
          }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter             = painterResource(id = R.drawable.map),
                    contentDescription  = "Location",
                    tint                = baseColor,
                    modifier            = Modifier
                        .padding(bottom = 2.dp)
                        .size(12.dp)
                )
                Text(
                    text       =   data.location,
                    fontFamily =   Typography.h1.fontFamily,
                    fontSize   =   13.sp,
                    color      =   baseColor
                )
            }
            Row{
                Text(
                    text       =  "$",
                    fontSize   =  13.sp,
                    fontFamily =  Typography.h1.fontFamily,
                    color      =  baseColor
                )
                Text(
                    text       =  data.price.toString(),
                    fontFamily =  Typography.h1.fontFamily,
                    fontWeight =  FontWeight.Bold,
                    color      =  baseColor,
                    fontSize   =  14.sp,
                )
            }
        }
    }
}


@Composable
fun BackPress(navController: NavController){
    var backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        // Handle back press
        navController.navigate(Screen.Home.route){
            navController.popBackStack()
        }
    }
}
