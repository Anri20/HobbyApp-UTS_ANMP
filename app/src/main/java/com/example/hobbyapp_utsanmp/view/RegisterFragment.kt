package com.example.hobbyapp_utsanmp.view

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hobbyapp_utsanmp.R
import com.example.hobbyapp_utsanmp.databinding.FragmentLoginBinding
import com.example.hobbyapp_utsanmp.databinding.FragmentRegisterBinding
import com.example.hobbyapp_utsanmp.viewmodel.LoginViewModel
import com.example.hobbyapp_utsanmp.viewmodel.RegisterViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            val username = binding.txtRegisterUsername.text.toString()
            registerViewModel.compare(username)
        }

        observeViewModel()

        binding.txtLogin.setOnClickListener {
            val action = RegisterFragmentDirections.actionLogin()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeViewModel() {
        registerViewModel.accountLDCompare.observe(viewLifecycleOwner, Observer {
            Log.d("showObserverVM", it.size.toString())
            if (it.size == 0) {
                Toast.makeText(requireContext(), "your username is available", Toast.LENGTH_SHORT)
                    .show()

                val countDownTimer = object : CountDownTimer(2000, 1000) { // 1 seconds countdown, with tick interval of 1 second
                    override fun onTick(millisUntilFinished: Long) {
                        // Code to execute on each tick (1 second interval)
                        val secondsRemaining = millisUntilFinished / 1000
                        // Update UI or perform any action based on the remaining time
                    }

                    override fun onFinish() {
                        // Code to execute when the countdown finishes
                        val nama_depan = binding.txtRegisterNamaDepan.text.toString()
                        val nama_belakang= binding.txtRegisterNamaBelakang.text.toString()
                        val username = binding.txtRegisterUsername.text.toString()
                        val password = binding.txtRegisterPassword.text.toString()
                        val imgUrl = binding.txtRegisterImgUrl.text.toString()

                        Log.d("showData", "$nama_depan, $nama_belakang, $username, $password, $imgUrl")

                        registerViewModel.register(nama_depan, nama_belakang, username, password, imgUrl)
                        // Terminate the timer
                        cancel()

                        val action = RegisterFragmentDirections.actionLogin()
                        Navigation.findNavController(requireView()).navigate(action)

                        Toast.makeText(requireContext(), "New account created successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                // Start the timer
                countDownTimer.start()

            } else {
                Toast.makeText(
                    context,
                    "Your username is not available",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}