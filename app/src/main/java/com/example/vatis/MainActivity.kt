package com.example.vatis

import android.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private val planFragment = PlanFragment()
        private val memoFragment = MemoFragment()
        private val ratingFragment = RatingFragment()
        private val bookmarkFragment = BookmarkFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(bookmarkFragment)

        bottom_navigation.selectedItemId = R.id.page_1
        bottom_navigation.setOnNavigationItemSelectedListener(listener)
    }

    private fun replaceFragment(fragment:Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment).commit()

    }

    private var listener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item : MenuItem): Boolean {
            when (item.itemId) {
                R.id.page_1 -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.fragment_container, planFragment).commit()
                }
                R.id.page_2 -> {
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.fragment_container, memoFragment).commit()
                }
                R.id.page_3 -> {
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.fragment_container, ratingFragment).commit()
                }
                R.id.page_4 -> {
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.fragment_container, bookmarkFragment).commit()
                }
            }
            return true
        }
    }

}

