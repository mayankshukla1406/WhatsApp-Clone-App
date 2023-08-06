package com.example.whatsapp.presentation.HomePageLayout.Contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.R
import com.example.whatsapp.databinding.FragmentContactsBinding
import com.example.whatsapp.presentation.HomePageLayout.Chat.IChatView
import com.example.whatsapp.presentation.HomePageLayout.Message.MessageFragment
import com.example.whatsapp.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ContactsFragment : Fragment(), IContactsViews, IChatView {

    private val contactsViewModel: ContactsViewModel by viewModels()
    private var binding: FragmentContactsBinding? = null
    private var deviceContacts: MutableList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        deviceContacts = ContactsManager(requireContext()).getContacts()

        deviceContacts.map {
            it.replace("+91", "").replace(" ", "")
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { binding ->
            if (deviceContacts.isNotEmpty()) {
                val newList = getContactsList()
                val contactsAdapter = ContactsAdapter(this)
                contactsViewModel.getAllWhatsAppContacts(newList, this)
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = contactsAdapter
                CoroutineScope(Dispatchers.IO).launch {
                    contactsViewModel.whatsAppContactsList.collectLatest {
                        withContext(Dispatchers.Main) {
                            if (it.isNotEmpty()) {
                                contactsAdapter.submitList(it)
                                withContext(Dispatchers.Main) {
                                    binding.subtitleText.text =
                                        "${contactsAdapter.currentList.size} Contacts"
                                }
                            } else {
                                Toast.makeText(context, "You have 0 contacts in your device", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(context, "You have 0 contacts in your device", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getContactsList(): List<String> {
        val list = ArrayList<String>()
        list.add("000000")
        list.add("1234567890")
        list.add("8765")
        list.add("12345")
        list.add("1234")
        list.add("1234567")
        list.add("9876")
        return list
    }

    override fun showError(error: String) {
        binding?.let { it.contactsProgressBar.visibility = View.GONE }
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun getUserId(): String {
        return ""
    }

    override fun hideProgressBar() {
        binding?.let { it.contactsProgressBar.visibility = View.GONE }
    }

    override fun showProgressBar() {
        binding?.let { it.contactsProgressBar.visibility = View.VISIBLE }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contact_fragment_menu, menu)
    }

    override fun openMessageFragment(chatId: String) {
        
        MessageFragment.newInstance(activity = activity as MainActivity,chatId)
    }
}