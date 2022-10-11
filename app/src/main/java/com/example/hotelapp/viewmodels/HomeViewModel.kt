package com.example.hotelapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelapp.data.CategoriesModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val firestore: FirebaseFirestore) : ViewModel() {

    private var _getCategoriesStateFlow = MutableStateFlow<List<CategoriesModel>>(emptyList())
    var getCategoriesStateFlow :StateFlow<List<CategoriesModel>> = _getCategoriesStateFlow

    fun getCategories(categoryName : String)
    {
        firestore.collection(categoryName).addSnapshotListener{ data , error ->
            if (error !=  null)
            {
                Log.d("testApp" , error.message.toString())
            }
            else
            {
               if (data != null)
               {
                   val categoryList    = arrayListOf<CategoriesModel>()
                   val categoryModel   = data.toObjects(CategoriesModel::class.java)
                       categoryList.addAll(categoryModel)
                      viewModelScope.launch {
                          _getCategoriesStateFlow.emit(categoryList)
                      }
               }
            }
        }
    }


    private var _getPopularStateFlow = MutableStateFlow<List<CategoriesModel>>(emptyList())
    var getPopularStateFlow : StateFlow<List<CategoriesModel>> = _getPopularStateFlow

    fun getPopular() {
        firestore.collection("PopularHotels").addSnapshotListener{data , error ->
            if (error !=  null)
            {
                Log.d("testApp" , error.message.toString())
            }
            else
            {
                if (data != null)
                {
                    val popularList  = arrayListOf<CategoriesModel>()
                    val popularModel = data.toObjects(CategoriesModel::class.java)
                        popularList.addAll(popularModel)
                    viewModelScope.launch {
                        _getPopularStateFlow.emit(popularList)
                    }
                }
            }
        }
    }




}