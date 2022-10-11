package com.example.hotelapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.hotelapp.data.PhotosModel
import com.example.hotelapp.data.RoomModel
import com.google.gson.Gson
import java.util.jar.Attributes

@TypeConverters
class RoomTypeConverter {


    @TypeConverter
    fun convertPhotos(photo : PhotosModel?) :String?{
        return photo?.toString() }


    @TypeConverter
    fun convertSource(photo :String?) : PhotosModel?{
        return photo?.let { PhotosModel(it , it , it) }
    }

}