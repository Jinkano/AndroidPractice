package com.example.androidpractice.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidpractice.R
import com.example.androidpractice.adapters.DiscographyAdapter
import com.example.androidpractice.data.MusicGroups
import com.example.androidpractice.databinding.ActivityDiscographyBinding
import com.google.gson.Gson

class DiscographyActivity : AppCompatActivity()
{
    /**/
    companion object {
        const val PE_GROUP_JSON = "PE_GROUP_JSON"
    }

    /**/
    lateinit var binding: ActivityDiscographyBinding

    lateinit var adapter: DiscographyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDiscographyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }/*Start of onCreate*/

        /**/
        val json = intent.getStringExtra(PE_GROUP_JSON)!!
        val musicGroup = Gson().fromJson(json, MusicGroups::class.java)

        /**/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = musicGroup.group
        supportActionBar?.subtitle = musicGroup.genre

        /**/
        adapter = DiscographyAdapter(musicGroup.discography) { position ->
            val intent = Intent(this, SongListActivity::class.java)
            val discography = musicGroup.discography[position]
            val json: String = Gson().toJson(discography)
            intent.putExtra(SongListActivity.PE_ALBUM_JSON,json)
            intent.putExtra(SongListActivity.PE_NAME_GROUP,musicGroup.group)
            startActivity(intent)
        }

        /**/
        binding.idRvDiscography.adapter = adapter
        binding.idRvDiscography.layoutManager = GridLayoutManager(this,2)

    }/*End of onCreate*/

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

}/*END of DiscographyActivity : AppCompatActivity()*/