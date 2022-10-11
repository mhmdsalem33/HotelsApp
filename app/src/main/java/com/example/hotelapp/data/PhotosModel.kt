package com.example.hotelapp.data

import androidx.room.Entity


data class PhotosModel (
    val imgOne   : String  = ""  ,
    val imgTwo   : String        ,
    val imgThird : String
    )
{
    constructor() :this("" , "" , " ")
}
