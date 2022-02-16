package com.practice.retromoshi

import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyService {
    @GET("character/2")
    fun getCharacterById(): Call<Any>
}