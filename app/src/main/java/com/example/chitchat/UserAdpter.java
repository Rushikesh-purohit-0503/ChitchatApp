package com.example.chitchat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdpter extends RecyclerView.Adapter<UserAdpter.viewholder> {
    Context mainActivity;
    ArrayList<Users> usersArrayList;
    public UserAdpter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity=mainActivity;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public UserAdpter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdpter.viewholder holder, int position) {
        Users users=usersArrayList.get(position);
        holder.username.setText(users.userName);
        holder.userStatus.setText(users.status);
        Picasso.get().load(users.profilePic).into(holder.userimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mainActivity, chat_windo.class);
                intent.putExtra("nameeee",users.getUserName());
                intent.putExtra("recieverImg",users.getProfilePic());
                intent.putExtra("uid",users.getUserId());

                mainActivity.startActivity(intent);


            }
        });




    }

    @Override
    public int getItemCount() {

        return usersArrayList.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        CircleImageView userimg;
        TextView username;
        TextView userStatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userimg=itemView.findViewById(R.id.userimg);
            username=itemView.findViewById(R.id.username);
            userStatus=itemView.findViewById(R.id.userStatus);
        }
    }
}
