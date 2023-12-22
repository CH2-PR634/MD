package com.capstone.vsl.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ModulesResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("dataAlpha")
	val dataAlpha: List<DataAlphaItem?>? = null
)

@Parcelize
data class DataAlphaItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("huruf")
	val huruf: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
): Parcelable
