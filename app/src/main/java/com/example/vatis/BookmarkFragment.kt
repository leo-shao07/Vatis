package com.example.vatis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class BookmarkFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookmarkList = ArrayList<BookmarkModel>()

        // use dummy data for now
        // to get favicon image: https://www.google.com/s2/favicons?sz=64&domain_url={target_domain}
        // Note: image should be jpg or anything but png/sng
        // TODO: write function to get user's bookmarks from FireStore
        bookmarkList.add(BookmarkModel("bookmark_1", R.drawable.favicon_1))
        bookmarkList.add(BookmarkModel("bookmark_2", R.drawable.favicon_2))
        bookmarkList.add(BookmarkModel("bookmark_3", R.drawable.favicon_3))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}