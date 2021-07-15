package com.example.vatis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bookmark_item.view.*

// import kotlinx.android.synthetic.main.bookmark_item.view


class BookmarkAdapter(val bookmarkItems: ArrayList<BookmarkModel>, val context: Context) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: BookmarkModel) {
            itemView.bookmark_title_text.text = model.title
            itemView.bookmark_image.setImageResource(model.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bookmark_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(bookmarkItems[position])
    }

    override fun getItemCount(): Int {
        return bookmarkItems.size
    }

}