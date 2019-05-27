package com.android.petsos

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_main.*
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import androidx.paging.PagedList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.petsos.databinding.ActivityMainBinding
import com.google.firebase.firestore.Query

const val KEY_DOG_FILTER = "key_dog_filter"
const val KEY_CAT_FILTER = "key_cat_filter"
const val KEY_BIRD_FILTER = "key_bird_filter"

class MainActivity : AppCompatActivity(),
    SwipeRefreshLayout.OnRefreshListener,
    LoadingStateListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var announcementAdapter: AnnouncementAdapter

    private var dogFilterEnabled: Boolean = false
    private var catFilterEnabled: Boolean = false
    private var birdFilterEnabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.ic_missing)

        if (savedInstanceState != null) {
            dogFilterEnabled = savedInstanceState.getBoolean(KEY_DOG_FILTER)
            catFilterEnabled = savedInstanceState.getBoolean(KEY_CAT_FILTER)
            birdFilterEnabled = savedInstanceState.getBoolean(KEY_BIRD_FILTER)
            handleFilterBackground(dogFilterEnabled, catFilterEnabled, birdFilterEnabled)
        }

        binding.main.swipeRefresh.setOnRefreshListener(this)

        binding.main.categoryFilterDog.setOnClickListener {
            dogFilterEnabled = !dogFilterEnabled
            if (dogFilterEnabled) {
                catFilterEnabled = false
                birdFilterEnabled = false
            }
            handleFilterBackground(dogFilterEnabled, catFilterEnabled, birdFilterEnabled)
        }

        binding.main.categoryFilterCat.setOnClickListener {
            catFilterEnabled = !catFilterEnabled
            if (catFilterEnabled) {
                dogFilterEnabled = false
                birdFilterEnabled = false
            }
            handleFilterBackground(dogFilterEnabled, catFilterEnabled, birdFilterEnabled)
        }

        binding.main.categoryFilterOthers.setOnClickListener {
            birdFilterEnabled = !birdFilterEnabled
            if (birdFilterEnabled) {
                catFilterEnabled = false
                dogFilterEnabled = false
            }
            handleFilterBackground(dogFilterEnabled, catFilterEnabled, birdFilterEnabled)
        }

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddPetActivity::class.java)
            startActivity(intent)
//            val announcement = Announcement(
//                photos = listOf("url"),
//                title = "TITULO",
//                location = "Garça",
//                phone_number = "14981996163",
//                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
//                type = 2
//            )
//            FirebaseFirestore.getInstance().collection("announcements").add(announcement)
//
//            Snackbar.make(view, "Added announcement", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }

        setupAdapter()
        setupRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_DOG_FILTER, dogFilterEnabled)
        outState.putBoolean(KEY_CAT_FILTER, catFilterEnabled)
        outState.putBoolean(KEY_BIRD_FILTER, birdFilterEnabled)
    }

    private fun setupAdapter() {
        val baseQuery = FirebaseFirestore.getInstance().collection("announcements")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .whereEqualTo("city", "Garça")

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build()

        val options = FirestorePagingOptions.Builder<Announcement>()
            .setLifecycleOwner(this)
            .setQuery(baseQuery, config, Announcement::class.java)
            .build()

        announcementAdapter = AnnouncementAdapter(options, this)
    }

    private fun setupRecyclerView() {
        binding.main.announcementsRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = announcementAdapter
        }
    }


    private fun handleFilterBackground(
        dogFilterEnabled: Boolean,
        catFilterEnabled: Boolean,
        birdFilterEnabled: Boolean
    ) {
        when {
            dogFilterEnabled -> {
                binding.main.categoryFilterDog.setBackgroundResource(R.drawable.category_circle_pressed)
                binding.main.categoryFilterCat.setBackgroundResource(R.drawable.category_circle_normal)
                binding.main.categoryFilterOthers.setBackgroundResource(R.drawable.category_circle_normal)
            }
            catFilterEnabled -> {
                binding.main.categoryFilterCat.setBackgroundResource(R.drawable.category_circle_pressed)
                binding.main.categoryFilterDog.setBackgroundResource(R.drawable.category_circle_normal)
                binding.main.categoryFilterOthers.setBackgroundResource(R.drawable.category_circle_normal)
            }
            birdFilterEnabled -> {
                binding.main.categoryFilterOthers.setBackgroundResource(R.drawable.category_circle_pressed)
                binding.main.categoryFilterDog.setBackgroundResource(R.drawable.category_circle_normal)
                binding.main.categoryFilterCat.setBackgroundResource(R.drawable.category_circle_normal)
            }
            else -> {
                binding.main.categoryFilterOthers.setBackgroundResource(R.drawable.category_circle_normal)
                binding.main.categoryFilterDog.setBackgroundResource(R.drawable.category_circle_normal)
                binding.main.categoryFilterCat.setBackgroundResource(R.drawable.category_circle_normal)
            }
        }
    }

    override fun setLoadingState(state: Boolean) {
        binding.main.swipeRefresh.isRefreshing = state
    }

    override fun onRefresh() {
        setupAdapter()
        binding.main.announcementsRv.adapter = announcementAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
