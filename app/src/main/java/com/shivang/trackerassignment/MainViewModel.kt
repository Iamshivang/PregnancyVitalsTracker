package com.shivang.trackerassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.shivang.trackerassignment.dataBase.interfaces.VitalDataRepository
import com.shivang.trackerassignment.models.Vital
import com.shivang.trackerassignment.utils.Resource
import com.shivang.trackerassignment.workmanager.ReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/*
created by  Shivang Yadav on 28-02-2025
gitHub: https://github.com/Iamshivang
project: TrackerAssignment
description:
*/


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: VitalDataRepository,
    private val workManager: WorkManager
): ViewModel(){

    // LiveData to hold the state of the vitals list.
    private val _vitals = MutableLiveData<Resource<List<Vital>>>()
    val vitals: LiveData<Resource<List<Vital>>> get() = _vitals

    // Function to fetch vitals from the repository.
    fun fetchVitals() {
        _vitals.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val vitalList = repository.getVitalList()
                if (vitalList.isNullOrEmpty()) {
                    _vitals.value = Resource.Error("No data available")
                } else {
                    _vitals.value = Resource.Success(vitalList)
                }
            } catch (e: Exception) {
                _vitals.value = Resource.Error("Error fetching data: ${e.message}")
            }
        }
    }

    // Function to insert a vital record.
    fun insertVital(vital: Vital) {
        viewModelScope.launch {
            try {
                repository.insertVital(vital)
                // Optionally, refresh the list after insertion.
                fetchVitals()
            } catch (e: Exception) {
                // You can optionally post an error state to a different LiveData.
                _vitals.value = Resource.Error("Error inserting data: ${e.message}")
            }
        }
    }

    // Function to delete a vital record by its id.
    fun deleteVital(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteVitalById(id)
                // Optionally, refresh the list after deletion.
                fetchVitals()
            } catch (e: Exception) {
                // You can optionally post an error state to a different LiveData.
                _vitals.value = Resource.Error("Error deleting data: ${e.message}")
            }
        }
    }

    fun scheduleReminder() {
        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(5, TimeUnit.HOURS)
            .build()
        workManager.enqueue(workRequest)
    }
}