package com.example.hobbyapp_utsanmp.view

import com.example.hobbyapp_utsanmp.model.Account

interface BottomNavigationVisibilityListener {
    fun setBottomNavigationVisibility(isVisible: Boolean)
}

interface OnDataPassListener {
    fun onDataPass(data: ArrayList<Account>)
}
