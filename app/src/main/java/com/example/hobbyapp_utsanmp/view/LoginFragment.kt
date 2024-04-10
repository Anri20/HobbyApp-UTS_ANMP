package com.example.hobbyapp_utsanmp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.hobbyapp_utsanmp.databinding.FragmentLoginBinding

class LoginFragment : Fragment(){
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var bottomNavigationVisibilityListener: BottomNavigationVisibilityListener? = null

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


        binding.btnLogin.setOnClickListener {
            if (binding.txtUsername.text.toString() == "a" && binding.txtPassword.text.toString() == "a"){
                Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()

                val action = LoginFragmentDirections.actionHobbyList(binding.txtUsername.text.toString())
                Navigation.findNavController(it).navigate(action)
            }else{
                Toast.makeText(requireContext(), "Your Username or Password is incorrect, please try again", Toast.LENGTH_LONG).show()
            }
        }

        binding.txtRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionRegister()
            Navigation.findNavController(it).navigate(action)
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