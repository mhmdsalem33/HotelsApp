package com.example.hotelapp.data

import com.google.firebase.Timestamp

data class UserScreenModel(val startDate : String , val endDate :String , val userName :String , val phoneNumber :String)
{

    constructor() : this("" ,"","","" )
}
