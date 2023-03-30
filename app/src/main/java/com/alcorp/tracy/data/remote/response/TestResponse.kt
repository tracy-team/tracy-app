package com.alcorp.tracy.data.remote.response

import com.google.gson.annotations.SerializedName

data class TestResponse(

	@field:SerializedName("data")
	val data: List<List<DataItemItem>>? = null
)

data class DataItemItem(

	@field:SerializedName("idLaporan")
	val idLaporan: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("detailLokasi")
	val detailLokasi: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("noTelepon")
	val noTelepon: String? = null,

	@field:SerializedName("kodeInstansi")
	val kodeInstansi: String? = null,

	@field:SerializedName("namaKantor")
	val namaKantor: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("isDataValid")
	val isDataValid: Boolean? = null,

	@field:SerializedName("dateOfBirth")
	val dateOfBirth: String? = null,

	@field:SerializedName("isPolice")
	val isPolice: Boolean? = null,

	@field:SerializedName("noHp")
	val noHp: String? = null,

	@field:SerializedName("namaLengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("jenisKejahatan")
	val jenisKejahatan: String? = null,

	@field:SerializedName("uraian")
	val uraian: String? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("polisi")
	val polisi: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
