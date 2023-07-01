package com.example.whatsapp.presentation

import android.service.voice.VoiceInteractionSession.ActivityId
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.domain.use_case.AuthenticationUseCase
import com.example.whatsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authUseCase: AuthenticationUseCase,
) : ViewModel() {

    fun signInWithPhoneNumber(phoneNumber: String, activity: MainActivity) {
        viewModelScope.launch {
            authUseCase.phoneNumberSignIn(phoneNumber, activity).collect {
                when (it) {
                    is Resource.Loading -> {
                        Toast.makeText(activity.baseContext,"Loading",Toast.LENGTH_LONG).show()
                    }

                    is Resource.Error -> {
                        Toast.makeText(activity.baseContext,"ERROR",Toast.LENGTH_LONG).show()
                    }

                    is Resource.Success -> {
                        Toast.makeText(activity.baseContext,"SUCCESS",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}