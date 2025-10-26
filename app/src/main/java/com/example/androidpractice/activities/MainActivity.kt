package com.example.androidpractice.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Duration

class MainActivity : AppCompatActivity()
{
    /**/
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: ListGroupAdapter
    var listMusicGroups: List<MusicGroups> = emptyList()

    var listDiscography: List<Discography> = emptyList()

/**/
    var nameGroup = ""
    var placeYearFounded = ""
    var musicalGenre = ""
    var groupMembers = ""
    var history = ""
    var imageGroup: String? = ""

    /**/
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
                //intent.putExtra(DiscographyActivity.PE_NAME_GROUP,musicGroup.music_group)
                //intent.putExtra("SELECTED_GROUP",musicGroup.discography)
                // Usamos putParcelableArrayListExtra para pasar una lista de Parcelables
                //intent.putArrayListExtra("ALBUMS_KEY", ArrayList(discographyList))
                //intent.putExtra(InformationActivity.PE_DISCOGRAPHY_GROUP,musicGroup.discography)
                //
                startActivity(intent)
        },
            {position ->
                val musicGroup = listMusicGroups[position]

                nameGroup = musicGroup.group
                placeYearFounded = musicGroup.founded
                musicalGenre = musicGroup.genre
                groupMembers = musicGroup.members
                history = musicGroup.history
                imageGroup = musicGroup.imageGroup

                showAlertDialogInfo()
                //showAlertDialog()
                /*
                val intent = Intent(this, InformationActivity::class.java)
                intent.putExtra(InformationActivity.PE_NAME_GROUP,musicGroup.group)
                intent.putExtra(InformationActivity.PE_FOUNDED_GROUP,musicGroup.founded)
                intent.putExtra(InformationActivity.PE_GENRE_GROUP,musicGroup.genre)
                intent.putExtra(InformationActivity.PE_MEMBERS_GROUP,musicGroup.members)
                intent.putExtra(InformationActivity.PE_HISTORY_GROUP,musicGroup.history)
                intent.putExtra(InformationActivity.PE_IMAGE_GROUP,musicGroup.imageGroup)
                startActivity(intent)*/
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

    /**/
    fun showAlertDialogInfo()
    {
        val builder = AlertDialog.Builder(this)

        // Inflar el layout personalizado
        val customLayout = LayoutInflater.from(this).inflate(R.layout.alertdialog_custom_info, null)

        //
        val ivImgGroup = customLayout.findViewById<ImageView>(R.id.idIvImgGroup)
        val tvNameGroup = customLayout.findViewById<TextView>(R.id.idTvNameGroup)
        val tvPlaceYearFounded = customLayout.findViewById<TextView>(R.id.idTvPlaceYearFounded)
        val tvMusicalGenre = customLayout.findViewById<TextView>(R.id.idTvMusicalGenre)
        val tvGroupMembers = customLayout.findViewById<TextView>(R.id.idTvGroupMembers)
        val tvHistory = customLayout.findViewById<TextView>(R.id.idTvHistory)

        //
        Picasso.get().load(imageGroup).into(ivImgGroup)
        tvNameGroup.text = nameGroup
        tvPlaceYearFounded.text = placeYearFounded
        tvMusicalGenre.text = musicalGenre
        tvGroupMembers.text = groupMembers
        tvHistory.text = history

        builder.setView(customLayout)

        // 4. Agregar los botones estándar (setPositiveButton, setNegativeButton, etc.)
        builder.setNeutralButton(R.string.txt_thanks_information) { dialog, which -> }

        //
        val dialog = builder.create()
        dialog.show()
    }

    /**/
    fun showAlertDialog()
    {
        val builder = AlertDialog.Builder(this)
        // Inflar el layout personalizado
        val customLayout = LayoutInflater.from(this).inflate(R.layout.alertdialog_custom, null)
        //
        val btnDiscography = customLayout.findViewById<Button>(R.id.idBtnDiscography)
        val btnInformation = customLayout.findViewById<Button>(R.id.idBtnInformation)
        //
        builder.setView(customLayout)
        val dialog = builder.create()
        //
        btnDiscography.setOnClickListener {
            Toast.makeText(this,"btnDiscography", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnInformation.setOnClickListener {
            Toast.makeText(this,"btnInformation", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        //
        dialog.show()
    }

}/*END of MainActivity : AppCompatActivity()*/