package com.shivang.trackerassignment.dataBase.interfaces

import com.shivang.trackerassignment.models.Vital

/*
created by  Shivang Yadav on 28-02-2025
gitHub: https://github.com/Iamshivang
project: TrackerAssignment
description:
*/


interface VitalDataRepository {

    suspend fun getVitalList(): List<Vital>?

    suspend fun insertVital(vital: Vital)

    suspend fun deleteVitalById(itd: Int)
}