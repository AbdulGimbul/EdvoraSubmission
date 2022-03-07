package com.abdl.edvorasubmission.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdl.edvorasubmission.data.repository.MainRepository
import com.abdl.edvorasubmission.data.source.remote.model.RideResponse
import com.abdl.edvorasubmission.data.source.remote.model.RideResponseItem
import com.abdl.edvorasubmission.data.source.remote.model.UserResponse
import kotlinx.coroutines.*

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val rideList = MutableLiveData<List<RideResponseItem>>()
    val userList = MutableLiveData<UserResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllRide(){
        job = viewModelScope.launch {
            val response = mainRepository.getAllRides()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    rideList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun getUser(){
        job = viewModelScope.launch {
            val response = mainRepository.getUser()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    userList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String){
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}