package com.example.lu3_camera_app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lu3_camera_app.Database.DatabaseHandler
import com.example.lu3_camera_app.Model.ImageModel
import com.example.lu3_camera_app.databinding.FragmentLocalImagesStoreBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LocalImagesStoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocalImagesStoreFragment : Fragment() {
    lateinit var binding: FragmentLocalImagesStoreBinding
    private var bitmap: Bitmap? = null
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var imagedb: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupImageChooser() // Set up image chooser (gets invoked before user selects image)
        imagedb = DatabaseHandler(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocalImagesStoreBinding.inflate(inflater, container, false)

        binding.btnChooseImage.setOnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }

        binding.btnSave.setOnClickListener {
            val name = binding.txtImageDescription.text.toString()
            if (name.isNotBlank() && bitmap != null) {
                val imageToStore = ImageModel(name, bitmap, null)
                imagedb.storeImageLocal(imageToStore)
                // Clear inputs after saving
                binding.txtImageDescription.text?.clear()
                binding.ivPreview.setImageDrawable(null)
                bitmap = null
            } else {
                Toast.makeText(context, "Please enter a name and choose an image", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    /*
    Choose what to do with the media once chosen by user
     */
    private fun setupImageChooser()
    {
        pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        )
        { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null)
            {
                // Receiving chosen media as URI
                val inputStream = context?.contentResolver?.openInputStream(uri)
                // Decoding URI to Bitmap and put it in ImageView
                bitmap = BitmapFactory.decodeStream(inputStream)
                binding.ivPreview.setImageBitmap(bitmap)
            } else {
                Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

}