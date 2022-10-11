package com.example.hotelapp.db

import androidx.room.*
import com.example.hotelapp.data.RoomModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend  fun upsertRoom(data  : RoomModel)


    @Delete
    suspend  fun deleteRoom(data : RoomModel)


    @Query("SELECT * FROM roomInformation")
    fun getSavedRooms() : Flow<List<RoomModel>>


}