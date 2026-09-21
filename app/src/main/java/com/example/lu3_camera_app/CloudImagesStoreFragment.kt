package com.example.lu3_camera_app

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lu3_camera_app.Model.ImageModel
import com.example.lu3_camera_app.databinding.FragmentCloudImagesStoreBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

/**
 * A simple [Fragment] subclass.
 * Use the [CloudImagesStoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CloudImagesStoreFragment : Fragment() {
    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: com.google.firebase.database.DatabaseReference
    lateinit var binding : FragmentCloudImagesStoreBinding
    private var imageData: Uri? = null
    private var bitmap: Bitmap? = null
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = Firebase.storage
        auth = Firebase.auth
        myRef = Firebase.database("https://photomemories-1a50a-default-rtdb.europe-west1.firebasedatabase.app").getReference("PhotoMemories")
        setupImageChooser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCloudImagesStoreBinding.inflate(inflater)
        binding.btnChooseImageCloud.setOnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
        binding.btnSaveCloud.setOnClickListener {
            if (binding.txtImageDescriptionCloud.text.toString().isNotEmpty() &&
                bitmap != null) {
                storeImage(binding.txtImageDescriptionCloud.text.toString())
            }
        }

        return binding.root
    }

    private fun setupImageChooser() {
        // From https://developer.android.com/training/data-storage/shared/photopicker
        pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                val inputStream = context?.contentResolver?.openInputStream(uri)
                imageData = uri
                bitmap = BitmapFactory.decodeStream(inputStream)
                binding.imgImagepaneCloud.setImageBitmap(bitmap)
            } else {
                Toast.makeText(
                    context, "No image selected",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private fun storeImage(name: String) {
        Log.d("Store Image", "Method is running")
        
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(context, "Please log in before uploading images.", Toast.LENGTH_SHORT).show()
            return
        }

        val currentBitmap = bitmap
        if (currentBitmap == null) {
            Toast.makeText(context, "Please select an Image ", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Convert Bitmap to a Base64 String to completely bypass Firebase Storage billing requirements
            val outputStream = java.io.ByteArrayOutputStream()
            // Compress the image down so it easily fits within Realtime Database's 10MB node limit
            currentBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
            val byteArray = outputStream.toByteArray()
            val base64ImageString = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)

            // Create model entry using the base64 string inside the imageUri field
            val imageModel = ImageModel(
                name.trim(),
                null,
                base64ImageString
            )

            val uploadID = myRef.push().key
            if (uploadID != null) {
                myRef.child(currentUser.uid).child(uploadID)
                    .setValue(imageModel)
                    .addOnSuccessListener {
                        Log.d("Store image", "Image successfully stored in Realtime Database")
                        Toast.makeText(context, "Photo loaded to the cloud (Base64)", Toast.LENGTH_SHORT).show()

                        // reset the user interface
                        binding.imgImagepaneCloud.setImageResource(R.drawable.photo)
                        binding.txtImageDescriptionCloud.setText("")
                        bitmap = null
                        imageData = null
                    }
                    .addOnFailureListener { exception ->
                        Log.e("Store image", "Database storage failed", exception)
                        Toast.makeText(context, "Failed to save: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}