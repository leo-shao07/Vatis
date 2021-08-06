package com.example.vatis.fragments


import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vatis.CellClickListener
import com.example.vatis.adapters.BookmarkAdapter
import com.example.vatis.items.BookmarkItem
import com.example.vatis.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_bookmark.view.*
import kotlinx.android.synthetic.main.fragment_memo.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class BookmarkFragment : Fragment(), CellClickListener {
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToRealTimeUpdates()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    private fun subscribeToRealTimeUpdates() {
        bookmarksRef.addSnapshotListener { querySnapShot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }

            querySnapShot?.let {
                for (document in it) {
                    val data = document.data
                    val title = data["title"] as String
                    val thumbnail = data["thumbnail"] as String

                    Log.d(TAG, "Title: $title found")

                    GlobalScope.launch(Dispatchers.IO) {
                        val favicon = fetchThumbnailImage(thumbnail)
                        withContext(Dispatchers.Main){
                            bookmarkList.add(BookmarkItem(title, favicon))
                        }
                    }
                }
            }

            view?.bookmark_list?.adapter = BookmarkAdapter(bookmarkList)
            view?.bookmark_list?.layoutManager = LinearLayoutManager(activity)
        }
    }

    private suspend fun fetchThumbnailImage(thumbnail: String): Bitmap {

        // default favicon if failed
        var favicon: Bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.googleg_standard_color_18
        )

        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val bytes = storageRef.child(thumbnail).getBytes(maxDownloadSize).await()
            favicon = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            Log.d(TAG, "$thumbnail download success")
        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
        }

        return favicon
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCellClickListener(data: BookmarkItem) {
        Toast.makeText(this.context, "bookmark item clicked", Toast.LENGTH_LONG).show()
    }


}