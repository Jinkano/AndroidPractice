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
    //var originalVgList: List<MusicGroups> = emptyList()
    //var filteredVgList: List<MusicGroups> = emptyList()
    var listMusicGroups: List<MusicGroups> = emptyList()

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
        adapter = ListGroupAdapter(listMusicGroups,
            {position ->
                val intent = Intent(this, DiscographyActivity::class.java)
                startActivity(intent)
                Toast.makeText(this,"fg", Toast.LENGTH_SHORT).show()
        },
            {position ->
                val intent = Intent(this, InformationActivity::class.java)
                startActivity(intent)
            }
        )

        /**/
        binding.idRvListGroups.adapter = adapter
        binding.idRvListGroups.layoutManager = LinearLayoutManager(this)

        getGroupList()

        /**/

    }/*End of onCreate*/


    /*We create the getGameList function*/
    fun getGroupList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = MusicGroupService.getInstance()
                listMusicGroups = service.getAllGroups()
                //filteredVgList = originalVgList
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(listMusicGroups)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}