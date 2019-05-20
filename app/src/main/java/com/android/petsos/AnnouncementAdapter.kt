package com.android.petsos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.petsos.databinding.AnnouncementListItemBinding
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState


class AnnouncementAdapter(
    options: FirestorePagingOptions<Announcement>,
    private var stateListener: LoadingStateListener
) : FirestorePagingAdapter<Announcement, AnnouncementAdapter.AnnouncementViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val binding = AnnouncementListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnnouncementViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: AnnouncementViewHolder, position: Int, announcement: Announcement) {
        viewHolder.bind(announcement)
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADED -> stateListener.setLoadingState(false)
            LoadingState.FINISHED -> stateListener.setLoadingState(false)
            LoadingState.LOADING_INITIAL -> stateListener.setLoadingState(true)
            LoadingState.LOADING_MORE -> stateListener.setLoadingState(true)
            LoadingState.ERROR -> {
                stateListener.setLoadingState(false)
                retry()
            }
            else -> stateListener.setLoadingState(false)
        }
    }

    class AnnouncementViewHolder(private val binding: AnnouncementListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(announcement: Announcement) {
            binding.announcement = announcement
            binding.executePendingBindings()
        }
    }
}