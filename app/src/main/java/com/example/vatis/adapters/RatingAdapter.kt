package com.example.vatis.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vatis.R
import com.example.vatis.items.RatingItem
import kotlinx.android.synthetic.main.rating_item.view.*

class RatingAdapter(val ratingItems: ArrayList<RatingItem>): RecyclerView.Adapter<RatingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: RatingItem) {
            itemView.rating_spot_name_text.text = item.spotName
            itemView.rating_comment_text.text = item.comment
            itemView.rating_score_text.text = item.score.toString()
            itemView.rating_image.setImageBitmap(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rating_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RatingAdapter.ViewHolder, position: Int) {
        val data = ratingItems[position]
        // TODO: add onClick for imageView to upload image

        holder.bindItems(data)
    }

    override fun getItemCount(): Int {
        return ratingItems.size
    }
}