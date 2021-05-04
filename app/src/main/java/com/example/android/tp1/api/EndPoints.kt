package com.example.android.tp1.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @POST("login")
    fun login(
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<LoginCheck>
}