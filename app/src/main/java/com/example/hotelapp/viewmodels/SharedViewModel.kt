package com.example.hotelapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.hotelapp.data.PhotosModel
import com.example.hotelapp.data.RoomModel

class SharedViewModel() : ViewModel(){

    var sharedData by mutableStateOf<RoomModel?>(null)
            private set

    fun viewPagerData(data : RoomModel)
    {
        sharedData = data
    }


    var sharedRoomInformationData by mutableStateOf<RoomModel?>(null)
        private set
    fun sharedRoomInformation(data: RoomModel)
    {
        sharedRoomInformationData = data
    }

}