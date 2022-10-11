package com.example.hotelapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roomInformation")
data class RoomModel (
    @PrimaryKey
    val id          :String  ,
    val name        :String  ,
    val imgUrl      :String  ,
    val price       :Double  ,
    val status      :String  ,
    val location    :String  ,
    val photos      :PhotosModel,
    val description :String
    )
{
    constructor() :this("" ,  "" , "" ,0.00 , "" , "" , PhotosModel("" ,"", ""), "")
}
