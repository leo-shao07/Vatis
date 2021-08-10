package com.example.vatis.fragments

import android.content.ContentValues
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
import com.example.vatis.R
import com.example.vatis.adapters.RatingAdapter
import com.example.vatis.items.RatingItem
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_memo.view.*
import kotlinx.android.synthetic.main.fragment_rating.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class RatingFragment : Fragment() {
    companion object {
        // Hardcode db reference for now
        val planRef = Firebase.firestore
            .collection("users")
            .document("python_test@gmail.com")
            .collection("folder1")
            .document("file1")
            .collection("plan")
            .orderBy("order.day")


        val storageRef = FirebaseStorage.getInstance().reference.child("python_test@gmail.com")
        var ratingList = ArrayList<RatingItem>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rating, container, false)
        subscribeToRealTimeUpdates()
        return view
    }

    private fun subscribeToRealTimeUpdates() {
        planRef.addSnapshotListener { querySnapShot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapShot?.let {
                CoroutineScope(Dispatchers.IO).launch{
                    fetchPlan(it)
                }
            }
        }
    }

    private suspend fun setLayoutAndAdapter(ratingList: ArrayList<RatingItem>){
        Log.d(TAG, "setting layout manager and adapter...")
        withContext(Dispatchers.Main){
            view?.rating_list?.layoutManager = LinearLayoutManager(activity)
            view?.rating_list?.adapter = RatingAdapter(ratingList)
        }
    }

    private suspend fun fetchPlan(querySnapShot: QuerySnapshot){
        val ratingList = ArrayList<RatingItem>()
        for (document in querySnapShot) {
            Log.d(TAG, "doc id: ${document.id}")
            val data = document.data
            val spotName = data["name"] as String
            val comment = data["comment"] as String
            val imagePath = data["image"] as String
            val score = data["rating"].toString().toFloat()
            val order = data["order"] as Map<*, *>
            val dayPosPair = Pair(order["day"], order["position"])

            withContext(Dispatchers.IO){
                val image = fetchImage(imagePath)
                withContext(Dispatchers.Main){
                    ratingList.add(RatingItem(spotName, comment, score, image, dayPosPair as Pair<Long, Long>))
                }
            }
        }
        Log.d(TAG, "ratingList size after fetch: ${ratingList.size}")
        setLayoutAndAdapter(ratingList)
    }

    private suspend fun fetchImage(imagePath: String): Bitmap {
        // default spotIamge if failed
        var spotImage: Bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.default_image
        )

        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val bytes = storageRef.child(imagePath).getBytes(maxDownloadSize).await()
            spotImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            Log.d(ContentValues.TAG, "$imagePath download success")
        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
        }

        return spotImage
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}