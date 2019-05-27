package com.android.petsos


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.petsos.databinding.FragmentFormPreviewBinding


class FormPreviewFragment : Fragment() {

    private lateinit var announcement: Announcement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args : FormPreviewFragmentArgs by navArgs()

        announcement = args.announcement
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFormPreviewBinding.inflate(layoutInflater)

        binding.preview.announcement = announcement

        return binding.root
    }


}
