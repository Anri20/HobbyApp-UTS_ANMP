package com.example.hobbyapp_utsanmp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hobbyapp_utsanmp.R
import com.example.hobbyapp_utsanmp.databinding.FragmentHobbyListBinding
import com.example.hobbyapp_utsanmp.databinding.FragmentLoginBinding
import com.example.hobbyapp_utsanmp.viewmodel.HobbyViewModel

class HobbyListFragment : Fragment() {
    private var _binding: FragmentHobbyListBinding? = null
    private val binding get() = _binding!!

    private var bottomNavigationVisibilityListener: BottomNavigationVisibilityListener? = null

    private lateinit var hobbyViewModel: HobbyViewModel

    private val hobbyListAdapter = HobbyListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showBottomNavigation()
        _binding = FragmentHobbyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hobbyViewModel = ViewModelProvider(this).get(HobbyViewModel::class.java)
        hobbyViewModel.getHobby()

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = hobbyListAdapter

        binding.refreshLayout.setOnRefreshListener {
            binding.progressLoad.visibility = View.VISIBLE
        }

        binding.fabAddHobby.setOnClickListener{
            val action = HobbyListFragmentDirections.actionAddHobby()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        hobbyViewModel.hobbyLD.observe(viewLifecycleOwner, Observer {
            hobbyListAdapter.updateHobbyList(it)
        })

        hobbyViewModel.LoadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.recView.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
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