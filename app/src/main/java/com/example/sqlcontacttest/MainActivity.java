package com.example.sqlcontacttest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sqlcontacttest.adapters.ContactAdapter;
import com.example.sqlcontacttest.models.Contact;
import com.example.sqlcontacttest.models.DbContact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    ArrayList<DbContact> contact_list = new ArrayList<>();
    ContactAdapter adapter;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initialization
        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.floatingActionButton2);
        recyclerView = findViewById(R.id.recyclerView);

        //Toolbar
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Sql Contacts");


//        contact_list.add(new DbContact(1,"Sachin","absy3529@gmail.com",1111,20,R.drawable.profile1));
//        contact_list.add(new DbContact(2,"Raju","raju@gmail.com",2222,20,R.drawable.profile2));
//        contact_list.add(new DbContact(3,"Adam","adam3529@gmail.com",3333,20,R.drawable.profile3));
//        contact_list.add(new DbContact(4,"Anna","anna3529@gmail.com",4444,20,R.drawable.profile4));
//        contact_list.add(new DbContact(5,"Gwen","gwen@gmail.com",5555,20,R.drawable.profile5));


        //RecyclerView
        adapter = new ContactAdapter(this,contact_list,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContact(false,null,-1);
            }

        });


    }

    public void addAndEditContact(final boolean isUpdated, final DbContact contact,final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //Getting and setting the view
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_update_dialog,null);
        alertDialog.setView(view);

        EditText name = view.findViewById(R.id.editTextTextPersonName);
        EditText email = view.findViewById(R.id.editTextTextPersonName2);
        EditText phone = view.findViewById(R.id.editTextTextPersonName3);
        EditText age = view.findViewById(R.id.editTextTextPersonName4);
        ImageView img = view.findViewById(R.id.imageView3);

        alertDialog.setTitle(!isUpdated ? " Add new contact " : "Update contact");

        if (isUpdated && contact != null){
            name.setText(contact.getContact_name());
            email.setText(contact.getContact_email());
            phone.setText(String.valueOf(contact.getContact_phone()));
            age.setText(String.valueOf(contact.getContact_age()));
            img.setImageResource(contact.getContact_img());
        }

        alertDialog.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isUpdated){
                    DeleteContact(contact,position);
                }
                else{
                    dialog.cancel();
                }
            }
        });

        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(MainActivity.this, "Please enter the name !", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(MainActivity.this, "Please enter the email !", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(MainActivity.this, "Please enter the phone !", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(age.getText().toString())){
                    Toast.makeText(MainActivity.this, "Please enter the age !", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (isUpdated && contact != null){

                        DbContact updatedContact = new DbContact(contact.getId(),name.getText().toString(),email.getText().toString(),Integer.parseInt(phone.getText().toString()),
                                Integer.parseInt(age.getText().toString()),R.drawable.profile2);
                         UpdateContact(updatedContact,position);
                    }
                    else {
                        Contact newContact = new Contact(name.getText().toString(),email.getText().toString(),Integer.parseInt(phone.getText().toString()),
                                Integer.parseInt(age.getText().toString()),R.drawable.profile4);
                         CreateContact(newContact);
                    }
                    dialog.dismiss();

                }


            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void CreateContact(Contact newContact) {
       long id = db.addContact(newContact);
       DbContact contact = db.getContact(id);

       if (contact != null){
           contact_list.add(contact);
           adapter.notifyDataSetChanged();
       }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void UpdateContact(DbContact updatedContact, int position) {
        DbContact contact = contact_list.get(position);
        contact.setId(updatedContact.getId());
        contact.setContact_name(updatedContact.getContact_name());
        contact.setContact_email(updatedContact.getContact_email());
        contact.setContact_phone(updatedContact.getContact_phone());
        contact.setContact_age(updatedContact.getContact_age());
        contact.setContact_img(updatedContact.getContact_img());

        db.updateContact(contact);
        contact_list.set(position,contact);
        adapter.notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void DeleteContact(DbContact contact, int position) {
        contact_list.remove(position);
        db.deleteContact(contact);
        adapter.notifyDataSetChanged();

    }

}