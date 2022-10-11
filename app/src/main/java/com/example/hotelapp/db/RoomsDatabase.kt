package com.example.hotelapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hotelapp.data.RoomModel

@Database(entities = [RoomModel::class] , version = 1 )
@TypeConverters(RoomTypeConverter::class)
abstract class RoomsDatabase  :RoomDatabase(){
    abstract fun roomDao() : RoomDao
}