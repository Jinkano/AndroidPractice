package com.example.androidpractice.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidpractice.R
import com.example.androidpractice.databinding.ActivityDiscographyBinding

class DiscographyActivity : AppCompatActivity()
{
    /*Constant for the Intent*/
    companion object {
        const val PE_NAME_GROUP = "PE_NAME_GROUP"
    }

    /**/
    lateinit var binding: ActivityDiscographyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDiscographyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_discography)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }/*Start of onCreate*/

        /**/
        val nameGroup = intent.getStringExtra(PE_NAME_GROUP)!!
        /**/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = nameGroup
        supportActionBar?.subtitle = nameGroup

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