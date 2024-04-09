package com.example.hobbyapp_utsanmp.model

data class Account(
    val id: Int?,
    val username: String?,
    var password: String?,
)

data class Hobby(
    val id: Int?,
    val title: String?
)