package com.birthday.kotlin.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.IOException

object ImageUtils {

    private const val MODE_READ_ONLY = "r"

    
    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val storageDirectory =
            ContextCompat.getExternalFilesDirs(context, Environment.DIRECTORY_PICTURES)[0]
        return File.createTempFile("user_profile", ".jpg", storageDirectory)
    }

    fun getBitmapFromUri(bitmapUri: Uri, activity: Activity): Bitmap? {
        activity.let {
            it.contentResolver.notifyChange(bitmapUri, null)
            return try {
                val parcelFileDescriptor =
                    it.contentResolver.openFileDescriptor(bitmapUri, MODE_READ_ONLY)
                BitmapFactory.decodeFileDescriptor(parcelFileDescriptor?.fileDescriptor)
            } catch (e: Exception) {
                null
            }
        }
    }

    @Throws(IOException::class)
    fun rotateImageIfRequired(bitmap: Bitmap, currentPhotoPath: String): Bitmap {
        val orientation: Int = ExifInterface(currentPhotoPath).getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90.0f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180.0f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270.0f)
            else -> bitmap
        }
    }

    private fun rotateImage(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix().apply {
            postRotate(degree)
        }
        val resultBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return resultBitmap
    }

}