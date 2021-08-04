package com.example.vatis.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import com.example.vatis.items.BookmarkItem
import com.example.vatis.items.MemoItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_memo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class MemoFragment : Fragment(), CellClickListener {
    companion object {
        // Hardcode db reference for now
        val planRef = Firebase.firestore
            .collection("users")
            .document("python_test@gmail.com")
            .collection("folder1")
            .document("file1")
            .collection("plan")
            .orderBy("order.day")

        // for firestore query, sort later
        val queryMemoItemList = ArrayList<MemoSubItem>()
        // main memo item list
        val memoItemList = ArrayList<MemoItem>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_memo, container, false)

        fetchMemoSubList()

        // TODO: add OnSnapshotListener to capture Plan order change

        view.memo_list.layoutManager = LinearLayoutManager(activity)
        view.memo_list.adapter = MemoItemAdapter(memoItemList, this)
        return view
    }

    private fun fetchMemoSubList() = CoroutineScope(Dispatchers.IO).launch{
        try {
            // FireStore functionalities
            planRef.get().addOnSuccessListener { task ->
                for (document in task) {
                    val data = document.data
                    val spotName = data["name"] as String
                    val memo = data["memo"] as String
                    val order = data["order"] as Map<*, *>
                    val dayPosPair = Pair(order["day"], order["position"])

                    // add to memoSubItemList here
                    queryMemoItemList.add(MemoSubItem(spotName, memo, dayPosPair as Pair<Long, Long>))
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
            .addOnCompleteListener {
                Log.d(TAG, "fetch plan collection completed")
                buildMemoList(queryMemoItemList)
            }
        } catch (e: Exception) {
            Toast.makeText(this@MemoFragment.context, "memoList build failed", Toast.LENGTH_LONG).show()
        }

    }

    // build memo list from multiple memoSubLists
    private fun buildMemoList(queryMemoItemList: ArrayList<MemoSubItem>) {
        val dayMemoMap = queryMemoItemList.sortedWith(
            compareBy( { it.order.first}, {it.order.second} )
        ).groupBy {
            it.order.first
        }

        for ((day, memoSubItemList) in dayMemoMap) {
            memoItemList.add(MemoItem("Day $day", memoSubItemList as ArrayList<MemoSubItem>))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCellClickListener(data: MemoSubItem) {
        val editDialogFragment = MemoEditDialogFragment(data)
        editDialogFragment.show(childFragmentManager, "MemoEditDialog")
    }


}