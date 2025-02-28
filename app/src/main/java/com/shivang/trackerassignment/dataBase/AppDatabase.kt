package com.shivang.trackerassignment.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shivang.trackerassignment.dataBase.dao.VitalDao
import com.shivang.trackerassignment.models.Vital

/*
created by  Shivang Yadav on 28-02-2025
gitHub: https://github.com/Iamshivang
project: TrackerAssignment
description:
*/


@Database(entities = [Vital::class], version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun vitalDataDao(): VitalDao

    companion object {
        fun getInstance(context: Context): AppDatabase  {

            return Room.databaseBuilder(context, AppDatabase::class.java, "vital.db")
                .allowMainThreadQueries()
                .build()
        }
    }
}