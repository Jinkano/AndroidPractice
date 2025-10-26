package com.example.androidpractice.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidpractice.R
import com.example.androidpractice.adapters.ListGroupAdapter
import com.example.androidpractice.data.Discography
import com.example.androidpractice.data.MusicGroupService
import com.example.androidpractice.data.MusicGroups
import com.example.androidpractice.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()
{
    /**/
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: ListGroupAdapter
    var listMusicGroups: List<MusicGroups> = emptyList()

    var listDiscography: List<Discography> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }/*Start of onCreate*/

        /**/
        supportActionBar?.title = getString(R.string.txt_title_main)

        getGroupList()

        /**/
        adapter = ListGroupAdapter(listMusicGroups,
            {position ->
                val musicGroup = listMusicGroups[position]
                val intent = Intent(this, DiscographyActivity::class.java)
                intent.putExtra(DiscographyActivity.PE_NAME_GROUP,musicGroup.group)
                // Usamos putParcelableArrayListExtra para pasar una lista de Parcelables
                //intent.putArrayListExtra("ALBUMS_KEY", ArrayList(discographyList))
                //intent.putExtra(InformationActivity.PE_DISCOGRAPHY_GROUP,musicGroup.discography)
                //
                startActivity(intent)
        },
            {position ->
                val musicGroup = listMusicGroups[position]
                val intent = Intent(this, InformationActivity::class.java)
                intent.putExtra(InformationActivity.PE_NAME_GROUP,musicGroup.group)
                intent.putExtra(InformationActivity.PE_FOUNDED_GROUP,musicGroup.founded)
                intent.putExtra(InformationActivity.PE_GENRE_GROUP,musicGroup.genre)
                intent.putExtra(InformationActivity.PE_MEMBERS_GROUP,musicGroup.members)
                intent.putExtra(InformationActivity.PE_HISTORY_GROUP,musicGroup.history)
                intent.putExtra(InformationActivity.PE_IMAGE_GROUP,musicGroup.imageGroup)
                startActivity(intent)
            }
        )

        /**/
        binding.idRvListGroups.adapter = adapter
        binding.idRvListGroups.layoutManager = LinearLayoutManager(this)



        /**/

    }/*End of onCreate*/


    /*We create the function*/
    fun getGroupList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = MusicGroupService.getInstance()
                listMusicGroups = service.getAllGroups()
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(listMusicGroups)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /*We create the function*/
    /*fun getListDiscograpy() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = MusicGroupService.getInstance()
                listDiscography = service.getAllDiscography()
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(listDiscography)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }*/

}/*END of MainActivity : AppCompatActivity()*/