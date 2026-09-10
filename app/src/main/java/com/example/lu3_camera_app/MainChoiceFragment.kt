package com.example.lu3_camera_app

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lu3_camera_app.databinding.FragmentLocalImagesStoreBinding
import com.example.lu3_camera_app.databinding.FragmentMainChoiceBinding
import com.google.android.material.card.MaterialCardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainChoiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainChoiceFragment : Fragment() {
    lateinit var binding: FragmentMainChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainChoiceBinding.inflate(inflater)

        binding.cardLocal.setOnClickListener {
            val openLocalImages = Intent(
                activity,
                LocalImagesActivity::class.java
            )
            startActivity(openLocalImages)
        }

        return binding.root
    }
}