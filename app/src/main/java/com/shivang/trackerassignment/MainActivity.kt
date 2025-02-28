package com.shivang.trackerassignment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivang.trackerassignment.databinding.ActivityMainBinding
import com.shivang.trackerassignment.databinding.AddVitalBinding
import com.shivang.trackerassignment.models.Vital
import com.shivang.trackerassignment.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG= "MainActivity"
    val viewModel: MainViewModel  by viewModels()
    private lateinit var adapter: VitalAdapter
    private lateinit var rvGallery: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvGallery= binding.rvGallery

        setView()

        viewModel.scheduleReminder()

        binding.buttonAddVital.setOnClickListener {
            openDialogBox()
        }
    }

    private fun setView() {

        adapter = VitalAdapter(mutableListOf())

        binding.rvGallery.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        viewModel.fetchVitals()

        viewModel.vitals.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.pb.isVisible = true
                    rvGallery.isVisible= false
                }
                is Resource.Success -> {
                    resource.data?.let { vitalsList ->

                        adapter.list.clear()
                        adapter.list.addAll(vitalsList)
                        adapter.notifyDataSetChanged()
                    }
                    Log.i(TAG, "Data: ${resource.data}")
                    binding.pb.isVisible = false
                    rvGallery.isVisible= true
                }
                is Resource.Error -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Error occurred: ${resource.message}")
                    binding.pb.isVisible = false
                }
            }
        }
    }



    private fun openDialogBox() {
        val dialog = Dialog(this)
        val binding: AddVitalBinding = AddVitalBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)


        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg_box)
        dialog.setCancelable(false)

        binding.btnSubmit.setOnClickListener {

            val sysBpStr = binding.etSysBp.text.toString().trim()
            val diaBpStr = binding.etDiaBp.text.toString().trim()
            val weightStr = binding.etWeight.text.toString().trim()
            val kicksStr = binding.etBabyKicks.text.toString().trim()
            val heartRateStr = binding.etHeartRate.text.toString().trim()


            if (sysBpStr.isEmpty() || diaBpStr.isEmpty() || weightStr.isEmpty() ||
                kicksStr.isEmpty() || heartRateStr.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val sysBp = sysBpStr.toIntOrNull()
            val diaBp = diaBpStr.toIntOrNull()
            val weight = weightStr.toIntOrNull()
            val kicks = kicksStr.toIntOrNull()
            val heartRate = heartRateStr.toIntOrNull()

            if (sysBp == null || diaBp == null || weight == null || kicks == null || heartRate == null) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val now = java.time.LocalDateTime.now()
            val currentDate = now.toLocalDate().toString()
            val currentTime = now.toLocalTime().toString()
            val currentDay = now.dayOfWeek.toString()

            val vital = Vital(
                id = 0,
                BPSys = sysBp,
                BPDia = diaBp,
                weight = weight,
                kicks = kicks,
                heartRate = heartRate,
                date = currentDate,
                time = currentTime,
                day = currentDay
            )

            viewModel.insertVital(vital)

            dialog.dismiss()
        }
        dialog.show()
    }

}