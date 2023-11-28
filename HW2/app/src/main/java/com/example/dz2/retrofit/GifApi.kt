package com.example.dz2.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface GifApi {
     @GET("products/{id}")
     suspend fun getWifuByID(@Path("id") id:Int): Image
}