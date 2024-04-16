package com.example.hobbyapp_utsanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp_utsanmp.model.Account
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    val accountLDCompare = MutableLiveData<ArrayList<Account>>()
    val accountLD = MutableLiveData<ArrayList<Account>>()
    val accountLoadErrorLD = MutableLiveData<Boolean>()
    val LoadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun compare(username: String) {
        accountLoadErrorLD.value = false
        LoadingLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        val query = "select * from account where username = '$username'"
        val url = "http://10.0.2.2/API/UTS ANMP/get_data.php?table=account&query=$query"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Account>>() {}.type
                val result = Gson().fromJson<ArrayList<Account>>(it, sType)
                accountLDCompare.value = result
//                if success, set loading progress live data to false
                LoadingLD.value = false
                Log.d("showvolley", result.toString())
            },
            {
//                if failed, show error message, set error live data to true and progress live data to false
                Log.d("showvolley", it.toString())
                accountLoadErrorLD.value = true
                LoadingLD.value = false
            }
        ).apply {
//            this.tag = TAG
            tag = TAG
            queue?.add(this)
        }
    }

    fun register(
        nama_depan: String,
        nama_belakang: String,
        username: String,
        password: String,
        imgUrl: String?
    ) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/API/UTS%20ANMP/insert_data.php"

        // Create the data object
        val user = Account(nama_depan, nama_belakang, username, password, imgUrl)
        // Convert User object to JSON using Gson
        val gson = Gson()
        val userJson = gson.toJson(user)

        // Create a StringRequest
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Log.d("showvolley", response)
            },
            Response.ErrorListener { error ->
                Log.e("showvolley", error.toString())
            }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return userJson.toByteArray(Charset.defaultCharset())
            }
        }

        // Add the request to the RequestQueue
        queue.add(stringRequest)
    }
}