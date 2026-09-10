package com.example.lu3_camera_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lu3_camera_app.databinding.FragmentLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentLoginBinding
    lateinit var loginListener: OnCompleteListener<AuthResult> // custom onCompleteListener in MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth // Initialise firebase auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Check if email and password are not empty
            // If not, register the user. If so, show a toast with failure message
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { // MainActivity not involved here
                        if (it.isSuccessful) {
                            Log.d("Register", "createUserWithEmail:success")
                            Toast.makeText(context,"Account created successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.w("Register", "createUserWithEmail:failure", it.exception)
                            Toast.makeText(context, "Unable to register",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Email and password cannot be empty",
                    Toast.LENGTH_SHORT).show()
            }
        }

        // Login button
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(loginListener) // MainActivity's onComplete invoked
                val user = auth.currentUser
            } else {
                Log.w("Login", "signInWithEmail:failure")
                Toast.makeText(context, "Unable to login",
                    Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}