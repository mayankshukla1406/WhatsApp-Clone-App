package com.example.whatsapp.presentation.HomePageLayout.Contacts

import android.content.Context
import android.provider.ContactsContract

class ContactsManager(private val context: Context) {
    private val contacts = arrayListOf<String>()

    fun getContacts() : ArrayList<String> {
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null,
        )
        cursor?.let {
            while (it.moveToNext()) {
                val value1 = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                var number = ""
                if(value1>-1) {
                    number = it.getString(value1)
                    contacts.add(number)
                }
            }
        }
        return contacts
    }
}