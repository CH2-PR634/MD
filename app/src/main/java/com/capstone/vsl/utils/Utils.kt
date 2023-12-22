package com.capstone.vsl.utils

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.capstone.vsl.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILENAME_FORMAT = "dd-MMM-yyyy"
private const val MAXIMAL_SIZE = 1000000

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}

fun rotateAndCompressBitmap(
    bitmap: Bitmap,
    isBackCamera: Boolean = true,
    compressionQuality: Int = 80
): Bitmap {
    val rotatedBitmap = rotateBitmap(bitmap, isBackCamera)

    val outputStream = ByteArrayOutputStream()
    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, outputStream)

    val compressedByteArray = outputStream.toByteArray()

    return BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.size)
}

fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = true): Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(360f)
        matrix.postScale(1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(360f, bitmap.width / 2f, bitmap.height / 2f)
        matrix.postScale(-1f, 1f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}

fun deletePhoto(file: File) {
    if (file.exists()) {
        file.delete()
    }
}