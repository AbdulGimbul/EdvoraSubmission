package com.abdl.edvorasubmission.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.edvorasubmission.R
import com.abdl.edvorasubmission.data.repository.MainRepository
import com.abdl.edvorasubmission.data.source.remote.model.RideResponseItem
import com.abdl.edvorasubmission.data.source.remote.services.RetrofitService
import com.abdl.edvorasubmission.databinding.FragmentNearestBinding
import com.abdl.edvorasubmission.databinding.FragmentUpcomingBinding
import com.abdl.edvorasubmission.viewmodel.MainViewModel
import com.abdl.edvorasubmission.viewmodel.MyViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class UpcomingFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var upcomingBinding: FragmentUpcomingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        upcomingBinding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        return upcomingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val retrofitService = RetrofitService.getInstance()
            val mainRepository = MainRepository(retrofitService)

            viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[MainViewModel::class.java]

            var distances = mutableListOf<Int?>()
            val ridesAdapter = RidesAdapter(distances)

            val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm a")
            val currentDate = sdf.format(Date())
            Log.d("MainActivity","Cek $currentDate")

            upcomingBinding.progressBar.visibility = View.VISIBLE
            viewModel.rideList.observe(viewLifecycleOwner) { rides ->
                val rideUpcoming = arrayListOf<RideResponseItem>()
                for (ride in rides){
                    if (ride.date!! > currentDate){
                        Log.d("UpcomingFragment", "upcoming ${ride.date} lebih dari $currentDate")
                        rideUpcoming.addAll(listOf(ride))
                    }
                }
                upcomingBinding.progressBar.visibility = View.GONE
                ridesAdapter.setRideList(rideUpcoming)
            }

            with(upcomingBinding.rvRidesUpcoming) {
                layoutManager = LinearLayoutManager(context)
                this.adapter = ridesAdapter
            }

            viewModel.getAllRide()
        }
    }

}