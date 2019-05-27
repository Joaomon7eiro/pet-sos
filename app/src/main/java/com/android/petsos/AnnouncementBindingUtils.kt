package com.android.petsos

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception
import java.text.DateFormat

@BindingAdapter("announcementTitle")
fun TextView.setAnnouncementTitle(announcement: Announcement?) {
    announcement?.let {
        text = announcement.title
    }
}

@BindingAdapter("description")
fun TextView.setDescription(announcement: Announcement?) {
    announcement?.let {
        text = announcement.text
    }
}

@BindingAdapter("phoneNumber")
fun TextView.setPhoneNumber(announcement: Announcement?) {
    announcement?.let {
        text = announcement.phone_number
    }
}

@BindingAdapter("date")
fun TextView.setDate(announcement: Announcement?) {
    announcement?.let {
        try {
            text = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(announcement.timestamp)
        } catch (e : Exception) {

        }
    }
}

@BindingAdapter("name")
fun TextView.setName(announcement: Announcement?) {
    announcement?.let {
        if (announcement.type == 1) {
            var petName = announcement.name
            if (petName == "") {
                petName = context.resources.getString(R.string.unknown)
            }
            visibility = View.VISIBLE
            text = context.resources.getString(R.string.pet_name, petName)
        }
    }
}

@BindingAdapter("breed")
fun TextView.setBreed(announcement: Announcement?) {
    announcement?.let {
        var petBreed = announcement.breed
        if (petBreed == "") {
            petBreed = context.resources.getString(R.string.breed_unknown)
        }
        text = context.resources.getString(R.string.pet_breed, petBreed)
        if (announcement.type != 1) {
            gravity = Gravity.CENTER
        }
    }
}

@BindingAdapter("reward")
fun TextView.setReward(announcement: Announcement?) {
    announcement?.let {
        if (announcement.reward!! > 0) {
            visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("image")
fun ImageView.setImage(announcement: Announcement?) {
    announcement?.let {
        Picasso.get().load(R.drawable.lost_dog).into(this)
        Picasso.get().load(R.drawable.lost_dog).into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap != null) {
                    Palette.from(bitmap).generate { palette ->
                        val color = palette?.vibrantSwatch?.rgb
                        if (color != null) {
                            setBackgroundColor(color)
                        }
                    }
                }
            }
        })
    }
}