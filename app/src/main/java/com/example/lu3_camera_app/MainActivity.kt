package com.example.lu3_camera_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class MainActivity : AppCompatActivity(), OnCompleteListener<AuthResult> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Use supportFragmentManager instead of the deprecated 'activity.fragmentManager'
        // Attaching loginListener to this activity. I.e., MainActivity now HANDLES loginListener
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            if (fragment is LoginFragment) {
                fragment.loginListener = this
            }
        }

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /*
    Function runs when login task finishes
     */
    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            val email = task.result?.user?.email
            Toast.makeText(this, "Signed in successfully: $email", Toast.LENGTH_SHORT).show()
            navigateToMainChoice()
        } else {
            Toast.makeText(this, "Unable to sign in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainChoice() {
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, MainChoiceFragment())
        transaction.commit()
    }
}
