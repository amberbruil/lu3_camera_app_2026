package com.example.lu3_camera_app.Model

import android.graphics.Bitmap
import com.google.firebase.database.Exclude

data class ImageModel (public val imageName: String?,
                       @get:Exclude public val imageBitmap: Bitmap?,
                       public val imageUri: String?) { public constructor() :
        this(null, null, null)
    { }
}