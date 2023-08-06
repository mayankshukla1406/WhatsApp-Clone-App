package com.example.whatsapp.presentation.HomePageLayout.Chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.R
import com.example.whatsapp.databinding.FragmentChatBinding
import com.example.whatsapp.presentation.HomePageLayout.Contacts.ContactsAdapter
import com.example.whatsapp.presentation.HomePageLayout.Contacts.ContactsFragment
import com.example.whatsapp.presentation.HomePageLayout.Message.MessageFragment
import com.example.whatsapp.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ChatFragment : Fragment(), IChatView {

    private var binding: FragmentChatBinding? = null
    private val chatViewModel: ChatViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { binding ->
            val chatAdapter = ChatAdapter(this)
            binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.chatRecyclerView.adapter = chatAdapter
            chatViewModel.fetchAllChats(this)
            CoroutineScope(Dispatchers.IO).launch {
                chatViewModel.chatList.collectLatest {
                    chatAdapter.submitList(it)
                }
            }
        }
    }

    override fun showProgressBar() {
        super.showProgressBar()
    }

    override fun hideProgressBar() {
        super.hideProgressBar()
    }

    override fun showError(error: String) {
        super.showError(error)
    }

    override fun getUserId(): String {
        return ""
    }

    override fun openMessageFragment() {
        super.openMessageFragment()
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, MessageFragment(), "message_fragment")
            .addToBackStack("message_fragment").commit()
    }
}