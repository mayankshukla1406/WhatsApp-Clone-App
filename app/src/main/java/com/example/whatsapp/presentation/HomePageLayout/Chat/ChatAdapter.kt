package com.example.whatsapp.presentation.HomePageLayout.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsapp.R
import com.example.whatsapp.domain.model.ModelChat
import com.example.whatsapp.util.OutlineProvider

class ChatAdapter : ListAdapter<ModelChat,ChatAdapter.ViewHolder>(ChatDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.chat_card,parent,false)
        return ChatAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelChat = getItem(position)
        holder.bindView(modelChat)
    }


    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        fun bindView(modelChat: ModelChat) {
            val chatName : TextView = view.findViewById(R.id.chatName)
            val chatImage : ImageView = view.findViewById(R.id.chatImage)
            val chatLastMessage : TextView = view.findViewById(R.id.chatLastMessage)
            val chatLastMessageTimeStamp : TextView = view.findViewById(R.id.chatLastMessageTimeStamp)
            chatImage.outlineProvider = OutlineProvider()
            chatImage.clipToOutline = true

            Glide.with(itemView.context).load(modelChat.chatImage).into(chatImage)
            chatName.text = modelChat.chatName
            chatLastMessage.text = modelChat.chatLastMessage
            chatLastMessageTimeStamp.text = modelChat.chatLastMessageTimestamp
        }
    }

    class ChatDiffUtil : DiffUtil.ItemCallback<ModelChat>() {
        override fun areItemsTheSame(oldItem: ModelChat, newItem: ModelChat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ModelChat, newItem: ModelChat): Boolean {
            return oldItem.chatId == newItem.chatId
        }
    }

}