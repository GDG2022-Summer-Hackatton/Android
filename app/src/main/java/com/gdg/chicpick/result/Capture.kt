package com.gdg.chicpick.result

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun Activity.captureScreen(view: View, onCompressed: (Uri) -> Unit, onFailure: (Exception) -> Unit) {
    val authority = "$packageName.fileprovider"
    val file = File(filesDir, "kkkk.png")

    @Suppress("DEPRECATION")
    with(view) {
        isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(drawingCache)
        isDrawingCacheEnabled = false

        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)

            val uri = FileProvider.getUriForFile(this@captureScreen, authority, file)

            onCompressed(uri)
        }
    }
}

@SuppressLint("QueryPermissionsNeeded")
fun Activity.sendPngImage(uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/png"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    val chooser = Intent.createChooser(intent, null)

    packageManager.queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY).forEach { resolveInfo ->
        val packageName = resolveInfo.activityInfo.packageName
        val modeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        grantUriPermission(packageName, uri, modeFlags)
    }

    startActivity(chooser)
}