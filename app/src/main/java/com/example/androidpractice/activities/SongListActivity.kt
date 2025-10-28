package com.example.androidpractice.activities

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import java.io.IOException

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

    /*--------------------------------------------------*/
    /*--------------------------------------------------*/
    private val PERMISSION_CODE = 101 // Código de solicitud único
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    /*--------------------------------------------------*/
    /*--------------------------------------------------*/

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
        adapter = SongListAdapter(discography.songList,
            { position ->
                val nameSong = discography.songList[position]
                handlePlayPause(nameSong)
                Toast.makeText(this, ""+nameSong, Toast.LENGTH_SHORT).show()
            }
        )

        /**/
        binding.idRvSongList.adapter = adapter
        binding.idRvSongList.layoutManager = LinearLayoutManager(this)

        /**/
        // Llama a la función para verificar y solicitar el permiso
        checkPermission()

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

    private fun checkPermission() {/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 o superior
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
            } else {
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Android 12 o inferior
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
            }
        }*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            // El permiso no ha sido otorgado, solicitamos permiso al usuario
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_CODE
            )
        } else {
            // Permiso ya otorgado, podemos empezar a buscar canciones
            Toast.makeText(this, "Permiso de almacenamiento concedido.", Toast.LENGTH_SHORT).show()
            // Aquí iría la función para buscar la canción
        }
    }


    // Manejar la respuesta del usuario a la solicitud de permiso
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "¡Permiso concedido! Ahora puedes buscar canciones.", Toast.LENGTH_LONG).show()
                // Aquí iría la función para buscar la canción
            } else {
                Toast.makeText(this, "Permiso denegado. No podemos buscar canciones.", Toast.LENGTH_LONG).show()
            }
        }
    }

    /*
     * Busca el archivo de audio en el dispositivo por el nombre de la canción.
     * @param songName Nombre de la canción a buscar (ej: "My Song.mp3").
     * @return La ruta completa del archivo de audio, o null si no se encuentra.
     */
    private fun findSongPath(songName: String): String? {
        val contentResolver = contentResolver

        // Proyección: qué columnas queremos obtener (solo necesitamos la ruta de datos)
        val projection = arrayOf(MediaStore.Audio.Media.DATA)

        // Selección: la condición de búsqueda. Buscamos en el TÍTULO del archivo
        val selection = MediaStore.Audio.Media.TITLE + " LIKE ?"

        // Argumentos de Selección: el valor que debe tener la columna TÍTULO.
        // Usamos el símbolo '%' para buscar coincidencias parciales.
        val selectionArgs = arrayOf("%$songName%")

        // URI: La ubicación estándar para los archivos de audio
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        var cursor: Cursor? = null
        var path: String? = null

        try {
            // Ejecutar la consulta en la base de datos de medios
            cursor = contentResolver.query(uri, projection, selection, selectionArgs, null)

            // Si se encuentra al menos un resultado, obtener la ruta del primero
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                path = cursor.getString(columnIndex)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al acceder al almacenamiento.", Toast.LENGTH_SHORT).show()
        } finally {
            cursor?.close() // Es crucial cerrar el cursor para liberar recursos
        }

        return path
    }

    // CONTINUACIÓN DE MainActivity.kt
    private fun handlePlayPause(nameSong: String) {
        //val inputName = etSongName.text.toString().trim()

        // 1. Pausar si ya se está reproduciendo
        if (isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
            //btnPlayPause.text = "Reproducir"
            Toast.makeText(this, "Pausado: $nameSong", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Si no se está reproduciendo, buscar la canción
        val songPath = findSongPath(nameSong)

        if (songPath != null) {
            // La canción fue encontrada

            // Liberar el viejo MediaPlayer si existe
            mediaPlayer?.release()
            mediaPlayer = null

            // 3. Inicializar el nuevo MediaPlayer con la ruta del archivo
            mediaPlayer = MediaPlayer()
            try {
                mediaPlayer?.setDataSource(songPath) // Establecer la fuente (la ruta del archivo)
                mediaPlayer?.prepare()              // Preparar para la reproducción (puede tardar)
                mediaPlayer?.start()                // ¡Comenzar a reproducir!

                isPlaying = true
                //btnPlayPause.text = "Pausar"
                Toast.makeText(this, "Reproduciendo: $nameSong", Toast.LENGTH_LONG).show()

                // 4. Configurar Listener para cuando la canción termine
                mediaPlayer?.setOnCompletionListener {
                    isPlaying = false
                    //btnPlayPause.text = "Reproducir"
                    mediaPlayer?.release()
                    mediaPlayer = null
                }

            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error de I/O al cargar el archivo.", Toast.LENGTH_LONG).show()
            }
        } else {
            // La canción NO fue encontrada
            Toast.makeText(this, "Canción '$nameSong' no encontrada en el dispositivo.", Toast.LENGTH_LONG).show()
        }
    }

    // 5. Liberar el recurso MediaPlayer cuando la Activity se destruye
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}/*END of SongListActivity : AppCompatActivity()*/