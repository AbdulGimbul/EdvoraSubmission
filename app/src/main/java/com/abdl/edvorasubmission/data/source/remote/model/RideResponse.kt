package com.abdl.edvorasubmission.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class RideResponse(

	@field:SerializedName("RideResponse")
	val rideResponse: List<RideResponseItem?>? = null
)

data class RideResponseItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("origin_station_code")
	val originStationCode: Int? = null,

	@field:SerializedName("destination_station_code")
	val destinationStationCode: Int? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("station_path")
	val stationPath: List<Int?>? = null,

	@field:SerializedName("map_url")
	val mapUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state")
	val state: String? = null
)
