package com.example.whatsapp.presentation.HomePageLayout.Message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.domain.model.ModelMessage
import com.example.whatsapp.domain.use_case.AuthenticationUseCase
import com.example.whatsapp.domain.use_case.ChatUseCase
import com.example.whatsapp.util.Resource
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
    private val authUseCase : AuthenticationUseCase
) : ViewModel() {


    private var _messages = MutableStateFlow<List<ModelMessage>>(emptyList())
    val messages : StateFlow<List<ModelMessage>> = _messages

    lateinit var iMessagesView: IMessagesView
    fun getAllChatMessages(chatId : String,listener : IMessagesView) = viewModelScope.launch {
        iMessagesView = listener
        chatUseCase.getAllMessagesOfChat(chatId).collectLatest {
            when(it) {
                is Resource.Success -> {
                    iMessagesView.hideProgressBar()
                    _messages.value = it.data
                }
                is Resource.Loading -> {
                    iMessagesView.showProgressBar()
                }
                is Resource.Error -> {
                    iMessagesView.showError(it.message?:"An Error Occurred")
                }
            }
        }
    }
    fun getUserId() = authUseCase.getUserId()
    fun sendMessage(chatId: String, messageModel: ModelMessage) = viewModelScope.launch {
        chatUseCase.sendMessage(chatId,messageModel).collectLatest {

        }
    }
}