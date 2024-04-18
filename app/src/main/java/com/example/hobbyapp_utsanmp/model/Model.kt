package com.example.hobbyapp_utsanmp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Account(
    var idaccount: Int?,
    var nama_depan: String?,
    var nama_belakang: String?,
    var username: String?,
    var password: String?,
    @SerializedName("img_url")
    var imgUrl: String?
) : Serializable

data class Hobby(
    val idhobby: Int?,
    @SerializedName("hobby_img_url")
    val imgUrl: String?,
    val title: String?,
    val writer: String?,
    val preview: String?,
    val content: Array<String>?,
)