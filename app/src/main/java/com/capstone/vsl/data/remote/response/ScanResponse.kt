package com.capstone.vsl.data.remote.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("predict_points")
	val predictPoints: String? = null,

	@field:SerializedName("huruf")
	val huruf: Any? = null,

	@field:SerializedName("predicted_class")
	val predictedClass: Int? = null
)
