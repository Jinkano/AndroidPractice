package com.example.androidpractice.data

import com.google.gson.annotations.SerializedName

data class MusicGroups(
    @SerializedName("id_group") val idGroup: String,
    @SerializedName("music_group") val group: String,
    @SerializedName("place_year_founded") val founded: String,
    @SerializedName("musical_genre") val genre: String,
    @SerializedName("group_members") val members: String,
    @SerializedName("history") val history: String,
    @SerializedName("discography") val discography: List<Discography>,
    @SerializedName("image_group_url") val imageGroup: String?
)

data class Discography(
    @SerializedName("album_title") val title: String,
    @SerializedName("year_released") val released: String,
    @SerializedName("song_list") val songList: List<String>,
    @SerializedName("image_disk_url") val imageDisk: String?
)
