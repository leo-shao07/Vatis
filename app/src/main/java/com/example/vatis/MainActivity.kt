package com.example.vatis

import android.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import java.lang.reflect.Array.newInstance

class MainActivity : AppCompatActivity() {

    private val planFragment = PlanFragment()
    private val memoFragment = MemoFragment()
    private val ratingFragment = RatingFragment()
    private val bookmarkFragment = BookmarkFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(planFragment)

        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    // Respond to navigation item 1 click(PLAN)
                    replaceFragment(planFragment)
                    true
                }
                R.id.page_2 -> {//memo
                    replaceFragment(memoFragment)
                    true
                }
                R.id.page_3 -> {//rating
                    replaceFragment(ratingFragment)
                    true
                }
                R.id.page_4 -> {//bookmark
                    replaceFragment(bookmarkFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment:Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()

    }

}