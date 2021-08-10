package com.example.vatistest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_plan.*
import kotlinx.android.synthetic.main.fragment_plan.view.*


@SuppressLint("ResourceType")
class PlanFragment : Fragment() {
    companion object {
        // Hardcode db reference for now
        val db = Firebase.firestore
                .collection("users")
                .document("jack@gmail.com")
                .collection("北部")
                .document("台北一日")
                .collection("plan")
                .orderBy("order.day")

        var planItemList = ArrayList<Spot_outter>()
    }

    // TODO: Rename and change types of parameters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        subscribeToRealTimeUpdates()
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        btn_AddSpot.setOnClickListener{
            val intent = Intent(this.context, AddSpotActivity::class.java)
            startActivity(intent)
        }


    }

    private fun buildPlanList(queryPlanItemList: ArrayList<Spot>): ArrayList<Spot_outter> {
        val planItemList = ArrayList<Spot_outter>()
        val dayPlanMap = queryPlanItemList.sortedWith(
                compareBy( { it.order.first}, {it.order.second} )
        ).groupBy {
            it.order.first
        }

        for ((day, planSubItemList) in dayPlanMap) {
            planItemList.add(Spot_outter("Day $day", planSubItemList as ArrayList<Spot>))
        }
        return planItemList
    }

    private fun subscribeToRealTimeUpdates() {
        db.addSnapshotListener { querySnapShot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapShot?.let {
                val queryPlanItemList = ArrayList<Spot>()
                for (document in it) {
                    val data = document.data
                    val spotName = data["name"] as String
                    val type = data["type"] as String
                    val order = data["order"] as Map<*, *>
                    val dayPosPair = Pair(order["day"], order["position"])

                    // add to planSubItemList here
                    queryPlanItemList.add(Spot(spotName,type,dayPosPair as Pair<Long, Long>))
                }
                planItemList = buildPlanList(queryPlanItemList)
            }

            view?.plan_outter?.layoutManager = LinearLayoutManager(activity)
            view?.plan_outter?.adapter = MyAdapterOutter(planItemList)
        }
    }


//    private fun EventChangeListener(){
////        db = FirebaseFirestore.getInstance()
//        db.collection("users")
//                .document("jack@gmail.com")
//                .collection("北部")
//                .document("台北一日")
//                .collection("plan")
//                .orderBy("x")
//                .orderBy("y")
//                .addSnapshotListener(object: EventListener<QuerySnapshot> {
//                    override fun onEvent(
//                            value: QuerySnapshot?,
//                            error: FirebaseFirestoreException?) {
//                        if(error!=null){
//                            Log.e("Firestore error",error.message.toString())
//                            return
//                        }
//                        for(dc: DocumentChange in value?.documentChanges!!){
//                            if(dc.type== DocumentChange.Type.ADDED){
//
//                                //spotArrayList.toMutableList().add(dc.document.toObject(Spot::class.java))
//                                myAdapter.addItem(dc.document.toObject(Spot::class.java));
//                                Log.e("xxx",dc.document.toObject(Spot::class.java).toString());
//                            }
//                        }
//                        //myAdapter.notifyDataSetChanged()
//
//                    }
//
//
//                })
//    }

}