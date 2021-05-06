package com.example.android.tp1.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("addReport")
    fun addReport(
            @Part("title") title: RequestBody,
            @Part("description") description: RequestBody,
            @Part("latitude") latitude: RequestBody,
            @Part("longitude") longitude: RequestBody,
            @Part image: MultipartBody.Part,
            @Part("user_id") user_id: Int?,
            @Part("type_id") type_id: Int?
    ): Call<OutputReport>

    @FormUrlEncoded
    @POST("deleteReport")
    fun deleteReport(@Field("id") first: Int?): Call<Report>

    @FormUrlEncoded
    @POST("editReport")
    fun editReport(
            @Field("id") first: Int?,
            @Field("title") second: String?,
            @Field("description") third: String?
    ): Call<String>

    @GET("reports")
    fun getReports(): Call<List<Report>>

    @GET("reports/users/{id}")
    fun getUserRep(@Path("id") id: Int): Call<List<Report>>

}