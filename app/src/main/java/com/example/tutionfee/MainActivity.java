package com.example.tutionfee;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutionfee.adapter.RecyclerViewAdapter;
import com.example.tutionfee.data.MyDbHandler;
import com.example.tutionfee.data.model.Contact;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    //ListView listView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Contact> contactArrayList;
    private ArrayList<String> arrayAdapter;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    //    Button addStudent;
    LinearLayout addStudent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Toast.makeText(MainActivity.this,getCurrentDate(),Toast.LENGTH_LONG).show();
        StringBuilder sb = new StringBuilder();
        MyDbHandler db = new MyDbHandler(MainActivity.this);
        contactArrayList = new ArrayList<>();
        List<Contact> contactList = db.getAllContacts();
        for (Contact contact : contactList) {
            Log.d("dbharry", "\nId: " + contact.getId() + "\n" +
                    "Name: " + contact.getName() + "\n" +
                    "Phone Number: " + contact.getPhoneNumber() + "\n");
            contactArrayList.add(contact);
        }
        System.out.println("contactArrayList" + contactArrayList);
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        addStudent = findViewById(R.id.addStudent);
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.prompts, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);
                final DatePicker datePicker=(DatePicker) promptsView.findViewById(R.id.datePick);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        MyDbHandler db=new MyDbHandler(MainActivity.this);
                                        Calendar calendar = Calendar.getInstance();
//                                        calendar=Calendar.getInstance();
                                        calendar.set(datePicker.getYear(), datePicker.getMonth(),datePicker.getDayOfMonth());
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                        String strDate = format.format(calendar.getTime());
//                                        builder.append();
//                                        builder.append();

                                        String name=userInput.getText().toString();
                                        db.addStudent(name);
                                        Contact harry = new Contact();
//                                        harry.setPhoneNumber("9090909090");
                                        harry.setName(name);
                                        harry.setPhoneNumber(strDate);
                                        db.addContact(harry);
                                        db.close();
                                        Toast.makeText(MainActivity.this, name +" Added", Toast.LENGTH_LONG).show();
                                        recreate();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }
        });

    }
    private String getCurrentDate(){
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
//        dateTimeDisplay.setText(date);
        return date;
    }

    public void hom(View v){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }public void fee(View v){
        startActivity(new Intent(getApplicationContext(),FeesPendingActivity.class));
    }public void cont(View v){
        startActivity(new Intent(getApplicationContext(),SettingActivity.class));
    }

}