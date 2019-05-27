package com.android.petsos


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.android.petsos.databinding.FragmentChooseTypeBinding


class ChooseTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentChooseTypeBinding.inflate(layoutInflater)

        binding.lostChoice.setOnClickListener {
            val action = ChooseTypeFragmentDirections.actionChooseTypeFragmentToChooseCategoryFragment(1)
            Navigation.findNavController(binding.root).navigate(action)
        }

        binding.foundChoice.setOnClickListener {
            val action = ChooseTypeFragmentDirections.actionChooseTypeFragmentToChooseCategoryFragment(2)
            Navigation.findNavController(binding.root).navigate(action)
        }

        return binding.root
    }


}
