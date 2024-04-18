package com.example.hobbyapp_utsanmp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
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

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val profileLD = MutableLiveData<ArrayList<Account>>()
    val profileLoadErrorLD = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun getProfile(id: String) {
        profileLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val query = "select * from account where idaccount = $id"
        val url = "http://10.0.2.2/API/UTS ANMP/get_data.php?table=account&query=$query"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Account>>() {}.type
                val result = Gson().fromJson<ArrayList<Account>>(it, sType)
                profileLD.value = result
//                if success, set loading progress live data to false
                Log.d("showvolley", result.toString())
            },
            {
//                if failed, show error message, set error live data to true and progress live data to false
                Log.d("showvolley", it.toString())
                profileLoadErrorLD.value = true
            }
        ).apply {
//            this.tag = TAG
            tag = TAG
            queue?.add(this)
        }
    }

    fun updateProfile(
        id: String,
        nama_depan: String,
        nama_belakang: String,
        username: String,
        password: String,
        imgUrl: String
    ) {
        val queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/API/UTS%20ANMP/update_data_account.php"

        val updatedData = Account(id.toInt(), nama_depan, nama_belakang, username, password, imgUrl)

        val jsonBody = Gson().toJson(updatedData)

        val request = object : StringRequest(Method.PUT, url,
            Response.Listener { response ->
                Log.d("showvolley", response)
            },
            Response.ErrorListener { error ->
                Log.e("showvolley", error.toString())
            }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return jsonBody.toByteArray(Charset.defaultCharset())
            }
        }

        queue.add(request)
    }
}