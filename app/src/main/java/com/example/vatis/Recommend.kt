package com.example.vatis

import android.location.Location
import com.google.firebase.firestore.GeoPoint

data class Recommend(var location:GeoPoint?=null,var name:String ?=null,var type:String ?=null)
