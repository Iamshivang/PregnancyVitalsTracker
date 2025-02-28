package com.shivang.trackerassignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shivang.trackerassignment.databinding.VitalBinding
import com.shivang.trackerassignment.models.Vital
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/*
created by  Shivang Yadav on 28-02-2025
gitHub: https://github.com/Iamshivang
project: TrackerAssignment
description:
*/


class VitalAdapter(val list: MutableList<Vital>): RecyclerView.Adapter<VitalAdapter.VitalViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VitalViewHolder {
        return VitalViewHolder(VitalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: VitalViewHolder, position: Int) {

        val item= list.get(position)
        holder.bindItem(item)
    }

    inner class VitalViewHolder(private val binding: VitalBinding): RecyclerView.ViewHolder(binding.root){

        fun bindItem(model: Vital){

            binding.tvHr.text = "${model.heartRate} bpm"

            binding.tvBp.text = "${model.BPSys}/${model.BPDia} mmHg"

            binding.tvWeight.text = "${model.weight} Kg"

            binding.tvKicks.text = "${model.kicks} Kicks"

            binding.tvDate.text = model.date
            binding.tvDay.text = "${model.day},"

            try {
                val parsedTime = LocalTime.parse(model.time)
                val formatter = DateTimeFormatter.ofPattern("hh:mm a")
                val formattedTime = parsedTime.format(formatter)
                binding.tvTime.text = formattedTime
            } catch (e: Exception) {
                binding.tvTime.text = model.time
            }

            binding.root.background = ContextCompat.getDrawable(binding.root.context, R.drawable.dialog_bg_box)
        }
    }
}