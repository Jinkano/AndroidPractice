package com.example.androidpractice.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidpractice.R
import com.example.androidpractice.databinding.ActivityInformationBinding
import com.squareup.picasso.Picasso

class InformationActivity : AppCompatActivity()
{
    /*Constant for the Intent*/
    companion object {
        const val PE_NAME_GROUP = "PE_NAME_GROUP"
        const val PE_FOUNDED_GROUP = "PE_FOUNDED_GROUP"
        const val PE_GENRE_GROUP = "PE_GENRE_GROUP"
        const val PE_MEMBERS_GROUP = "PE_MEMBERS_GROUP"
        const val PE_HISTORY_GROUP = "PE_HISTORY_GROUP"
        const val PE_IMAGE_GROUP = "PE_IMAGE_GROUP"

        //val PE_DISCOGRAPHY_GROUP: List<String> = listOf("PE_DISCOGRAPHY_GROUP")
    }
    /**/
    lateinit var binding: ActivityInformationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }/*Start of onCreate*/

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadGameData()

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

    /**/
    fun loadGameData()
    {
        supportActionBar?.title = intent.getStringExtra(PE_NAME_GROUP)!!
        binding.idTvNameGroup.text = intent.getStringExtra(PE_NAME_GROUP)!!
        binding.idTvPlaceYearFounded.text = intent.getStringExtra(PE_FOUNDED_GROUP)!!
        binding.idTvMusicalGenre.text = intent.getStringExtra(PE_GENRE_GROUP)!!
        binding.idTvGroupMembers.text = intent.getStringExtra(PE_MEMBERS_GROUP)!!
        binding.idTvHistory.text = intent.getStringExtra(PE_HISTORY_GROUP)!!
        Picasso.get().load(intent.getStringExtra(PE_IMAGE_GROUP)).into(binding.idIvImgGroup)
    }
}/*END of class InformationActivity : AppCompatActivity()*/