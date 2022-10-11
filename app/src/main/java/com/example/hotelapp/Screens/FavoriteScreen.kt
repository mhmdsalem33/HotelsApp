package com.example.hotelapp.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
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
import com.example.hotelapp.data.PhotosModel
import com.example.hotelapp.data.RoomModel
import com.example.hotelapp.ui.theme.Typography
import com.example.hotelapp.ui.theme.baseColor
import com.example.hotelapp.ui.theme.contentChip
import com.example.hotelapp.viewmodels.RoomScreenViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.skydoves.landscapist.glide.GlideImage


@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun FavoriteScreen(roomMvvm : RoomScreenViewModel = hiltViewModel()){

    val savedRooms by   roomMvvm.getSavedRooms.collectAsState(initial = emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 56.dp))
    {

            if (savedRooms.isEmpty())
            {
                Box(contentAlignment = Alignment.Center , modifier = Modifier.fillMaxSize()) {
                  Column(modifier = Modifier.fillMaxWidth() , horizontalAlignment = Alignment.CenterHorizontally) {
                      Text(
                          text  = "No Favorite Items Added",
                          color = baseColor
                          )
                      Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite Empty" , tint = baseColor)
                  }
                }
            }

            Text(
                text       = "Favorite Rooms",
                fontFamily = Typography.h1.fontFamily,
                fontSize   = 20.sp,
                color      = baseColor,
                fontWeight = FontWeight.Bold,
                modifier   = Modifier.padding(start = 20.dp , top = 20.dp))

        LazyColumn{
            itemsIndexed(
                items = savedRooms ,
                key   = { _,listItem -> listItem.hashCode() }
            ){ index , item ->
                val state =  rememberDismissState(    //todo remember if item delete
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart)
                        {
                            roomMvvm.deleteRoom(item)
                        }
                        true
                    }
                )
                SwipeToDismiss(state = state, background = {
                    val color  = when(state.dismissDirection){
                        DismissDirection.StartToEnd -> Color.Transparent
                        DismissDirection.EndToStart -> Color.Transparent
                        null -> Color.Transparent
                    }

                } ,
                    dismissContent = {
                        FavoriteDesign(item)
                    },

                    directions = setOf(DismissDirection.EndToStart))
                //   Divider()  // الخط الفاصل تحت
            }
        }
    }
}



@ExperimentalPagerApi
@Composable
fun FavoriteDesign(data : RoomModel  , roomMvvm: RoomScreenViewModel = hiltViewModel())
{
    val context = LocalContext.current
    Column(modifier    = Modifier.fillMaxWidth())
    {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(15.dp))
                .height(300.dp)
        )
        {
                 GlideImage(imageModel = data.imgUrl)
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
                    text        =   data.name                ,
                    fontFamily  =   Typography.h1.fontFamily ,
                    fontSize    =   15.sp,
                    color       =   baseColor)
                Row{
                    Text(
                        text       = "$" ,
                        fontSize   = 10.sp  ,
                        fontFamily = Typography.h1.fontFamily)

                    Text(
                        text       = data.price.toString()   ,
                        fontSize   = 15.sp                   ,
                        fontFamily = Typography.h1.fontFamily,
                        fontWeight = FontWeight.Bold,
                        color      =   baseColor
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text       = "per night" ,
                        fontSize   = 10.sp  ,
                        fontFamily = Typography.h1.fontFamily ,
                        color      = contentChip
                    )
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
                    color      = contentChip
                )

            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

