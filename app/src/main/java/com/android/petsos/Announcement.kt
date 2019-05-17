package com.android.petsos

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Announcement(
        var photos: List<String>? = null,
        var title: String = "",
        var breed: String = "",
        var country: String = "",
        var state: String = "",
        var city: String = "",
        var neighborhood: String = "",
        var address: String = "",
        var age: Int = -1,
        var phone_number: String = "",
        var text: String = "",
        var name: String = "",
        var reward: Double? = 0.0,
        var type: Int = 0,
        @ServerTimestamp
        var timestamp: Date? = null
)