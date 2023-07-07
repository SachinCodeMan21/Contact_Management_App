package com.example.sqlcontacttest.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlcontacttest.MainActivity;
import com.example.sqlcontacttest.R;
import com.example.sqlcontacttest.models.Contact;
import com.example.sqlcontacttest.models.DbContact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    ArrayList<DbContact> list;
    MainActivity mainActivity;

    public ContactAdapter(Context context, ArrayList<DbContact> list, MainActivity mainActivity) {
        this.context = context;
        this.list = list;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final DbContact item = list.get(position);
        holder.name.setText(item.getContact_name());
        holder.email.setText(item.getContact_email());
        holder.phone.setText(String.valueOf(item.getContact_phone()));
        holder.age.setText(String.valueOf(item.getContact_age()));
        holder.img.setImageResource(item.getContact_img());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.addAndEditContact(true,item,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,email,phone,age;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            age = itemView.findViewById(R.id.age);
            img = itemView.findViewById(R.id.imageView);


        }
    }



}
