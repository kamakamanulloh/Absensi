package com.softwarepintarcom.absensi.loginpgw

import com.google.gson.annotations.SerializedName

data class PgwResponse(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("nip")
	val nip: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
