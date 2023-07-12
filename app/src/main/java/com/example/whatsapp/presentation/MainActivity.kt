package com.example.whatsapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.whatsapp.R
import com.example.whatsapp.databinding.ActivityMainBinding
import com.example.whatsapp.presentation.HomePageLayout.HomePageFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),IViewsHandling {

    private lateinit var binding : ActivityMainBinding
    private val authenticationViewModel : AuthenticationViewModel by viewModels()

    var otpValue = MutableStateFlow<String>("")

    lateinit var phoneNumber : String
    lateinit var userName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(checkAuthenticationStatus()) {
            openHomePage()
        } else {
            binding.userAuthenticationLayout.visibility = View.VISIBLE
            binding.appLogo.visibility = View.VISIBLE
            binding.userNumberLayout.visibility = View.VISIBLE
            binding.textInputLayout1.visibility = View.VISIBLE
            binding.etNumber.visibility = View.VISIBLE
            binding.btProceed.visibility = View.VISIBLE
        }
        binding.btProceed.setOnClickListener {
            if (binding.etNumber.isVisible) {
                phoneNumber = binding.etNumber.text.toString()
                authenticationViewModel.signInWithPhoneNumber("+91 $phoneNumber",this)
            } else {
                userName = binding.etName.text.toString()
                authenticationViewModel.createUserProfile(userName, userNumber = phoneNumber)
            }
        }
    }

    override fun changeViewsVisibility() {
        binding.userNumberLayout.visibility = View.GONE
        binding.textInputLayout1.visibility = View.GONE
        binding.etNumber.visibility = View.GONE
        binding.userNameLayout.visibility = View.VISIBLE
        binding.textInputLayout2.visibility = View.VISIBLE
        binding.etName.visibility = View.VISIBLE
    }

    private fun checkAuthenticationStatus(): Boolean {
        return authenticationViewModel.isUserAuthenticated()
    }

    fun showBottomSheet() {
        val otpFragment = OTPFragment()
        supportFragmentManager.beginTransaction().add(otpFragment,"bottomSheetOtpFragment").commit()
    }

    override fun showHomePage() {
        openHomePage()
    }

    private fun openHomePage() {
        setAllMainActivityViewsGone()
        binding.fragmentContainer.visibility = View.VISIBLE
        val homePageFragment = HomePageFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,homePageFragment,"homePageFragment").commit()
    }

    private fun setAllMainActivityViewsGone() {
        binding.userAuthenticationLayout.visibility = View.GONE
        binding.appLogo.visibility = View.GONE
        binding.userNumberLayout.visibility = View.GONE
        binding.textInputLayout1.visibility = View.GONE
        binding.etNumber.visibility = View.GONE
        binding.btProceed.visibility = View.GONE
        binding.userNameLayout.visibility = View.GONE
        binding.textInputLayout2.visibility = View.GONE
        binding.etName.visibility = View.GONE
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showError(error : String) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this.applicationContext,error,Toast.LENGTH_LONG).show()
    }

    override fun dismissOtpBottomSheetDialogFragment() {
        val fragment = supportFragmentManager.findFragmentByTag("bottomSheetOtpFragment")
        if(fragment!=null) {
            (fragment as BottomSheetDialogFragment).dismiss()
        }
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