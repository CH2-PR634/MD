package com.capstone.vsl.data.remote.retrofit

import com.capstone.vsl.data.remote.response.ModulesResponse
import com.capstone.vsl.data.remote.response.ScanResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("modules/a-f")
    fun getModulesAF(): Call<ModulesResponse>

    @GET("modules/g-k")
    fun getModulesGK(): Call<ModulesResponse>

    @GET("modules/l-o")
    fun getModulesLO(): Call<ModulesResponse>

    @GET("modules/p-r")
    fun getModulesPR(): Call<ModulesResponse>

    @GET("modules/s-u")
    fun getModulesSU(): Call<ModulesResponse>

    @GET("modules/v-z")
    fun getModulesVZ(): Call<ModulesResponse>

    @Multipart
    @POST("exercises/predict")
    fun scanSampah (
        @Part file: MultipartBody.Part,
    ): Call<ScanResponse>
}