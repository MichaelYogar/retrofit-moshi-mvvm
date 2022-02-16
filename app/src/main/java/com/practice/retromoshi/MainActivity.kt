package com.practice.retromoshi

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.tv_hello)


        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val service: RickAndMortyService = retrofit.create(RickAndMortyService::class.java)
        service.getCharacterById(8).enqueue(object : Callback<GetCharacterByIdResponse> {
            override fun onResponse(
                call: Call<GetCharacterByIdResponse>,
                response: Response<GetCharacterByIdResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.e("MainActivity", "Error in GET request")
                } else {
                    val body = response.body()?.let { body ->
                        textView.text = body.name
                    }
                }
            }

            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                Log.e("MainActivity", t.message!!)
            }
        })
    }

}