package com.abdl.edvorasubmission.data.repository

import com.abdl.edvorasubmission.data.source.remote.services.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getAllRides() = retrofitService.getAllRides()
    suspend fun getUser() = retrofitService.getUser()
}