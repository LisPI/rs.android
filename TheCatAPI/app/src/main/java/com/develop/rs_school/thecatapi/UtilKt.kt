package com.develop.rs_school.thecatapi

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun saveBitmapImageToPNGFile(
    bitmap: Bitmap,
    compressQuality: Int,
    bitmapFileNameUrl: String,
    context: Context
): Uri? {
    val resolver = context.contentResolver
    val fileName = getFilenameFromUrl(bitmapFileNameUrl) ?: System.currentTimeMillis().toString()

    val imagesCollection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val imageDetails = ContentValues().apply {
        put(MediaStore.Images.Media.TITLE, fileName)
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        imageDetails.put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val imageContentUri = resolver.insert(imagesCollection, imageDetails)

    if (imageContentUri != null) {
        resolver.openOutputStream(imageContentUri, "w").use {
            bitmap.compress(Bitmap.CompressFormat.PNG, compressQuality, it)
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        imageDetails.clear()
        imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
        imageContentUri?.let { resolver.update(it, imageDetails, null, null) }
    }

    return imageContentUri
}

fun getFilenameFromUrl(fileNameUrl: String): String? {
    return fileNameUrl.substringAfterLast('/').substringBeforeLast('.')
}
