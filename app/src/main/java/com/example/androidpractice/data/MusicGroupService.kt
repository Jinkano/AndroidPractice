package com.example.androidpractice.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MusicGroupService
{
    @GET("5186-9b70-4ebc-8a2a")//https://dummyjson.com/c/5186-9b70-4ebc-8a2a
    suspend fun getAllGroups() : List<MusicGroups>

    @GET("5186-9b70-4ebc-8a2a")//https://dummyjson.com/c/5186-9b70-4ebc-8a2a
    suspend fun getAllDiscography() : List<Discography>

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