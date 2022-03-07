package com.abdl.edvorasubmission.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.abdl.edvorasubmission.R
import com.abdl.edvorasubmission.data.repository.MainRepository
import com.abdl.edvorasubmission.data.source.remote.model.RideResponseItem
import com.abdl.edvorasubmission.data.source.remote.model.UserResponse
import com.abdl.edvorasubmission.data.source.remote.services.RetrofitService
import com.abdl.edvorasubmission.databinding.ItemsRidesBinding
import com.abdl.edvorasubmission.viewmodel.MainViewModel
import com.bumptech.glide.Glide

class RidesAdapter(var distances: List<Int?>) : RecyclerView.Adapter<RidesAdapter.RidesViewHolder>() {
    var ride = ArrayList<RideResponseItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setRideList(ride: List<RideResponseItem>) {
        if (ride == null) return
        this.ride.clear()
        this.ride.addAll(ride)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RidesViewHolder {
        val itemsRidesBinding =
            ItemsRidesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RidesViewHolder(itemsRidesBinding)
    }

    override fun onBindViewHolder(holder: RidesViewHolder, position: Int) {
        val ride = ride[position]
        val distances = distances[position]
        if (ride != null) {
            holder.bind(ride)
            holder.distance(listOf(distances))
        }

    }

    override fun getItemCount(): Int {
        return ride.size
    }

    class RidesViewHolder(private val binding: ItemsRidesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ride: RideResponseItem) {
            with(binding) {
                tvCityName.text = ride.city
                tvStateName.text = ride.state
                tvRoleId.text = ride.id.toString()
                tvOriginStation.text = ride.originStationCode.toString()
                tvStationPath.text = ride.stationPath.toString()
                tvDate.text = ride.date

                Glide.with(itemView.context)
                    .load(ride.mapUrl)
                    .into(imgMaps)
            }
        }
        fun distance(distances: List<Int?>){
            for (distance in distances)
            {
                binding.tvDistance.text = distance.toString()
            }
        }
    }

}