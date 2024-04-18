package com.example.hobbyapp_utsanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp_utsanmp.model.Account
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val accountLD = MutableLiveData<ArrayList<Account>>()
    val accountLoadErrorLD = MutableLiveData<Boolean>()
    val LoadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun login(username: String, password: String) {
        accountLoadErrorLD.value = false
        LoadingLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        val query = "select * from account where username = '$username' and password = '$password'"
        val url = "http://10.0.2.2/API/UTS ANMP/get_data.php?table=account&query=$query"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Account>>() {}.type
                val result = Gson().fromJson<ArrayList<Account>>(it, sType)
                accountLD.value = result
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

    override fun onCleared() {
        super.onCleared()

        queue?.cancelAll(TAG)
    }
}