package com.example.tutionfee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutionfee.adapter.FeesListAdapter;
import com.example.tutionfee.data.MyDbHandler;
import com.example.tutionfee.data.model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayContacts extends AppCompatActivity {
TextView nametxt;
    private ArrayList<Contact> contactArrayList;
    private RecyclerView recyclerView;
    private FeesListAdapter recyclerViewAdapter;
    private Button delete;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts);
        nametxt=findViewById(R.id.displayContact);
//        phonetxt=findViewById(R.id.displayNumber);
        MyDbHandler db = new MyDbHandler(DisplayContacts.this);
        recyclerView=findViewById(R.id.listFees);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String phone=intent.getStringExtra("phone");
        nametxt.setText(name);
        delete=findViewById(R.id.deleteBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDbHandler db=new MyDbHandler(DisplayContacts.this);
                db.deleteStudent(name);
                db.deleteContact(name);
                try {
                    db.deletePendingName(name);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                db.close();
                finish();
                startActivity(new Intent(DisplayContacts.this,MainActivity.class));
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactArrayList=new ArrayList<>();
        List<Contact> contactList=db.getAllStudents(name);
        for(Contact contact: contactList){
            Log.d("dbharry", "\nId: " + contact.getId() + "\n" +
                    "Name: " + contact.getName() + "\n"+
                    "Phone Number: " + contact.getPhoneNumber() + "\n" );
            contactArrayList.add(contact);
        }
        Collections.reverse(contactList);
        recyclerViewAdapter=new FeesListAdapter(DisplayContacts.this, contactList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void hom(View v){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }public void fee(View v){
        startActivity(new Intent(getApplicationContext(),FeesPendingActivity.class));
    }public void cont(View v){
        startActivity(new Intent(getApplicationContext(),SettingActivity.class));
    }
}