package com.example.whatsapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.whatsapp.R
import com.example.whatsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(checkAuthenticationStatus()) {

        } else {
            binding.userAuthenticationLayout.visibility = View.VISIBLE
            binding.appLogo.visibility = View.VISIBLE
            binding.userNumberLayout.visibility = View.VISIBLE
            binding.textInputLayout1.visibility = View.VISIBLE
            binding.etNumber.visibility = View.VISIBLE
            binding.btProceed.visibility = View.VISIBLE
        }
    }

    private fun checkAuthenticationStatus(): Boolean {
        return false
    }

    fun showBottomSheet(verificationId : String) {

    }
}













































 /*   if() {

    } else {
        binding.appLogo.visibility = View.VISIBLE
        binding.userAuthenticationLayout.visibility = View.VISIBLE
        binding.userNumberLayout.visibility = View.VISIBLE
        binding.etNumber.visibility = View.VISIBLE
    }

    binding.btProceed.setOnClickListener {
        if(binding.etNumber.isVisible) {
            var number = binding.etNumber.text.toString()
            authViewModel.phoneNumberSignIn("91 $number",this)
        }
    }
}

fun showBottomSheet(verificationId : String) {

}*/