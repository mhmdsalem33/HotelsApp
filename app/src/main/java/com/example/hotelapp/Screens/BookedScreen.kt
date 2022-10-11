package com.example.hotelapp.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hotelapp.R
import com.example.hotelapp.data.PagerModel
import com.example.hotelapp.data.RoomModel
import com.example.hotelapp.ui.theme.Typography
import com.example.hotelapp.ui.theme.baseColor
import com.example.hotelapp.ui.theme.contentChip
import com.example.hotelapp.viewmodels.BookedViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun BookedScreen(bookedMvvm :BookedViewModel = hiltViewModel())
{

    val bookedData by bookedMvvm.getBookedData.collectAsState(initial =  emptyList())
     Column(modifier = Modifier
         .fillMaxSize()
         .padding(bottom = 70.dp)) {
           LazyColumn{
             items(bookedData)
            {
                BookedDesign(it)
            }
      }
  }

}



@ExperimentalPagerApi
@Composable
fun BookedDesign(data : RoomModel , bookedMvvm: BookedViewModel = hiltViewModel())
{
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth())
    {
        Text(
            text = "Rooms Booked",
            fontFamily = Typography.h1.fontFamily,
            fontSize = 20.sp,
            color = baseColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp , top = 20.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

           Box(modifier = Modifier
               .padding(start = 20.dp, end = 20.dp)
               .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))) {
               val list = listOf(
                   PagerModel(data.imgUrl),
                   PagerModel(data.photos.imgOne),
                   PagerModel(data.photos.imgTwo)
               )
               OnBoardingScreen(list)
           }

        Spacer(modifier = Modifier.height(10.dp))

        Column(  modifier              = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth()  ,
                horizontalArrangement = Arrangement.SpaceBetween  ,
                verticalAlignment     = Alignment.CenterVertically)
            {
                Text(
                    text        = data.name                ,
                    fontFamily  = Typography.h1.fontFamily ,
                    fontSize    = 15.sp)
                Row{
                    Text(
                        text       = "$" ,
                        fontSize   = 10.sp  ,
                        fontFamily = Typography.h1.fontFamily)

                    Text(
                        text       = data.price.toString()   ,
                        fontSize   = 15.sp                   ,
                        fontFamily = Typography.h1.fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text       = "per night" ,
                        fontSize   = 10.sp  ,
                        fontFamily = Typography.h1.fontFamily ,
                        color      = contentChip)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row() {
               Icon(
                   painter             = painterResource(id = R.drawable.map),
                   contentDescription  = "" ,
                   modifier            = Modifier.size(15.dp)
               )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text       = data.location ,
                    fontSize   = 10.sp  ,
                    fontFamily = Typography.h1.fontFamily ,
                    color      = contentChip)

            }
            
            Spacer(modifier = Modifier.height(3.dp))
            
            Box(contentAlignment = Alignment.Center , modifier = Modifier.fillMaxWidth()

            )
            {
                Box(contentAlignment = Alignment.Center  ,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(baseColor)
                        .padding(start = 15.dp  , end = 15.dp, top = 10.dp    ,  bottom = 10.dp)
                        .clickable { bookedMvvm.deleteRoomBooked( data.id  ,  context = context) }
                )
                {
                    Text(
                        text         = "Cancel",
                        fontSize     =  15.sp,
                        fontFamily   =  Typography.h1.fontFamily,
                        color        =  Color.White
                    )
                }   
            }
        }
    }

}






