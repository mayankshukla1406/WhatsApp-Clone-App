package com.example.whatsapp.presentation.HomePageLayout.Message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsapp.databinding.DefaultLayoutBinding
import com.example.whatsapp.databinding.MessageImageReceiverBinding
import com.example.whatsapp.databinding.MessageImageSenderBinding
import com.example.whatsapp.databinding.MessageTextReceiverBinding
import com.example.whatsapp.databinding.MessageTextSenderBinding
import com.example.whatsapp.domain.model.ModelMessage
import com.example.whatsapp.presentation.IViewsHandling

class MessageAdapter(var listener : IViewsHandling) : ListAdapter<ModelMessage,RecyclerView.ViewHolder>(MessageDiffUtil()) {

    var text_message_sender = 0
    var text_message_receiver = 1
    var image_message_sender = 2
    var image_message_receiver = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            text_message_sender -> TextMessageViewHolderSender(MessageTextSenderBinding.inflate(inflater,parent,false))
            text_message_receiver -> TextMessageViewHolderReceiver(MessageTextReceiverBinding.inflate(inflater,parent,false))
            image_message_sender -> ImageMessageViewHolderSender(MessageImageSenderBinding.inflate(inflater,parent,false))
            image_message_receiver -> ImageMessageViewHolderReceiver(MessageImageReceiverBinding.inflate(inflater,parent,false))
            else -> {
                DefaultViewHolder(DefaultLayoutBinding.inflate(inflater,parent,false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var messageItem = getItem(position)
        var userId = listener.getUserId()
        if(messageItem.messageType == "text" && messageItem.messageSender == userId) {
            (holder as TextMessageViewHolderSender).bindView(messageItem)
        } else if(messageItem.messageType == "text" && messageItem.messageReceiver == userId) {
            (holder as TextMessageViewHolderReceiver).bindView(messageItem)
        } else if(messageItem.messageType == "image" && messageItem.messageSender == userId) {
            (holder as ImageMessageViewHolderSender).bindView(messageItem)
        } else if(messageItem.messageType == "image" && messageItem.messageReceiver == userId) {
            (holder as ImageMessageViewHolderReceiver).bindView(messageItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        var messageItem = getItem(position)
        var userId = listener.getUserId()
        return if(messageItem.messageType == "text" && messageItem.messageSender == userId) {
            text_message_sender
        } else if(messageItem.messageType == "text" && messageItem.messageReceiver == userId) {
            text_message_receiver
        } else if(messageItem.messageType == "image" && messageItem.messageSender == userId) {
            image_message_sender
        } else if(messageItem.messageType == "image" && messageItem.messageReceiver == userId) {
            image_message_receiver
        } else {
            -1
        }
    }

    class ImageMessageViewHolderSender(var binding: MessageImageSenderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(messageModel : ModelMessage) {
            Glide.with(itemView.context).load(messageModel.messageData).into(binding.imageSender)
        }
    }
    class ImageMessageViewHolderReceiver(var binding: MessageImageReceiverBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(messageModel : ModelMessage) {
            Glide.with(itemView.context).load(messageModel.messageData).into(binding.imageReceiver)
        }
    }
    class TextMessageViewHolderSender(var binding: MessageTextSenderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(messageModel : ModelMessage) {
            binding.textSender.text = messageModel.messageData
        }
    }
    class TextMessageViewHolderReceiver(var binding: MessageTextReceiverBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(messageModel : ModelMessage) {
            binding.textReceiver.text = messageModel.messageData
        }
    }
    class DefaultViewHolder(binding: DefaultLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class MessageDiffUtil : DiffUtil.ItemCallback<ModelMessage>() {
        override fun areItemsTheSame(oldItem: ModelMessage, newItem: ModelMessage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ModelMessage, newItem: ModelMessage): Boolean {
            return oldItem.messageData == newItem.messageData
        }

    }
}