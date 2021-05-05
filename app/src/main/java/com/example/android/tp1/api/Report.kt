package com.example.android.tp1.api

data class Report(
        val id: Int?,
        val title: String,
        val description: String,
        val latitude: String,
        val longitude: String,
        val image: String,
        val user_id: Int,
        val type_id: Int
)