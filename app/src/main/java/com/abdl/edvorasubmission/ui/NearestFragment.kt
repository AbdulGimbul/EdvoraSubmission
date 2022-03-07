package com.abdl.edvorasubmission.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.edvorasubmission.data.repository.MainRepository
import com.abdl.edvorasubmission.data.source.remote.services.RetrofitService
import com.abdl.edvorasubmission.databinding.FragmentNearestBinding
import com.abdl.edvorasubmission.viewmodel.MainViewModel
import com.abdl.edvorasubmission.viewmodel.MyViewModelFactory

class NearestFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var nearestBinding: FragmentNearestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        nearestBinding = FragmentNearestBinding.inflate(layoutInflater, container, false)
        return nearestBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val retrofitService = RetrofitService.getInstance()
            val mainRepository = MainRepository(retrofitService)

            viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[MainViewModel::class.java]

            val distances = mutableListOf<Int>()
            val ridesAdapter = RidesAdapter(distances)

            nearestBinding.progressBar.visibility = View.VISIBLE
            viewModel.rideList.observe(viewLifecycleOwner) { rides ->
                viewModel.userList.observe(viewLifecycleOwner){
                    val myNumber = it.stationCode
                    for (ride in rides){
                        val numbers = ride.stationPath
                        val operate = myNumber?.let { it1 -> numbers?.get(0)?.minus(it1) }
                        var distance: Int? = operate?.let { it1 -> Math.abs(it1) }
                        var idx = 0
                        if (numbers != null) {
                            for (c in 1 until numbers.size) {
                                val cdistance: Int? = numbers?.get(c)?.minus(myNumber!!)
                                    ?.let { it1 -> Math.abs(it1) }
                                if (cdistance != null) {
                                    if (cdistance < distance!!) {
                                        idx = c
                                        distance = cdistance
                                    }
                                }
                            }
                        }
                        val theNumber: Int? = numbers?.get(idx)


                        var jarak: Int? = null
                        if (theNumber!! > myNumber!!){
                            jarak = theNumber - myNumber
                        } else {
                            jarak = myNumber - theNumber
                        }

                        distances.add(jarak)

                        Log.d("NearestFragment", "Ini angka station user $myNumber")
                        Log.d("NearestFragment", "Ini angka statiopath $numbers")
                        Log.d("NearestFragment", "Ini angka Terkecil $theNumber")
                        Log.d("NearestFragment", "Jadi angka jaraknya adalah $jarak")
                        Log.d("NearestFragment", "Jadi coba $distances")
                    }

                }
                nearestBinding.progressBar.visibility = View.GONE
                ridesAdapter.setRideList(rides)
            }

            with(nearestBinding.rvRides) {
                layoutManager = LinearLayoutManager(context)
                this.adapter = ridesAdapter
            }

            viewModel.getAllRide()
            viewModel.getUser()
        }
    }

}