package com.example.whatsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.domain.model.User
import com.example.whatsapp.domain.use_case.AuthenticationUseCase
import com.example.whatsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authUseCase: AuthenticationUseCase,
) : ViewModel() {

    private lateinit var iViewsHandling : IViewsHandling

    fun signInWithPhoneNumber(phoneNumber: String, activity: MainActivity) {
        iViewsHandling = activity
        viewModelScope.launch {
            authUseCase.phoneNumberSignIn(phoneNumber, activity).collect {
                when (it) {
                    is Resource.Loading -> {
                        iViewsHandling.showProgressBar()
                    }

                    is Resource.Error -> {
                        iViewsHandling.showError(it.message?:"Some Error Occurred")
                    }

                    is Resource.Success -> {
                        iViewsHandling.hideProgressBar()
                        iViewsHandling.changeViewsVisibility()
                        iViewsHandling.dismissOtpBottomSheetDialogFragment()
                    }
                }
            }
        }
    }

    fun createUserProfile(userName : String, userNumber : String) {
        val modelUser : User = User(userName,userNumber,"","")
        viewModelScope.launch {
            authUseCase.createProfile(user = modelUser, authUseCase.getUserId()).collectLatest {
                when(it) {
                    is Resource.Loading -> {
                        iViewsHandling.showProgressBar()
                    }

                    is Resource.Error -> {
                        iViewsHandling.showError(it.message?:"Some Error Occurred")
                    }

                    is Resource.Success -> {
                        iViewsHandling.hideProgressBar()
                        iViewsHandling.showHomePage()
                    }
                }
            }
        }
    }

}