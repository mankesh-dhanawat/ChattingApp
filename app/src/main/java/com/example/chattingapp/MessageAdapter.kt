package com.example.chattingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context:Context, val messageList:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val ITEM_RECIEVED = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType ==1){
            // inflate receive
            val view:View = LayoutInflater.from(context).inflate(R.layout.msg_recieve, parent, false)
            return MessageAdapter.recievedViewHolder(view)
        }
        else{
            // inflate sent
            val view: View = LayoutInflater.from(context).inflate(R.layout.msg_sent, parent, false)
            return MessageAdapter.sentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMsg = messageList[position]

        if(holder.javaClass == sentViewHolder::class.java){
            // sent view holder
            val viewHolder = holder as sentViewHolder
            holder.sentMessage.text = currentMsg.message
        }
        else{
            // reviewed view holder
            val viewHolder = holder as recievedViewHolder
            holder.recievedMessage.text = currentMsg.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid == currentMessage.senderId){
            return ITEM_SENT
        }
        else{
            return ITEM_RECIEVED
        }

    }
    class sentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sent_msg_txt)
    }
    class recievedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val recievedMessage = itemView.findViewById<TextView>(R.id.recieve_msg_txt)
    }

}