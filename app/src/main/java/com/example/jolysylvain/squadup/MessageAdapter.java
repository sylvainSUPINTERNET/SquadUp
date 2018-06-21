package com.example.jolysylvain.squadup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Message message);
    }
    private List<Message> messagesList;



    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView sender, receveur, message, send_at;
        public Context contextItem;

        public MyViewHolder(View view) {
            super(view);
            sender = (TextView) view.findViewById(R.id.sender);
            receveur = (TextView) view.findViewById(R.id.receveur);
            message = (TextView) view.findViewById(R.id.message);
            send_at = (TextView) view.findViewById(R.id.sendAt);
            contextItem = view.getContext();

        }

        public void bind(final Message message, final OnItemClickListener listener) {
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(message);
                }
            });
        }


    }


    public MessageAdapter(List<Message> messageList) {
        this.messagesList = messageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Message message = messagesList.get(position);
        holder.sender.setText(message.getSender());
        holder.message.setText(message.getMessage());
        holder.receveur.setText(message.getReceveur());
        holder.send_at.setText(message.getSend_at().toString());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

}