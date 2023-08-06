package com.example.whatsapp.presentation.HomePageLayout.Chat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.databinding.FragmentChatBinding
import com.example.whatsapp.domain.model.ModelChat
import com.example.whatsapp.presentation.HomePageLayout.Message.MessageFragment
import com.example.whatsapp.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ChatFragment : Fragment(), IChatView {

    private var binding: FragmentChatBinding? = null
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter : ChatAdapter

    private val backgroundThreadScope  = CoroutineScope(Dispatchers.IO)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragmentManager : FragmentManager? = activity?.let { it.supportFragmentManager }
        fragmentManager?.let {
            val fragment = it.findFragmentByTag("message_fragment")
            fragment?.let {
                val transaction = fragmentManager.beginTransaction()
                transaction.remove(fragment)
                transaction.commit()
            }
        }
    }

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
            chatAdapter = ChatAdapter(this)
            binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.chatRecyclerView.adapter = chatAdapter
            chatViewModel.fetchAllChats(this)
            chatViewModel.chatList.onEach {
                withContext(Dispatchers.Main) {
                    setDataToRecyclerView(it)
                }
            }.launchIn(backgroundThreadScope)
        }
    }

    private fun setDataToRecyclerView(it: List<ModelChat>) {
        if(it.isEmpty()) {
            Toast.makeText(context,"No Messages Found",Toast.LENGTH_SHORT).show()
        } else {
            chatAdapter.submitList(it)
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

    override fun openMessageFragment(chatId: String) {
        MessageFragment.newInstance(activity = activity as MainActivity,chatId)
    }
}