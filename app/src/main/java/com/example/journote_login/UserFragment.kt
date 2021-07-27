package com.example.journote_login


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.journote_login.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserFragment : Fragment() {

    private val db = Firebase.firestore

    companion object {
        val TAG = "UserFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentUserBinding>(
            inflater, R.layout.fragment_user, container, false
        )

        verifyUserIsLoggedIn(binding.userText)

        binding.logoutButton.setOnClickListener { userSignOut() }

        return binding.root
    }

    private fun verifyUserIsLoggedIn(textView: TextView) {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            findNavController()?.navigate(R.id.action_userFragment_to_titleFragment)
        } else {
            getUserName(textView)
            Log.d(TAG, "Successfully login with uid: $uid")
        }
    }

    private fun getUserName(textView: TextView){
        val user = Firebase.auth.currentUser
        user?.let {
            val userEmail = user.email
            val docRef = userEmail?.let { it1 -> db.collection("users").document(it1) }
            if (docRef != null) {
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                            val username = document.data?.get("name")
                            textView.text = "Welcome!\u2000"+username

                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }

        }

    }

    private fun userSignOut(){
        FirebaseAuth.getInstance().signOut()
        view?.findNavController()?.navigate(R.id.action_userFragment_to_titleFragment)
    }
}