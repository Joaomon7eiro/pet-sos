package com.android.petsos

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception
import java.text.DateFormat
import java.text.DateFormat.SHORT

class AnnouncementAdapter(options: FirestorePagingOptions<Announcement>)
    : FirestorePagingAdapter<Announcement, AnnouncementAdapter.AnnouncementViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.announcement_list_item, parent, false)
        return AnnouncementViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: AnnouncementViewHolder, position: Int, announcement: Announcement) {
        viewHolder.bind(announcement)
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
        }
    }



    class AnnouncementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photoImageView: ImageView = itemView.findViewById(R.id.photos_iv)
        private val rewardTextView: TextView = itemView.findViewById(R.id.reward_tv)
        private val petNameTextView: TextView = itemView.findViewById(R.id.pet_name_tv)
        private val petBreedTextView: TextView = itemView.findViewById(R.id.pet_breed_tv)
        private val titleTextView: TextView = itemView.findViewById(R.id.title_tv)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.description_tv)
        private val phoneNumberTextView: TextView = itemView.findViewById(R.id.phone_number_tv)
        private val timeTextView: TextView = itemView.findViewById(R.id.time_tv)

        fun bind(announcement: Announcement) {
            Picasso.get().load(R.drawable.lost_dog).into(photoImageView)
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
                                photoImageView.setBackgroundColor(color)
                            }
                        }
                    }
                }
            })

            titleTextView.text = announcement.title
            descriptionTextView.text = announcement.text
            phoneNumberTextView.text = announcement.phone_number

            val date = DateFormat.getDateTimeInstance(SHORT, SHORT).format(announcement.timestamp)
            timeTextView.text = date

            val res = itemView.context.resources
            if (announcement.type == 1) {
                var petName = announcement.name
                if (petName == "") {
                    petName = res.getString(R.string.unknown)
                }
                petNameTextView.visibility = View.VISIBLE
                petNameTextView.text = res.getString(R.string.pet_name, petName)
            } else {
                petBreedTextView.gravity = Gravity.START
            }

            var petBreed = announcement.breed
            if (petBreed == "") {
                petBreed = res.getString(R.string.breed_unknown)
            }
            petBreedTextView.text = res.getString(R.string.pet_breed, petBreed)

            if (announcement.reward!! > 0) {
                rewardTextView.visibility = View.VISIBLE
            }
        }
    }
}