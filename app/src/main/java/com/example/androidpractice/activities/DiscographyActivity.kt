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

        /**/
        // 1. Recuperar el objeto Parcelable del Intent
        // Usamos T::class.java para especificar el tipo en getParcelableExtra
        //val discographyList: List<Discography> = intent.getParcelableExtra<MusicGroup>("SELECTED_GROUP")

        //if (musicGroup != null) {
            // 2. Obtener la discografía del objeto
            //val discographyList: List<Album> = musicGroup.discography

            // 3. Usar la lista de discografía para poblar un RecyclerView
            //    que muestre los títulos, años, etc., de los álbumes.

            // Ejemplo de uso: Mostrar el nombre del grupo y la cantidad de álbumes
            // log.d("SecondActivity", "Grupo: ${musicGroup.music_group}")
            // log.d("SecondActivity", "Número de álbumes: ${discographyList.size}")

            // Aquí configuras tu Adapter para el RecyclerView de la discografía
            // val recyclerView = findViewById<RecyclerView>(R.id.discography_recyclerview)
            // val adapter = DiscographyAdapter(discographyList)
            // recyclerView.adapter = adapter

        //} else {
            // Manejar el caso en que el objeto no se haya pasado correctamente
        //}


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