package com.example.hotelapp.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelapp.data.RoomModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val firestore: FirebaseFirestore) : ViewModel() {





    private var _getSearchProductsData = MutableStateFlow<List<RoomModel>>(emptyList())
    var getSearchProductsData : StateFlow<List<RoomModel>> = _getSearchProductsData

    fun searchInFirestore(id :String , type :String , searchText: String , context: Context) {
        firestore.collection(type)
            .document(id)
            .collection("Rooms")
            .orderBy("searchName")
            .startAt(searchText)
            .endAt("$searchText\uf8ff")
            .limit(10)
            .get()
            .addOnCompleteListener {
                val data = it.result.toObjects(RoomModel::class.java)
                viewModelScope.launch { _getSearchProductsData.emit(data) }
            }
            .addOnFailureListener{
                Toast.makeText(context, ""+it.message, Toast.LENGTH_SHORT).show()
            }
    }

}