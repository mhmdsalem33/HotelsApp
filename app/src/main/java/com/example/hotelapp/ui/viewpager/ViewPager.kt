package com.example.hotelapp.ui.viewpager

import androidx.annotation.DrawableRes
import com.example.hotelapp.R

data class PagerModel  (
    @DrawableRes
    val image :Int,
    val des   :String
        )


val dataList = listOf(
    PagerModel(R.drawable.one , "One"),
    PagerModel(R.drawable.two , "Two"),
    PagerModel(R.drawable.three , "There")
)
