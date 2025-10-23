package com.example.androidpractice.activities

import android.os.Bundle
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
    var originalVgList: List<MusicGroups> = emptyList()
    var filteredVgList: List<MusicGroups> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /*Start of onCreate*/

        //
        adapter = ListGroupAdapter(filteredVgList) { position ->
            val videoGame = filteredVgList[position]
            /*val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.PUT_EXTRA_GAME_ID, videoGame.id)
            startActivity(intent)*/
        }
        //
        binding.idRvListGroups.adapter = adapter
        binding.idRvListGroups.layoutManager = LinearLayoutManager(this)

        getGroupList()

    }/*End of onCreate*/


    /*We create the getGameList function*/
    fun getGroupList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = MusicGroupService.getInstance()
                originalVgList = service.getAllGroups()
                filteredVgList = originalVgList
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateItems(filteredVgList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}