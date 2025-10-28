package com.example.androidpractice.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidpractice.R
import com.example.androidpractice.activities.DiscographyActivity.Companion.PE_GROUP_JSON
import com.example.androidpractice.adapters.SongListAdapter
import com.example.androidpractice.data.Discography
import com.example.androidpractice.data.MusicGroups
import com.example.androidpractice.databinding.ActivitySongListBinding
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class SongListActivity : AppCompatActivity()
{
    /**/
    companion object
    {
        const val PE_ALBUM_JSON = "PE_ALBUM_JSON"
        const val PE_NAME_GROUP = "PE_NAME_GROUP"
    }
    /**/
    lateinit var binding: ActivitySongListBinding
    lateinit var adapter: SongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySongListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }/*START of onCreate*/

        /**/
        val json = intent.getStringExtra(PE_ALBUM_JSON)!!
        val discography = Gson().fromJson(json, Discography::class.java)

        /**/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = discography.title
        supportActionBar?.subtitle = intent.getStringExtra(PE_NAME_GROUP)!!

        /**/
        binding.idTvYearReleased.text = discography.released
        Picasso.get().load(discography.imageDisk).into(binding.idIvImageDisk)

        /**/
        adapter = SongListAdapter(discography.songList) { }

        /**/
        binding.idRvSongList.adapter = adapter
        binding.idRvSongList.layoutManager = LinearLayoutManager(this)

    }/*END of onCreate*/

    /**/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    /**/

}/*END of SongListActivity : AppCompatActivity()*/