package com.example.whatsapp.presentation.HomePageLayout.Contacts

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.R
import com.example.whatsapp.databinding.FragmentContactsBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ContactsFragment : Fragment(), IContactsViews {

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
                val newList = ArrayList<String>()
                newList.add("000000")
                newList.add("1234567890")
                newList.add("8765")
                newList.add("12345")
                newList.add("1234")
                newList.add("1234567")
                newList.add("9876")
                val contactsAdapter = ContactsAdapter()
                contactsViewModel.getAllWhatsAppContacts(newList, this)
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = contactsAdapter
                CoroutineScope(Dispatchers.IO).launch {
                    contactsViewModel.whatsAppContactsList.collectLatest {
                        if (it.isNotEmpty()) {
                            contactsAdapter.submitList(it)
                            withContext(Dispatchers.Main) {
                                binding.subtitleText.text =
                                    "${contactsAdapter.currentList.size} Contacts"
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(context, "You have 0 contacts in your device", Toast.LENGTH_LONG)
                    .show()
            }
        }
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
}