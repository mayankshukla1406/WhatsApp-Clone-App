package com.example.whatsapp.presentation.HomePageLayout.Contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.domain.model.User
import com.example.whatsapp.domain.use_case.ContactsUseCase
import com.example.whatsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private var contactsUseCase: ContactsUseCase,
) : ViewModel() {

    private val _whatsAppContactsList = MutableStateFlow<List<User>>(emptyList())
    val whatsAppContactsList : StateFlow<List<User>> = _whatsAppContactsList

    private lateinit var iContactsViews : IContactsViews


    fun getAllWhatsAppContacts(deviceContacts: List<String>,interfaceListener : IContactsViews) = viewModelScope.launch {
        iContactsViews = interfaceListener
        contactsUseCase.getAllWhatsAppContacts(deviceContacts).collectLatest {
            when(it) {
                is Resource.Success -> {
                    iContactsViews.hideProgressBar()
                    _whatsAppContactsList.value = it.data
                }
                is Resource.Loading -> {
                    iContactsViews.showProgressBar()
                }
                is Resource.Error -> {
                    iContactsViews.showError(it.message?:"An Error Occurred")
                }
            }
        }
    }
}