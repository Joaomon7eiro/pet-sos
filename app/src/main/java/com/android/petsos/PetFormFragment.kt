package com.android.petsos


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.android.petsos.databinding.FragmentPetFormBinding
import kotlinx.android.synthetic.main.fragment_pet_form.*


class PetFormFragment : Fragment() {

    private var type : Int = 0
    private var category : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args : PetFormFragmentArgs by navArgs()

        type = args.type
        category = args.category
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPetFormBinding.inflate(layoutInflater)

        if (type == Constants.TYPE_LOST) {
            binding.labelName.visibility = View.VISIBLE
            binding.nameEt.visibility = View.VISIBLE
            binding.labelReward.visibility = View.VISIBLE
            binding.addReward.visibility = View.VISIBLE
            binding.locationLabel.text = getString(R.string.hint_location_lost)
        }
        binding.next.setOnClickListener {
            var announcement : Announcement? = null
            if (type == Constants.TYPE_LOST) {
                announcement = Announcement(
                    breed = binding.breedEt.text.toString(),
                    age = binding.ageEt.text.toString().toInt(),
                    location = binding.locationEt.toString(),
                    phone_number = binding.phoneEt.text.toString(),
                    title = binding.titleEt.text.toString(),
                    text = binding.textEt.text.toString(),
                    date = binding.dateEt.text.toString(),
                    time = binding.timeEt.text.toString(),
                    name = binding.nameEt.text.toString(),
                    reward = binding.rewardEt.text.toString().toDouble()
                )
            } else {
              announcement = Announcement(
                    breed = binding.breedEt.text.toString(),
                    age = binding.ageEt.text.toString().toInt(),
                    location = binding.locationEt.toString(),
                    phone_number = binding.phoneEt.text.toString(),
                    title = binding.titleEt.text.toString(),
                    text = binding.textEt.text.toString(),
                    date = binding.dateEt.text.toString(),
                    time = binding.timeEt.text.toString()
                )
            }

            val action = PetFormFragmentDirections.actionPetFormFragmentToFormPreviewFragment(announcement)
            Navigation.findNavController(binding.root).navigate(action)
        }


        return binding.root
    }


}
