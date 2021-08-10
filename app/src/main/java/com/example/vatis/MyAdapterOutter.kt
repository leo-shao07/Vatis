package com.example.vatis

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.example.vatistest.PlanFragment.Companion.db
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.plan_per_day.view.*

class MyAdapterOutter(private val planItems: ArrayList<Spot_outter>) :
        RecyclerView.Adapter<MyAdapterOutter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: Spot_outter) {
            itemView.dayId.text = item.dayCount
            Log.e("xxx",item.dayCount)
            itemView.innerList.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            itemView.innerList.adapter = MyAdapter(item.spotInnerItemList)
            itemView.innerList.swipeListener = onItemSwipeListener
            itemView.innerList.dragListener = onItemDragListener

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.plan_per_day, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(planItems[position])
    }

    override fun getItemCount(): Int {
        return planItems.size
    }



    private val onItemSwipeListener = object : OnItemSwipeListener<Spot> {
        override fun onItemSwiped(position: Int, direction: OnItemSwipeListener.SwipeDirection, item: Spot): Boolean {
            // Handle action of item swiped
            // Return false to indicate that the swiped item should be removed from the adapter's data set (default behaviour)
            // Return true to stop the swiped item from being automatically removed from the adapter's data set (in this case, it will be your responsibility to manually update the data set as necessary)
//            Log.e("xxx",position.toString())
//            Log.e("xxx",item.toString())
//            val db = Firebase.firestore
//            db.collection("users")
//                    .document("yww9901@gmail.com")
//                    .collection("台北")
//                    .document("R9GyvGw1CeglLVkUGt6B")
//                    .collection("recommendations")
//                    .addSnapshotListener(object: EventListener<QuerySnapshot> {
//                        override fun onEvent(
//                                value: QuerySnapshot?,
//                                error: FirebaseFirestoreException?) {
//                            if(error!=null){
//                                Log.e("Firestore error",error.message.toString())
//                                return
//                            }
//                            for(dc: DocumentChange in value?.documentChanges!!){
//                                if(dc.type== DocumentChange.Type.ADDED){
//                                    if(dc.document.toObject(Spot::class.java).o == locationType){
//                                        if
//                                    }
//
//                                }
//                            }
//                            //myAdapter2.notifyDataSetChanged()
//                        }
//
//
//                    })

            return false
        }
    }

    private val onItemDragListener = object : OnItemDragListener<Spot> {
        override fun onItemDragged(previousPosition: Int, newPosition: Int, item: Spot) {
            // Handle action of item being dragged from one position to another
        }

        override fun onItemDropped(initialPosition: Int, finalPosition: Int, item: Spot) {
            // Handle action of item dropped
            Log.e("xxx",initialPosition.toString())
            Log.e("xxx",finalPosition.toString())
            Log.e("xxx",item.toString())

        }
    }
}