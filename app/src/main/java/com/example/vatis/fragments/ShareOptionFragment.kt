package com.example.vatis.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vatis.CellClickListener
import com.example.vatis.adapters.MemoItemAdapter
import com.example.vatis.items.MemoSubItem
import com.example.vatis.R
import com.example.vatis.ShareActivity
import com.example.vatis.items.MemoItem
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_memo.view.*
import kotlinx.android.synthetic.main.fragment_share_option.*
import kotlinx.android.synthetic.main.fragment_share_option.view.*


class ShareOptionFragment(private val planRef: DocumentReference) : Fragment() {
    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_share_option, container, false)

        view.share_ig_text.setOnClickListener {
            Toast.makeText(this.context, "share to IG", Toast.LENGTH_SHORT).show()
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.show(ShareActivity.shareIgSelectFragment)
                ?.addToBackStack("SHARE_OPTION")
                ?.hide(this)
                ?.commit()
        }

        view.share_journal_text.setOnClickListener {
            Toast.makeText(this.context, "share as journal", Toast.LENGTH_SHORT).show()
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.show(ShareActivity.shareJournalSelectFragment)
                ?.addToBackStack("SHARE_OPTION")
                ?.hide(this)
                ?.commit()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}