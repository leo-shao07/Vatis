package com.example.journote_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.journote_login.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login, container, false
        )

        binding.loginButton.setOnClickListener {
            performLogin(binding.emailLogin.text.toString(), binding.passwordLogin.text.toString())
        }

        binding.haveNoAccountText.setOnClickListener{view: View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)

        }

        return binding.root
    }

    private fun performLogin(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Please fill out email/pw.", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d("Login", "Successfully logged in: ${it.result?.user?.uid}")
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_folderFragment)
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

}