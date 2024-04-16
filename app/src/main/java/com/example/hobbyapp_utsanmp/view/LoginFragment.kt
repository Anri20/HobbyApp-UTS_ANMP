package com.example.hobbyapp_utsanmp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hobbyapp_utsanmp.databinding.FragmentLoginBinding
import com.example.hobbyapp_utsanmp.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var bottomNavigationVisibilityListener: BottomNavigationVisibilityListener? = null

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        hideBottomNavigation()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            loginViewModel.login(username, password)
        }

        observeViewModel()

        binding.txtRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionRegister()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeViewModel() {
        loginViewModel.accountLD.observe(viewLifecycleOwner, Observer {
            Log.d("showObserverVM", it.size.toString())
            if (it.size != 0) {
                Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()

                val action =
                    LoginFragmentDirections.actionHobbyList(binding.txtUsername.text.toString())
                Navigation.findNavController(requireView()).navigate(action)
            } else {
                Toast.makeText(
                    context,
                    "Your Username or Password is incorrect, please try again",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
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