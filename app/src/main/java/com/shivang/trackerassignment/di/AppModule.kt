package com.shivang.trackerassignment.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.shivang.trackerassignment.dataBase.AppDatabase
import com.shivang.trackerassignment.dataBase.interfaces.VitalDataRepository
import com.shivang.trackerassignment.dataBase.repository.VitalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
created by  Shivang Yadav on 28-02-2025
gitHub: https://github.com/Iamshivang
project: TrackerAssignment
description:
*/


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).build()

        return db;
    }

    @Provides
    @Singleton
    fun provideHitDataDao(db: AppDatabase) = db.vitalDataDao()

    @Provides
    @Singleton
    fun provideVitalDataRepository(
        db: AppDatabase
    ): VitalDataRepository {
        return VitalRepository(db)
    }

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)
}