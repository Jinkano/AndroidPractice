package com.example.androidpractice.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MusicGroupService
{
    @GET("65e7-87a3-40b7-9e0a")//https://dummyjson.com/c/65e7-87a3-40b7-9e0a
    suspend fun getAllGroups() : List<MusicGroups>

    @GET("musicGroup")
    //suspend fun getGroupById(@Query("id")id: Int): MusicGroups
    suspend fun getGroupById(@Query("music_group")group: String): MusicGroups

    companion object
    {
        fun getInstance(): MusicGroupService
        {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/c/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MusicGroupService::class.java)
        }
    }
}