package com.example.lu3_camera_app

import org.junit.Test
import com.example.lu3_camera_app.Model.ImageModel
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    /*
        Testing the ImageModel class to ensure it's initialised correctly
     */
    @Test
    fun imageModel_initialization_isCorrect() {
        val name = "TestImage"
        val uri = "content://media/external/images/media/1"
        
        // Creating an instance of ImageModel (Bitmap is null for local unit test)
        val imageModel = ImageModel(name, null, uri)
        
        assertEquals(name, imageModel.imageName)
        assertNull(imageModel.imageBitmap)
        assertEquals(uri, imageModel.imageUri)
    }
}