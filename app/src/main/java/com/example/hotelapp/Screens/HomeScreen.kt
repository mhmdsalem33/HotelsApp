package com.example.hotelapp.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.hotelapp.R
import com.example.hotelapp.data.CategoriesModel
import com.example.hotelapp.ui.theme.*
import com.example.hotelapp.viewmodels.HomeViewModel
import com.skydoves.landscapist.glide.GlideImage


var categoryName =  mutableStateOf("Hotel")
@Composable
fun HomeScreen(navController: NavController){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 56.dp)
        .verticalScroll(rememberScrollState())
    ) {
        
        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = buttonFont,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 22.sp,
                    color      = baseColor
                )
            ){
                append("Welcome to \n")
            }
            withStyle(
                style = SpanStyle(
                    fontFamily = buttonFont,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = baseColor)
            ){
                append("Grand Hotels")
            }
        })

        Spacer(modifier = Modifier.height(10.dp))
        ChipGroupCompose()

        Spacer(modifier = Modifier.height(20.dp))

        GetData(navController = navController)
        
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text       = "Popular Hotels" ,
            fontFamily = Typography.h1.fontFamily ,
            fontWeight = FontWeight.Bold ,
            fontSize   = 15.sp,
            color      = baseColor
        )
        Spacer(modifier = Modifier.height(5.dp))
        GetPopularData(navController = navController)
    }
}

@Composable
fun ChipGroupCompose()
{
    val chipList   :List<String> = listOf(
        "Hotel" ,
        "Apartment",
        "Motel"
    )

    var selected by remember{ mutableStateOf("")}

    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    )
    {
        chipList.forEach { it ->
            Chip(
                title      =  it ,
                selected   =  selected  ,
                onSelected =
                {
                    selected = it }
            )
        }
    }

}    @Composable
fun Chip(
    title      :    String ,
    selected   :    String ,
    onSelected :   (String) -> Unit
)
{
    val isSelected = selected == title
    val background   =  if (isSelected) baseColor   else chipBackground
    val contentColor =  if (isSelected) Color.White else contentChip

    Box(
        modifier = Modifier
            .padding(end = 5.dp)
            .height(35.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = {
                    onSelected(title)
                    categoryName.value = title
                })
    ) {
        Row(
            modifier =
            Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnimatedVisibility(visible = isSelected) {
                Icon(imageVector       = Icons.Filled.Check, contentDescription = "Check" , tint = Color.White )
            }

            Text(
                text       = title ,
                color      = contentColor ,
                fontSize   = 16.sp,
                fontFamily = Typography.h1.fontFamily
            )
        }
    }
}

@Composable
fun GetData(homeMvvm : HomeViewModel = hiltViewModel() , navController: NavController)
{
     if (categoryName.value == "Hotel")
     {
         homeMvvm.getCategories("Hotel")
     }
    else
     {
         homeMvvm.getCategories(categoryName.value)
     }

    val data by homeMvvm.getCategoriesStateFlow.collectAsState(initial = emptyList())

    LazyRow{
        items(data){
            CategoriesHomeDesign(data = it , navController = navController)
        }
    }
}


@Composable
fun CategoriesHomeDesign(data : CategoriesModel , navController: NavController){
    
    ConstraintLayout(
        modifier = Modifier.padding(end = 10.dp)
            .clickable {
                navController.navigate(Screens.Room.route
                        + "?id=${data.id}"
                        + "&name=${data.name}"
                        + "&type=${categoryName.value}"

                )
                {
                  //  navController.popBackStack(Screen.Home.route , inclusive = true)
                    navController.popBackStack()
                }
            }
    ) {

        val (imgRef , columnRef) = createRefs()

        GlideImage(
            imageModel = data.imgUrl,
            modifier   = Modifier
                .width(200.dp)
                .height(220.dp)
                .clip(RoundedCornerShape(15.dp))
                .constrainAs(imgRef)
                {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })

            Column(modifier = Modifier
                .padding(bottom = 10.dp, start = 10.dp)
                .constrainAs(columnRef)
                {
                    bottom.linkTo(imgRef.bottom)
                }
            ) {
                Text(
                    text       = data.name,
                    fontFamily = Typography.h1.fontFamily,
                    color      = Color.White,
                    fontSize   = 18.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically){
                     Icon(
                         painter            =  painterResource(id = R.drawable.map),
                         contentDescription =  "Location",
                         tint               =  Color.White,
                         modifier           = Modifier
                             .padding(bottom = 2.dp)
                             .size(15.dp)
                     )
                    Spacer(modifier  = Modifier.width(2.dp))
                     Text(
                         text        = data.location,
                         fontFamily  = Typography.h1.fontFamily,
                         color       = Color.White,
                         fontSize    = 13.sp,
                         )
                }
            }
    }
}


@Composable
fun GetPopularData(homeMvvm: HomeViewModel = hiltViewModel() , navController : NavController)
{ 
         homeMvvm.getPopular()
    val data by homeMvvm.getPopularStateFlow.collectAsState(initial =  emptyList())
        LazyRow{
            items(data){
                PopularHotelsHomeDesign(data = it , navController = navController)
            }
        }
}

@Composable
fun PopularHotelsHomeDesign(data :CategoriesModel , navController: NavController)
{
    Column(
        modifier = Modifier.padding(end = 10.dp , bottom = 10.dp).clickable {
            navController.navigate(Screens.Room.route
                    + "?id=${data.id}"
                    + "&name=${data.name}"
                    + "&type=${categoryName.value}"
            )
            {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    ) {
        
        GlideImage(
            imageModel = data.imgUrl,
            modifier   = Modifier
                .width(250.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(15.dp))
             )
        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text        = data.name,
            fontFamily  = Typography.h1.fontFamily,
            color       = baseColor,
            fontSize    = 13.sp,
        )
    }
}
