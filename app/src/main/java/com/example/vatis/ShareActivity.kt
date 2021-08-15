package com.example.vatis

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vatis.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_share.*


class ShareActivity : AppCompatActivity() {
    companion object {
        lateinit var planRef: DocumentReference

        lateinit var shareOptionFragment: ShareOptionFragment
        lateinit var shareIgSelectFragment: ShareIgSelectFragment
        lateinit var shareJournalSelectFragment: ShareJournalSelectFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        val userEmail = intent.getStringExtra("userEmail")!!
        val folderName = intent.getStringExtra("folderName")!!
        val planName = intent.getStringExtra("planName")!!

        // init plan reference to pass in
        planRef = Firebase.firestore
            .collection("users")
            .document(userEmail)
            .collection(folderName)
            .document(planName)

        // init fragments
        shareOptionFragment = ShareOptionFragment(planRef)
        shareIgSelectFragment = ShareIgSelectFragment(planRef)
        shareJournalSelectFragment = ShareJournalSelectFragment(planRef)

        initFragment()
    }

    private fun initFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(R.id.share_fragment_container, shareOptionFragment, "SHARE_OPTION")
            .add(R.id.share_fragment_container, shareIgSelectFragment, "SHARE_IG_SELECT")
            .add(R.id.share_fragment_container, shareJournalSelectFragment, "SHARE_JOURNAL_SELECT")
            .hide(shareIgSelectFragment)
            .hide(shareJournalSelectFragment)
            .commit()
    }

}

