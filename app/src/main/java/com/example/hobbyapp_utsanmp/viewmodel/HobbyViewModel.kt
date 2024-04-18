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
import com.example.hobbyapp_utsanmp.model.Hobby
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HobbyViewModel(application: Application) : AndroidViewModel(application) {
    val hobbyLD = MutableLiveData<ArrayList<Hobby>>()
    val hobbyLoadErrorLD = MutableLiveData<Boolean>()
    val LoadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun getHobby() {
        hobbyLoadErrorLD.value = false
        LoadingLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        val query = "select idhobby, hobby_img_url, title, a.username as 'writer', preview, content from hobby as h inner join account as a on a.idaccount=h.account_idaccount"
        val url = "http://10.0.2.2/API/UTS ANMP/get_data.php?table=hobby&query=$query"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Hobby>>() {}.type
                val result = Gson().fromJson<ArrayList<Hobby>>(it, sType)
                hobbyLD.value = result
//                if success, set loading progress live data to false
                LoadingLD.value = false
                Log.d("showvolley", result.toString())
            },
            {
//                if failed, show error message, set error live data to true and progress live data to false
                Log.d("showvolley", it.toString())
                hobbyLoadErrorLD.value = true
                LoadingLD.value = false
            }
        ).apply {
//            this.tag = TAG
            tag = TAG
            queue?.add(this)
        }
    }
}