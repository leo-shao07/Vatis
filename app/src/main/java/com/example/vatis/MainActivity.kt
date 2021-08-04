package com.example.vatis

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vatis.fragments.BookmarkFragment
import com.example.vatis.fragments.MemoFragment
import com.example.vatis.fragments.PlanFragment
import com.example.vatis.fragments.RatingFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        private val planFragment = PlanFragment()
        private val memoFragment = MemoFragment()
        private val ratingFragment = RatingFragment()
        private val bookmarkFragment = BookmarkFragment()
        val auth = FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user: FirebaseUser? = auth.currentUser
        if (user != null) {
            // user action

        } else {
            signInAsAnonymous()
        }

        initFragment()

        bottom_navigation.selectedItemId = R.id.page_1
        bottom_navigation.setOnNavigationItemSelectedListener(listener)
    }

    private fun signInAsAnonymous(){
        auth.signInAnonymously()
            .addOnSuccessListener {
                Log.d(TAG, "login successful")
            }
            .addOnFailureListener {
                Log.d(TAG, "login failed")
            }
            .addOnCompleteListener {

            }

    }

    private fun initFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(R.id.fragment_container, planFragment)
            .add(R.id.fragment_container, memoFragment)
            .add(R.id.fragment_container, ratingFragment)
            .add(R.id.fragment_container, bookmarkFragment)
            .hide(memoFragment)
            .hide(ratingFragment)
            .hide(bookmarkFragment)
            .commit()

//        transaction.replace(R.id.fragment_container,planFragment).commit()
    }

    private var listener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    supportFragmentManager.beginTransaction()
                        .hide(memoFragment)
                        .hide(ratingFragment)
                        .hide(bookmarkFragment)
                        .show(planFragment)
                        .commit()
                }
                R.id.page_2 -> {
                    supportFragmentManager.beginTransaction()
                        .hide(planFragment)
                        .hide(ratingFragment)
                        .hide(bookmarkFragment)
                        .show(memoFragment)
                        .commit()
                }
                R.id.page_3 -> {
                    supportFragmentManager.beginTransaction()
                        .hide(memoFragment)
                        .hide(planFragment)
                        .hide(bookmarkFragment)
                        .show(ratingFragment)
                        .commit()
                }
                R.id.page_4 -> {
                    supportFragmentManager.beginTransaction()
                        .hide(memoFragment)
                        .hide(ratingFragment)
                        .hide(planFragment)
                        .show(bookmarkFragment)
                        .commit()
                }
            }
            true
        }

}

