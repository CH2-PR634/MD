package com.capstone.vsl.ui.main.practice.camera

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.capstone.vsl.R
import com.capstone.vsl.data.remote.response.ScanResponse
import com.capstone.vsl.data.remote.retrofit.ApiConfig
import com.capstone.vsl.databinding.ActivityUploadBinding
import com.capstone.vsl.utils.deletePhoto
import com.capstone.vsl.utils.rotateAndCompressBitmap
import java.io.File

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding

    private var isBackCamera: Boolean = true
    private var getFile: File? = null
    private var currentImageUri: Uri? = null

    private val viewModel: UploadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.scannedImage.observe(this, Observer { file ->
            getFile = file
            if (getFile != null) {
                val result = rotateAndCompressBitmap(
                    BitmapFactory.decodeFile(getFile?.path),
                    isBackCamera
                )
                binding.previewImageView2.setImageBitmap(result)
            } else {
                binding.previewImageView2.setImageResource(R.drawable.ic_insert_photo_24)
            }
        })

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        customToolbar()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Not getting permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun customToolbar() {

        binding.apply {
            ivBack.setOnClickListener() {
                onBackPressed()
            }

            btnCameraX.setOnClickListener { startCamera() }
            BtnGallery.setOnClickListener { startGallery() }
            ivDelete.setOnClickListener { deletePhoto() }
            btnUpload.setOnClickListener { detectPhoto() }
        }
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            viewModel.setScannedImage(myFile)
            this.isBackCamera = isBackCamera
        }
    }

    private fun startGallery() {
        Toast.makeText(this, "Sorry, this feature is still not available", Toast.LENGTH_SHORT).show()
//        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView2.setImageURI(it)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun deletePhoto() {
        getFile?.let {
            deletePhoto(it)
            getFile = null
            viewModel.clearPhoto()
            binding.ivDelete.setImageDrawable(null)
        } ?: run {
            Toast.makeText(this, "There is no photo to delete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun detectPhoto() {
        val apiService = ApiConfig.getApiService()
        val label = ""
        val value = 0
        showLoading(true)

        viewModel.scanImage(apiService, label, value).observe(this) { scanResponse ->
            if (scanResponse != null) {
                showResult(scanResponse)
                showLoading(false)
                viewModel.clearPhoto()
            } else {
                showLoading(false)
                Toast.makeText(this, "Please take your picture first!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showResult(scanresponse: ScanResponse?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.pop_up_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvjudul = dialog.findViewById<TextView>(R.id.tv_result)
        val tvdetail = dialog.findViewById<TextView>(R.id.tv_desc)
        val ivEmote = dialog.findViewById<ImageView>(R.id.ivEmote)
        val btnAgain = dialog.findViewById<Button>(R.id.btn_again)
        val btnBack = dialog.findViewById<Button>(R.id.btn_back)

        tvjudul.text = labelName(label = scanresponse?.predictedClass)
        tvdetail.text = labelDesc(label = scanresponse?.predictedClass)
        setEmoteImage(ivEmote, label = scanresponse?.predictedClass)

        btnAgain.setOnClickListener {
            dialog.dismiss()
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }

        val window = dialog.window
        val layoutParams = window?.attributes
        layoutParams?.gravity = Gravity.CENTER or Gravity.CENTER
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams

        dialog.show()
    }

    private fun setEmoteImage(imageView: ImageView, label: Int?) {
        when (label) {
            0 -> imageView.setImageResource(R.drawable.smiley)
            1 -> imageView.setImageResource(R.drawable.smiley)
            2 -> imageView.setImageResource(R.drawable.smiley)
            3 -> imageView.setImageResource(R.drawable.smiley)
            4 -> imageView.setImageResource(R.drawable.smiley)
            5 -> imageView.setImageResource(R.drawable.smiley)
            6 -> imageView.setImageResource(R.drawable.smiley)
            7 -> imageView.setImageResource(R.drawable.smiley)
            8 -> imageView.setImageResource(R.drawable.smiley)
            9 -> imageView.setImageResource(R.drawable.smiley)
            10 -> imageView.setImageResource(R.drawable.smiley)
            11 -> imageView.setImageResource(R.drawable.smiley)
            12 -> imageView.setImageResource(R.drawable.smiley)
            13 -> imageView.setImageResource(R.drawable.smiley)
            14 -> imageView.setImageResource(R.drawable.smiley)
            15 -> imageView.setImageResource(R.drawable.smiley)
            16 -> imageView.setImageResource(R.drawable.smiley)
            17 -> imageView.setImageResource(R.drawable.smiley)
            18 -> imageView.setImageResource(R.drawable.smiley)
            19 -> imageView.setImageResource(R.drawable.smiley)
            20 -> imageView.setImageResource(R.drawable.smiley)
            21 -> imageView.setImageResource(R.drawable.smiley)
            22 -> imageView.setImageResource(R.drawable.smiley)
            23 -> imageView.setImageResource(R.drawable.smiley)
            24 -> imageView.setImageResource(R.drawable.smiley)
            25 -> imageView.setImageResource(R.drawable.smiley)
            26 -> imageView.setImageResource(R.drawable.smiley)
            27 -> imageView.setImageResource(R.drawable.smiley)
            28 -> imageView.setImageResource(R.drawable.smiley)
            29 -> imageView.setImageResource(R.drawable.smiley)
            30 -> imageView.setImageResource(R.drawable.smiley)
            31 -> imageView.setImageResource(R.drawable.smiley)
            32 -> imageView.setImageResource(R.drawable.smiley)
            33 -> imageView.setImageResource(R.drawable.smiley)
            34 -> imageView.setImageResource(R.drawable.smiley)
            35 -> imageView.setImageResource(R.drawable.smiley)
            else -> imageView.setImageResource(R.drawable.emote3)
        }
    }

    private fun labelDesc(label: Int?): String {
        return when (label) {
            0 -> getString(R.string.l_0)
            1 -> getString(R.string.l_01)
            2 -> getString(R.string.l_02)
            3 -> getString(R.string.l_03)
            4 -> getString(R.string.l_04)
            5 -> getString(R.string.l_05)
            6 -> getString(R.string.l_06)
            7 -> getString(R.string.l_07)
            8 -> getString(R.string.l_08)
            9 -> getString(R.string.l_09)
            10 -> getString(R.string.l_a)
            11 -> getString(R.string.l_b)
            12 -> getString(R.string.l_c)
            13 -> getString(R.string.l_d)
            14 -> getString(R.string.l_e)
            15 -> getString(R.string.l_f)
            16 -> getString(R.string.l_g)
            17 -> getString(R.string.l_h)
            18 -> getString(R.string.l_i)
            19 -> getString(R.string.l_j)
            20 -> getString(R.string.l_k)
            21 -> getString(R.string.l_l)
            22 -> getString(R.string.l_m)
            23 -> getString(R.string.l_n)
            24 -> getString(R.string.l_o)
            25 -> getString(R.string.l_p)
            26 -> getString(R.string.l_q)
            27 -> getString(R.string.l_r)
            28 -> getString(R.string.l_s)
            29 -> getString(R.string.l_t)
            30 -> getString(R.string.l_u)
            31 -> getString(R.string.l_v)
            32 -> getString(R.string.l_w)
            33 -> getString(R.string.l_x)
            34 -> getString(R.string.l_y)
            35 -> getString(R.string.l_z)
            else -> getString(R.string.nothing_desc)
        }

    }

    private fun labelName(label: Int?): String {
        return when (label) {
            0 -> getString(R.string.success_title)
            1 -> getString(R.string.success_title)
            2 -> getString(R.string.success_title)
            3 -> getString(R.string.success_title)
            4 -> getString(R.string.success_title)
            5 -> getString(R.string.success_title)
            6 -> getString(R.string.success_title)
            7 -> getString(R.string.success_title)
            8 -> getString(R.string.success_title)
            9 -> getString(R.string.success_title)
            10 -> getString(R.string.success_title)
            11 -> getString(R.string.success_title)
            12 -> getString(R.string.success_title)
            13 -> getString(R.string.success_title)
            14 -> getString(R.string.success_title)
            15 -> getString(R.string.success_title)
            16 -> getString(R.string.success_title)
            17 -> getString(R.string.success_title)
            18 -> getString(R.string.success_title)
            19 -> getString(R.string.success_title)
            20 -> getString(R.string.success_title)
            21 -> getString(R.string.success_title)
            22 -> getString(R.string.success_title)
            23 -> getString(R.string.success_title)
            24 -> getString(R.string.success_title)
            25 -> getString(R.string.success_title)
            26 -> getString(R.string.success_title)
            27 -> getString(R.string.success_title)
            28 -> getString(R.string.success_title)
            29 -> getString(R.string.success_title)
            30 -> getString(R.string.success_title)
            31 -> getString(R.string.success_title)
            32 -> getString(R.string.success_title)
            33 -> getString(R.string.success_title)
            34 -> getString(R.string.success_title)
            35 -> getString(R.string.success_title)
            else -> getString(R.string.nothing_title)
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}