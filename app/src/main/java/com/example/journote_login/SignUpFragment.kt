package com.example.journote_login


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.journote_login.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment() {

    private val db = Firebase.firestore

    companion object {
        val TAG = "SignUpFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            inflater, R.layout.fragment_sign_up, container, false
        )

        binding.signupFinishButton.setOnClickListener {
            performRegister(binding.emailSignup.text.toString(), binding.passwordSignup.text.toString(), binding.usernameSignup.text.toString())
        }

        binding.alreadyHaveAccountText.setOnClickListener {
            Log.d(TAG, "Try to show login activity")
            view?.findNavController()?.navigate(R.id.action_signUpFragment_to_loginFragment)
        }


        return binding.root
    }

    private fun performRegister(email: String, password: String, username: String) {

        if (username.isEmpty()||email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Please enter text in User name/Email/Password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "Attempting to create user with email: $email")

        // Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else if successful
                Log.d(TAG, "Successfully created user with uid: ${it.result?.user?.uid}")

                saveUserToFirestore(username, email)

                view?.findNavController()?.navigate(R.id.action_signUpFragment_to_folderFragment)

            }
            .addOnFailureListener{
                Log.d(TAG, "Failed to create user: ${it.message}")
                Toast.makeText(activity, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserToFirestore(username: String, email: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""

        val user = hashMapOf(
            "uid" to uid,
            "name" to username
        )

        db.collection("users").document(email)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot  successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }


}