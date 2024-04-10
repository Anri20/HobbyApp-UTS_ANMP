package com.example.hobbyapp_utsanmp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hobbyapp_utsanmp.R

class HobbyListFragment : Fragment() {

    private var bottomNavigationVisibilityListener: BottomNavigationVisibilityListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showBottomNavigation()
        return inflater.inflate(R.layout.fragment_hobby_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationVisibilityListener) {
            bottomNavigationVisibilityListener = context
        } else {
            throw RuntimeException("$context must implement BottomNavigationVisibilityListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationVisibilityListener = null
    }

    private fun changeBottomNavigationVisibility(isVisible: Boolean) {
        bottomNavigationVisibilityListener?.setBottomNavigationVisibility(isVisible)
    }

    private fun showBottomNavigation() {
        changeBottomNavigationVisibility(true)
    }
}