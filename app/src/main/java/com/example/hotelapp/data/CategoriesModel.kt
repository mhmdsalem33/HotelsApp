package com.example.hotelapp.data

data class CategoriesModel (val id :String , val name :String , val imgUrl :String , val location :String ,val  type:String)
{
    constructor() :this("" ,"" ,"" ,"","")
}
