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
import com.example.whatsapp.domain.model.ModelMessage
import com.example.whatsapp.presentation.HomePageLayout.Chat.ChatViewModel
import com.example.whatsapp.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MessageFragment : Fragment(),IMessagesView {

    private var binding : FragmentMessageBinding? = null
    private val messageViewModel : MessageViewModel by viewModels()
    lateinit var messageAdapter: MessageAdapter
    lateinit var chatId : String

    private val backgroundThreadScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            chatId = it.getString("chatId").toString()
        }
    }
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
            messageViewModel.getAllChatMessages(chatId,this)
            messageViewModel.messages.onEach {
                messageAdapter.submitList(it)
            }.launchIn(backgroundThreadScope)

            binding.btSendMessage.setOnClickListener {
                var message = binding.etMessageBox.text.toString()
                if(message.isNotBlank()) {
                    var messageModel = ModelMessage(
                        messageData = message,
                        messageType = "text",
                        messageSender = getUserId(),
                        messageReceiver = ""
                    )
                    messageViewModel.sendMessage(chatId,messageModel)
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

    companion object {
        fun newInstance(activity : MainActivity,chatId : String) {
            var fragment = MessageFragment()
            var bundle = Bundle()
            bundle.putString("chatId",chatId)
            fragment.arguments = bundle
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, "message_fragment")
                .addToBackStack("message_fragment").commit()
        }
    }
}