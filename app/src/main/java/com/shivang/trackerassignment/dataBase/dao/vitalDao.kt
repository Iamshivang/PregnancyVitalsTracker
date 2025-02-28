package com.shivang.trackerassignment.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shivang.trackerassignment.models.Vital

/*
created by  Shivang Yadav on 28-02-2025
gitHub: https://github.com/Iamshivang
project: TrackerAssignment
description:
*/

@Dao
interface VitalDao {

    @Query("SELECT * FROM Vital_Table")
    suspend fun getAllVitals(): List<Vital>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVital(vital: Vital)

    @Query("DELETE FROM vital_Table WHERE id = :id")
    suspend fun deleteVitalById(id: Int)
}