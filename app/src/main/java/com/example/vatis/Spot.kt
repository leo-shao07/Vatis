package com.example.vatis

import com.google.firebase.firestore.GeoPoint

data class Spot(var name:String ?=null, var type:String ?=null,var order:Pair<Long,Long>)
