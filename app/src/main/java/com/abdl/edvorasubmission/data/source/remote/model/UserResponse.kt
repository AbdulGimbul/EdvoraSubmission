package com.abdl.edvorasubmission.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("station_code")
	val stationCode: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)
