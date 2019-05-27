package com.android.petsos

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

import kotlinx.android.synthetic.main.activity_add_pet.*

class AddPetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.add_pet_content)?.childFragmentManager
            if (fragment != null && fragment.backStackEntryCount > 0) {
                Navigation.findNavController(this, R.id.add_pet_content).popBackStack()
            } else {
                onBackPressed()
            }
        }
    }
}
