package com.example.vatis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter2(private  val recommendList : ArrayList<Recommend>,private val cellClickListener: CellClickListener):RecyclerView.Adapter<MyAdapter2.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter2.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter2.MyViewHolder, position: Int) {
        val recommend : Recommend = recommendList[position]
        holder.name.text=recommend.name
        //holder.type.text=recommend.type
        //holder.location.text=recommend.location.toString()
        val data = recommendList[position]
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(data)
        }
    }

    override fun getItemCount(): Int {
        return recommendList.size
    }


    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.name)
        //val location : TextView = itemView.findViewById(R.id.location)
        //val type : TextView = itemView.findViewById(R.id.type)
    }
}