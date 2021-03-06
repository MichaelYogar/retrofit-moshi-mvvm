package com.practice.retromoshi

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.tv_name)
        val cardImage = findViewById<ImageView>(R.id.iv_card_image)


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
                        Picasso.get().load(body.image).into(cardImage)
                    }
                }
            }

            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                Log.e("MainActivity", t.message!!)
            }
        })
    }

}