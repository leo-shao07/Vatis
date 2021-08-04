package com.example.vatis.fragments


import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vatis.adapters.BookmarkAdapter
import com.example.vatis.items.BookmarkItem
import com.example.vatis.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_bookmark.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class BookmarkFragment : Fragment() {
    // TODO: parameters needed: user, folderName, fileName

    companion object {
        // Hardcode db reference for now
        val bookmarksRef = Firebase.firestore
            .collection("users")
            .document("python_test@gmail.com")
            .collection("folder1")
            .document("file1")
            .collection("bookmarks")
            .orderBy("title")
        val storageRef = FirebaseStorage.getInstance().reference
        val bookmarkList = ArrayList<BookmarkItem>()
        val titleThumbnailList = ArrayList<Pair<String, String>>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)
        val titleThumbnailList = ArrayList<Pair<String, String>>()

        // FireStore functionalities
        bookmarksRef.get().addOnSuccessListener { task ->
            for (document in task) {
                val data = document.data
                val title = data["title"] as String
                val thumbnail = data["thumbnail"] as String
                titleThumbnailList.add(Pair(title, thumbnail))

                // get url for direct access maybe
                // val url = data["url"]
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
            Toast.makeText(activity, "bookmarks retrieve failed", Toast.LENGTH_SHORT).show()
        }
        .addOnCompleteListener {
            addBookmarkItems(view, titleThumbnailList)
        }

        //TODO: add listener for realtime changes

        return view
    }

    private fun addBookmarkItems(view: View, titleThumbnailList: ArrayList<Pair<String, String>>) = CoroutineScope(Dispatchers.IO).launch {
        for (item in titleThumbnailList) {
            val title = item.first
            val thumbnail = item.second

            try {
                val maxDownloadSize = 5L * 1024 * 1024
                val bytes = storageRef.child(thumbnail).getBytes(maxDownloadSize).await()
                val favicon = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                withContext(Dispatchers.Main){
                    bookmarkList.add(BookmarkItem(title, favicon))
                    Log.d(TAG, "$thumbnail successfully downloaded")
                }
            } catch (e: Exception) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                val favicon = BitmapFactory.decodeResource( // return default favicon if failed
                    resources,
                    R.drawable.googleg_standard_color_18
                )
                withContext(Dispatchers.Main){
                    bookmarkList.add(BookmarkItem(title, favicon))
                }
            }
        }
        Log.d(TAG, "Bookmark list size (after): ${bookmarkList.size}")
        withContext(Dispatchers.Main){
            view.bookmark_list.adapter = BookmarkAdapter(bookmarkList)
            view.bookmark_list.layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}