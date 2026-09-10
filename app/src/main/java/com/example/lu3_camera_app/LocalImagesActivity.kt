package com.example.lu3_camera_app

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.lu3_camera_app.databinding.ActivityLocalImagesBinding

class LocalImagesActivity : AppCompatActivity() {

    private val storeFragment = LocalImagesStoreFragment()
    private val viewFragment = LocalImagesViewFragment()
    lateinit var binding: ActivityLocalImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLocalImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val manager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, storeFragment)
            transaction.commitAllowingStateLoss()
        }

        binding.btnView.setOnClickListener {
            val manager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, viewFragment)
            transaction.commitAllowingStateLoss()
        }
    }

}