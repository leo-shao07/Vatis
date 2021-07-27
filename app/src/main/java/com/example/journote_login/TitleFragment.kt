package com.example.journote_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.journote_login.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater, R.layout.fragment_title, container, false
        )

        binding.titleLoginButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_titleFragment_to_loginFragment)
        }

        binding.titleSignupButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_titleFragment_to_signUpFragment)
        }


        return binding.root
    }
}