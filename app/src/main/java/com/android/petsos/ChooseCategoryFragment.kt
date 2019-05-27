package com.android.petsos


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.android.petsos.databinding.FragmentChooseCategoryBinding
import com.android.petsos.databinding.FragmentChooseTypeBinding


class ChooseCategoryFragment : Fragment() {

    private var type : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args : ChooseCategoryFragmentArgs by navArgs()
        type = args.type
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChooseCategoryBinding.inflate(layoutInflater)

        binding.categoryDog.setOnClickListener {
            val action = ChooseCategoryFragmentDirections.actionChooseCategoryFragmentToPetFormFragment(type, 1)
            Navigation.findNavController(binding.root).navigate(action)
        }

        binding.categoryCat.setOnClickListener {
            val action = ChooseCategoryFragmentDirections.actionChooseCategoryFragmentToPetFormFragment(type, 2)
            Navigation.findNavController(binding.root).navigate(action)
        }

        binding.categoryOthers.setOnClickListener {
            val action = ChooseCategoryFragmentDirections.actionChooseCategoryFragmentToPetFormFragment(type, 3)
            Navigation.findNavController(binding.root).navigate(action)
        }

        return binding.root
    }


}
