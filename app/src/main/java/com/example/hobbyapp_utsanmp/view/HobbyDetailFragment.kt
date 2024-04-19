package com.example.hobbyapp_utsanmp.view

import android.content.Context
import android.content.res.TypedArray
import android.net.UrlQuerySanitizer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hobbyapp_utsanmp.R
import com.example.hobbyapp_utsanmp.databinding.FragmentHobbyDetailBinding
import com.example.hobbyapp_utsanmp.databinding.FragmentLoginBinding

class HobbyDetailFragment : Fragment() {
    private var _binding: FragmentHobbyDetailBinding? = null
    private val binding get() = _binding!!

    private var bottomNavigationVisibilityListener: BottomNavigationVisibilityListener? = null

    private lateinit var imgUrl: String
    private lateinit var title: String
    private lateinit var writer: String
    private lateinit var content: Array<String>

    private var pageNow: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideBottomNavigation()
        // Inflate the layout for this fragment
        _binding = FragmentHobbyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let {
            imgUrl = HobbyDetailFragmentArgs.fromBundle(requireArguments()).imgUrl
            title = HobbyDetailFragmentArgs.fromBundle(requireArguments()).title
            writer = HobbyDetailFragmentArgs.fromBundle(requireArguments()).writer
            content = HobbyDetailFragmentArgs.fromBundle(requireArguments()).content.split("; ")
                .toTypedArray()
        }

        Log.d("contentSize", content.size.toString())

        binding.txtDetailTitle.text = title
        binding.txtDetailWriter.text = "@$writer"

        binding.txtDetailContent.text = content[pageNow]

        binding.txtPage.text = "${pageNow + 1} / ${content.size}"

        binding.btnNext.setOnClickListener {
            if (pageNow < content.size-1){
                pageNow += 1
            }

            binding.txtDetailContent.text = content[pageNow]
            binding.txtPage.text = "${pageNow + 1} / ${content.size}"
        }

        binding.btnPrev.setOnClickListener {
            if (pageNow > 0){
                pageNow -= 1
            }

            binding.txtDetailContent.text = content[pageNow]
            binding.txtPage.text = "${pageNow + 1} / ${content.size}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun hideBottomNavigation() {
        changeBottomNavigationVisibility(false)
    }
}