package com.abdl.edvorasubmission.data.source.remote.services

import com.abdl.edvorasubmission.data.source.remote.model.RideResponse
import com.abdl.edvorasubmission.data.source.remote.model.RideResponseItem
import com.abdl.edvorasubmission.data.source.remote.model.UserResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("rides")
    suspend fun getAllRides() : Response<List<RideResponseItem>>

    @GET("user")
    suspend fun getUser() : Response<UserResponse>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://assessment.api.vweb.app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}