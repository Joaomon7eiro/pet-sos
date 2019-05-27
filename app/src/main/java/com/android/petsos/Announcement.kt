package com.android.petsos

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Announcement(
    var photos: List<String>? = null,
    var title: String = "",
    var breed: String = "",
    var location: String = "",
    var place_id: String = "",
    var age: Int = -1,
    var phone_number: String = "",
    var text: String = "",
    var name: String? = "",
    var reward: Double? = 0.0,
    var type: Int = 0,
    @ServerTimestamp
    var timestamp: Date? = null,
    var date: String = "",
    var time: String = ""
) : Parcelable