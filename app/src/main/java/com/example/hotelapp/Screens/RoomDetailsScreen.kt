package com.example.hotelapp.Screens

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hotelapp.R
import com.example.hotelapp.data.PagerModel
import com.example.hotelapp.ui.theme.*
import com.example.hotelapp.viewmodels.RoomScreenViewModel
import com.example.hotelapp.viewmodels.SharedViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import java.util.*


@ExperimentalPagerApi
@Composable
fun RoomDetailScreen(sharedMvvm : SharedViewModel  , navController: NavController)
{
    LazyColumn{
        item {
          Column(modifier       = Modifier
              .fillMaxSize()
              .padding(bottom   = 56.dp))
          {
              GetViewPagerData(sharedMvvm = sharedMvvm)
              Spacer(modifier   = Modifier.height(20.dp))
              Body(sharedMvvm   = sharedMvvm)
              Spacer(modifier   = Modifier.height(20.dp))
              Footer(sharedMvvm = sharedMvvm , navController = navController)
              Spacer(modifier   = Modifier.height(20.dp))
          }
        }
    }

}

@Composable
fun Body(sharedMvvm: SharedViewModel , roomMvvm : RoomScreenViewModel = hiltViewModel())
{

    val sharedData = sharedMvvm.sharedData
    val context    = LocalContext.current
    val scope      = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp))
    {


        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            sharedData?.name?.let {
                Text(
                    text        = it         ,
                    color       = baseColor  ,
                    fontSize    = 18.sp      ,
                    fontWeight  = FontWeight.Bold,
                    fontFamily  = landFont
                )
            }

            //todo save favorite rooms
           IconButton(onClick = {
                sharedData?.let { scope.launch { roomMvvm.upsertRoom(it) }
             }
           }) {
               Icon(imageVector = Icons.Default.Favorite, contentDescription = "" , tint = baseColor)
           }
        }


        Spacer(modifier = Modifier.height(height = 10.dp))
        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

        Row{
            Icon(
                painter             =  painterResource(id = R.drawable.map),
                contentDescription  =  "Location",
                modifier            = Modifier
                    .size(15.dp)
                    .padding(bottom = 3.dp)     ,
                tint                =  baseColor
            )

            Spacer(modifier = Modifier.width(5.dp))

            sharedData?.location?.let {
                Text(
                    text       = it       ,
                    fontSize   = 15.sp    ,
                    color      = TextColor,
                    fontFamily = landFont
                )
            }
        }

           if (sharedData?.status == "avalable")
           {
               Icon(
                   painter = painterResource(id = R.drawable.ic_online),
                   contentDescription = "avalable",
                   tint = online
               )
           }
            else
           {
               Icon(painter = painterResource(id = R.drawable.ic_offline), contentDescription = "avalable" , tint = offline )
           }
        }

        Spacer(modifier = Modifier.height(height = 10.dp))
        sharedData?.description?.let {
            Text(
                text       = it,
                fontSize   = 13.sp,
                color      = TextColor,
                fontFamily = landFont,
                modifier   = Modifier.fillMaxWidth()
            )
        }
    }

}


@Composable
fun Footer(sharedMvvm: SharedViewModel , navController: NavController)
{
    val data = sharedMvvm.sharedData
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

       Row(modifier = Modifier.padding(start = 20.dp)) {
           Text(
               text       = "$",
               fontSize   = 12.sp,
               fontFamily = landFont,
               )

            data?.price?.let {
                Text(
                    text       = it.toString(),
                    fontSize   = 16.sp,
                    fontFamily = landFont,
                    fontWeight = FontWeight.Bold
                )
            }

           Spacer(modifier = Modifier.width(3.dp))
           Text(
               text       = "per night",
               fontSize   =  10.sp,
               fontFamily =  landFont,
                color     =  TextColor
               )
       }

        val context = LocalContext.current
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(50.dp)
                .padding(end = 20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(baseColor)
                .padding(10.dp)
                .clickable {
                    if (data?.status == "avalable") {
                        sharedMvvm.sharedRoomInformation(data = data)
                        navController.navigate(Screens.UserScreenInformation.route)
                        {
                            navController.popBackStack()
                        }
                    } else {
                        Toast
                            .makeText(
                                context,
                                "This is is booked from other user",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text       = "Select Room" ,
                color      = Color.White   ,
                fontSize   = 12.sp         ,
                fontFamily = landFont)
        }

    }
}

@ExperimentalPagerApi
@Composable
fun GetViewPagerData(sharedMvvm : SharedViewModel)
{
    val data = sharedMvvm.sharedData
    if (data != null)
    {
        val list = listOf(
              PagerModel(data.imgUrl),
              PagerModel(data.photos.imgOne),
              PagerModel(data.photos.imgTwo)
        )
        OnBoardingScreen(list)
    }
}

@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(data : List<PagerModel>)
{
    val pageState =  rememberPagerState()
    Column{
        HorizontalPager(
            count    = data.size,
            state    = pageState,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)

        ) { page ->
            PageUi(pagerModel = data[page])
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier              = Modifier.fillMaxWidth()
        ) {
            HorizontalPagerIndicator(
                pagerState = pageState,
                activeColor = baseColor,
            )
        }

        AnimatedVisibility(visible = pageState.currentPage == 0) {}
    }
}

@Composable
fun PageUi(pagerModel : PagerModel)
{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       GlideImage(
           imageModel = pagerModel.img ,
           modifier   = Modifier.clip(RoundedCornerShape(bottomStart = 15.dp , bottomEnd = 15.dp)),
       )
    }
}


