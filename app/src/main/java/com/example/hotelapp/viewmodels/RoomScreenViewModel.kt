package com.example.hotelapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelapp.data.RoomModel
import com.example.hotelapp.db.RoomsDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomScreenViewModel @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val db       : RoomsDatabase
    ) :ViewModel(){


    private var _getRoomData = MutableStateFlow<List<RoomModel>>(emptyList())
    var getRoomData :StateFlow<List<RoomModel>> = _getRoomData

    fun getRoomData(hotelId :String , type :String)
    {
        firestore.collection(type)
            .document(hotelId)
            .collection("Rooms")
            .addSnapshotListener{data , error ->
                if (error != null)
                {
                    Log.d("testApp" , error.message.toString())
                }
                else

                {
                    if (data != null)
                    {
                        val roomList  =    arrayListOf<RoomModel>()
                        val roomModel =    data.toObjects(RoomModel::class.java)
                            roomList.addAll(roomModel)
                        viewModelScope.launch {
                            _getRoomData.emit(roomList)
                        }
                    }
                }
            }
    }



    private var _getOfferRoomLiveData = MutableStateFlow<List<RoomModel>>(emptyList())
    var getOfferRoomLiveData : StateFlow<List<RoomModel>> = _getOfferRoomLiveData


    fun getOffersRoomData(hotelId :String , type :String)
    {
        firestore.collection(type).document(hotelId).collection("Offers")
            .addSnapshotListener{ data , error ->
                if (error != null)
                {
                    Log.d("testApp" , error.message.toString())
                }
                else
                {
                    if (data != null)
                    {
                        val offerList    = arrayListOf<RoomModel>()
                        val offerModel   = data.toObjects(RoomModel::class.java)
                            offerList.addAll(offerModel)
                           _getOfferRoomLiveData.value = offerModel

                    }
                }
            }
    }


    suspend fun upsertRoom(data : RoomModel) =  viewModelScope.launch { db.roomDao().upsertRoom(data = data) }
    fun deleteRoom(data : RoomModel) =  viewModelScope.launch { db.roomDao().deleteRoom(data = data) }

    val getSavedRooms  = db.roomDao().getSavedRooms()



}