package com.example.hotelapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hotelapp.db.RoomsDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideAuth()     = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()


    @Singleton
    @Provides
    fun provideDatabase(app : Application) :RoomsDatabase =
      Room.databaseBuilder(app , RoomsDatabase::class.java , "room.db").build()


}