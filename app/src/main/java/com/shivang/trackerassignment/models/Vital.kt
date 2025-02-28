package com.shivang.trackerassignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

/*
created by  Shivang Yadav on 28-02-2025
gitHub: https://github.com/Iamshivang
project: TrackerAssignment
description:
*/

@Entity(tableName = "Vital_Table")
data class Vital(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val BPSys: Int,
    val BPDia: Int,
    val weight: Int,
    val kicks: Int,
    val heartRate: Int,
    val date: String,
    val time: String,
    val day: String
)
