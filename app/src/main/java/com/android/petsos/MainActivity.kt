package com.android.petsos

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_main.*
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.firestore.Query


class MainActivity : AppCompatActivity() {

    private lateinit var announcementsRv: RecyclerView
    private lateinit var announcementAdapter: AnnouncementAdapter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var categoryFilterDog: ImageButton
    private lateinit var categoryFilterCat: ImageButton
    private lateinit var categoryFilterBird: ImageButton

    private var dogFilterEnabled: Boolean = false
    private var catFilterEnabled: Boolean = false
    private var birdFilterEnabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.ic_missing)

        swipeRefreshLayout = findViewById(R.id.swipe_refresh)

        categoryFilterDog = findViewById(R.id.category_filter_dog)
        categoryFilterDog.setOnClickListener {
            dogFilterEnabled = !dogFilterEnabled
            if (dogFilterEnabled) {
                it.setBackgroundResource(R.drawable.category_circle_pressed)
                categoryFilterCat.setBackgroundResource(R.drawable.category_circle_normal)
                categoryFilterBird.setBackgroundResource(R.drawable.category_circle_normal)
                catFilterEnabled = false
                birdFilterEnabled = false
            } else {
                it.setBackgroundResource(R.drawable.category_circle_normal)
            }
        }

        categoryFilterCat = findViewById(R.id.category_filter_cat)
        categoryFilterCat.setOnClickListener {
            catFilterEnabled = !catFilterEnabled
            if (catFilterEnabled) {
                it.setBackgroundResource(R.drawable.category_circle_pressed)
                categoryFilterDog.setBackgroundResource(R.drawable.category_circle_normal)
                categoryFilterBird.setBackgroundResource(R.drawable.category_circle_normal)
                dogFilterEnabled = false
                birdFilterEnabled = false
            } else {
                it.setBackgroundResource(R.drawable.category_circle_normal)
            }
        }

        categoryFilterBird = findViewById(R.id.category_filter_others)
        categoryFilterBird.setOnClickListener {
            birdFilterEnabled = !birdFilterEnabled
            if (birdFilterEnabled) {
                it.setBackgroundResource(R.drawable.category_circle_pressed)
                categoryFilterDog.setBackgroundResource(R.drawable.category_circle_normal)
                categoryFilterCat.setBackgroundResource(R.drawable.category_circle_normal)
                catFilterEnabled = false
                dogFilterEnabled = false
            } else {
                it.setBackgroundResource(R.drawable.category_circle_normal)
            }
        }

        fab.setOnClickListener { view ->
            val announcement = Announcement(
                    photos = listOf("url"),
                    title = "TITULO",
                    country = "Brasil",
                    state = "SP",
                    city = "Garça",
                    neighborhood = "Williams",
                    address = "Rua alguma 344",
                    phone_number = "14981996163",
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ",
                    type = 2,
                    timestamp = null
            )
            FirebaseFirestore.getInstance().collection("announcements").add(announcement)

            Snackbar.make(view, "Added announcement", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val baseQuery = FirebaseFirestore.getInstance().collection("announcements")
                .orderBy("timestamp", Query.Direction.ASCENDING)

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(10)
                .setPageSize(20)
                .build()


        val options = FirestorePagingOptions.Builder<Announcement>()
                .setLifecycleOwner(this)
                .setQuery(baseQuery, config, Announcement::class.java)
                .build()

        announcementAdapter = AnnouncementAdapter(options)

        announcementsRv = findViewById(R.id.announcements_rv)
        announcementsRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = announcementAdapter
        }
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
