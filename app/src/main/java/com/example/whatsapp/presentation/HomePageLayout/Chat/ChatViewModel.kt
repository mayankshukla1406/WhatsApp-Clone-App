package com.example.whatsapp.presentation.HomePageLayout.Chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.domain.model.ModelChat
import com.example.whatsapp.domain.use_case.AuthenticationUseCase
import com.example.whatsapp.domain.use_case.ChatUseCase
import com.example.whatsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase : ChatUseCase,
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    private lateinit var iChatView : IChatView
    private var _chatList = MutableStateFlow<List<ModelChat>>(emptyList())
    val chatList : StateFlow<List<ModelChat>> = _chatList

    fun fetchAllChats(listener : IChatView) = viewModelScope.launch{
        iChatView = listener
        chatUseCase.getAllChats(authenticationUseCase.getUserId()).collectLatest {
            when(it) {
                is Resource.Success -> {
                    iChatView.hideProgressBar()
                    _chatList.value = it.data
                }
                is Resource.Loading -> {
                    iChatView.showProgressBar()
                }
                is Resource.Error -> {
                    iChatView.showError(it.message?:"An Error Occurred")
                }
            }
        }

    }

}