package com.shivang.trackerassignment.dataBase.repository

import com.shivang.trackerassignment.dataBase.AppDatabase
import com.shivang.trackerassignment.dataBase.interfaces.VitalDataRepository
import com.shivang.trackerassignment.models.Vital
import javax.inject.Inject

/*
created by  Shivang Yadav on 28-02-2025
gitHub: https://github.com/Iamshivang
project: TrackerAssignment
description:
*/

class VitalRepository @Inject  constructor(
    private val db: AppDatabase
): VitalDataRepository{

    override suspend fun getVitalList(): List<Vital>? {
        // Fetch all vitals from the database.
        return db.vitalDataDao().getAllVitals()
    }

    override suspend fun insertVital(vital: Vital) {
        // Insert the vital record into the database.
        db.vitalDataDao().insertVital(vital)
    }

    override suspend fun deleteVitalById(itd: Int) {
        // Delete the vital record with the given id.
        db.vitalDataDao().deleteVitalById(itd)
    }

}