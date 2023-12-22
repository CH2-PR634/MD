package com.capstone.vsl.ui.main.practice.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.vsl.data.remote.response.ScanResponse
import com.capstone.vsl.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadViewModel: ViewModel() {
    private val _scannedImage = MutableLiveData<File?>()
    val scannedImage: LiveData<File?>
        get() = _scannedImage

    fun setScannedImage(file:File?) {
        _scannedImage.value = file
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun clearPhoto() {
        _scannedImage.value = null
    }

    fun scanImage(apiService: ApiService, label: String, value: Int): MutableLiveData<ScanResponse?> {
        val result = MutableLiveData<ScanResponse?>()

        val file = _scannedImage.value
        if (file != null) {
            val filePart = MultipartBody.Part.createFormData(
                "file",
                file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )


            apiService.scanSampah(filePart).enqueue(object :
                Callback<ScanResponse> {
                override fun onResponse(call: Call<ScanResponse>, response: Response<ScanResponse>) {
                    if (response.isSuccessful) {
                        val scanResponse = response.body()
                        result.value = scanResponse
                    } else {
                        result.value = null
                    }
                }

                override fun onFailure(call: Call<ScanResponse>, t: Throwable) {
                    result.value = null
                }
            })
        } else {
            result.value = null
        }

        return result
    }
}