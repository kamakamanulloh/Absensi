package com.softwarepintarcom.absensi.scan

import com.google.gson.annotations.SerializedName

data class AbsensiResponse(

	@field:SerializedName("pesan")
	val pesan: String? = null,


	@field:SerializedName("status")
	val status: Int? = null
)
