package com.example.whatsapp.presentation.HomePageLayout.Message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.R
import com.example.whatsapp.databinding.FragmentMessageBinding
import com.example.whatsapp.presentation.HomePageLayout.Chat.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MessageFragment : Fragment(),IMessagesView {

    private var binding : FragmentMessageBinding? = null
    private val messageViewModel : MessageViewModel by viewModels()
    lateinit var messageAdapter: MessageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {binding->
            messageAdapter = MessageAdapter(this)
            binding.messageRecyclerView.adapter = messageAdapter
            binding.messageRecyclerView.layoutManager = LinearLayoutManager(context)
            messageViewModel.getAllChatMessages("",this)
            CoroutineScope(Dispatchers.IO).launch {
                messageViewModel.messages.collectLatest {
                    messageAdapter.submitList(it)
                }
            }
        }
    }

    override fun getUserId(): String {
        return messageViewModel.getUserId()
    }

    override fun showError(error: String) {
        binding!!.messageProgressBar.visibility = View.GONE
        Toast.makeText(context,error,Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding!!.messageProgressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        binding!!.messageProgressBar.visibility = View.VISIBLE
    }
}