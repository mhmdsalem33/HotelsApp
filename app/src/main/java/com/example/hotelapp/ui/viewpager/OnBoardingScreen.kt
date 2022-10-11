package com.example.hotelapp.ui.viewpager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hotelapp.ui.theme.baseColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


@ExperimentalPagerApi
@Composable
fun OnBoardingScreen()
{
    val pageState =  rememberPagerState()
    Column() {
        HorizontalPager(
            count    = dataList.size,
            state    = pageState,
            modifier = Modifier.size(200.dp)
        ) { page ->
            PageUi(pager = dataList[page])
        }

        HorizontalPagerIndicator(
            pagerState = pageState,
            activeColor = baseColor
        )

        AnimatedVisibility(visible = pageState.currentPage == 0) {
            Button(onClick = { }) {

            }
        }
    }

}


@Composable
fun PageUi(pager : PagerModel)
{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(painter = painterResource(id = pager.image), contentDescription = "" , modifier = Modifier.size(200.dp) )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(text = pager.des)

    }
}
