package com.example.vatis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter

class MyAdapter(spotList : List<Spot> = emptyList())
    : DragDropSwipeAdapter<Spot, MyAdapter.ViewHolder>(spotList) {

    class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView) {
        val typeIcon: ImageView = itemView.findViewById(R.id.type_icon)
        val itemText: TextView = itemView.findViewById(R.id.item_text)
        val dragIcon: ImageView = itemView.findViewById(R.id.drag_icon)
    }

    override fun getViewHolder(itemLayout: View) = MyAdapter.ViewHolder(itemLayout)

    override fun onBindViewHolder(spot: Spot, viewHolder: MyAdapter.ViewHolder, position: Int) {
        // Here we update the contents of the view holder's views to reflect the item's data
        //viewHolder.itemText.text = item
//        val spot : Spot = spotList[position]
        viewHolder.itemText.text=spot.name
        if(spot.type=="hotel"){
            viewHolder.typeIcon.setImageResource(R.drawable.hotel)
        }
        else if(spot.type=="restaurant"){
            viewHolder.typeIcon.setImageResource(R.drawable.restaurant)
        }
        else{
            viewHolder.typeIcon.setImageResource(R.drawable.scenic)
        }

    }

    override fun getViewToTouchToStartDraggingItem(item: Spot, viewHolder: MyAdapter.ViewHolder, position: Int): View? {
        // We return the view holder's view on which the user has to touch to drag the item
        return viewHolder.dragIcon
    }
}
