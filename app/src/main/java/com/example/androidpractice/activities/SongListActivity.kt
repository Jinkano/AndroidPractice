package com.example.androidpractice.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidpractice.R
import com.example.androidpractice.activities.DiscographyActivity.Companion.PE_GROUP_JSON
import com.example.androidpractice.adapters.SongListAdapter
import com.example.androidpractice.data.MusicGroups
import com.example.androidpractice.databinding.ActivitySongListBinding
import com.google.gson.Gson

class SongListActivity : AppCompatActivity()
{
    /**/
    companion object
    {
        const val PE_ALBUM = "PE_ALBUM"
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
        val json = intent.getStringExtra(PE_ALBUM)!!
        val musicGroup = Gson().fromJson(json, MusicGroups::class.java)

        /**/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.title = songList.discography
        //supportActionBar?.subtitle = musicGroup.genre

        /**/
        adapter = SongListAdapter(musicGroup.discography[0].songList) { }

        /**/
        binding.idRvSongList.adapter = adapter
        binding.idRvSongList.layoutManager = LinearLayoutManager(this)

        /**/

        /**/

    }/*END of onCreate*/

    /**/


}/*END of SongListActivity : AppCompatActivity()*/