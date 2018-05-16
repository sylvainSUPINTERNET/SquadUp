package com.example.jolysylvain.squadup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;



public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(User user);
    }
    private List<User> usersList;
    private final OnItemClickListener listener;



    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, email, role, description;
        public Context contextItem;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            description = (TextView) view.findViewById(R.id.description);
            role = (TextView) view.findViewById(R.id.role);
            contextItem = view.getContext();

        }

        public void bind(final User user, final OnItemClickListener listener) {
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(user);
                }
            });
        }


    }


    public UserAdapter(List<User> usersList, OnItemClickListener listener) {
        this.usersList = usersList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.description.setText(user.getDescription());
        holder.role.setText(user.getRole());
        holder.bind(usersList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

}