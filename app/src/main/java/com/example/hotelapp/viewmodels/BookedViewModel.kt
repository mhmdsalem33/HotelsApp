package com.example.hotelapp.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.hotelapp.data.RoomModel
import com.example.hotelapp.data.UserScreenModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookedViewModel @Inject constructor(private val firestore: FirebaseFirestore) : ViewModel()
{

    fun uploadBookedRoomToFirestore(data : RoomModel , userInformation :UserScreenModel , context :Context)  {

        val bookedMap = HashMap<String  , Any>()
            bookedMap["id"]          = data.id
            bookedMap["name"]        = data.name
            bookedMap["location"]    = data.location
            bookedMap["price"]       = data.price
            bookedMap["status"]      = data.status
            bookedMap["imgUrl"]      = data.imgUrl
            bookedMap["photos"]      = data.photos
            bookedMap["description"] = data.description
            bookedMap["startDate"]   = userInformation.startDate
            bookedMap["endDate"]     = userInformation.endDate
            bookedMap["phoneNumber"] = userInformation.phoneNumber
            bookedMap["userName"]    = userInformation.userName

      firestore.collection("RoomBooked").document("123")
          .collection("Rooms")
          .document(data.id)
          .set(bookedMap)
          .addOnSuccessListener {
              Toast.makeText(context, "Your Room Booked Successfully", Toast.LENGTH_SHORT).show()
          }
          .addOnFailureListener {
              Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
          }
   }



    init {
        getBookedRoom()
    }

    private var _getBookedData = MutableStateFlow<List<RoomModel>>(emptyList())
    var getBookedData : StateFlow<List<RoomModel>> = _getBookedData

   private fun getBookedRoom() {
        firestore.collection("RoomBooked").document("123")
            .collection("Rooms")
            .addSnapshotListener{ data , error ->
                if (error != null)
                {
                    Log.d("testApp" , error.message.toString())
                }
                else
                {
                if (data != null)
                {
                    val bookedList   = arrayListOf<RoomModel>()
                    val bookedModel  = data.toObjects(RoomModel::class.java)
                        bookedList.addAll(bookedModel)
                    viewModelScope.launch { _getBookedData.emit(bookedList) }
                }
                }
            }
    }


    fun deleteRoomBooked(id :String , context: Context){
        firestore.collection("RoomBooked").document("123")
            .collection("Rooms").document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Room Canceled Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }


}