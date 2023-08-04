package com.example.tutionfee;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutionfee.adapter.FeesPendingAdapter;
import com.example.tutionfee.data.MyDbHandler;
import com.example.tutionfee.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class FeesPendingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FeesPendingAdapter recyclerViewAdapter;
    private ArrayList<Contact> contactArrayList;
    LinearLayout addStudent;
    Calendar calendar;
    SimpleDateFormat dateFormat;
//    private ArrayList<String> arrayAdapter;
    //    Button addStudent;
//    LinearLayout addStudent;
    private Button mButton;
//    int count=1;
    private boolean firstTimeUsed = false;
    private String firstTimeUsedKey="FIRST_TIME";
    private String sharedPreferencesKey = "MY_PREF";
    private String buttonClickedKey = "BUTTON_CLICKED";
    private SharedPreferences mPrefs;
    private long savedDate=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_pending);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringBuilder sb = new StringBuilder();
        MyDbHandler db = new MyDbHandler(FeesPendingActivity.this);
        contactArrayList = new ArrayList<>();
        List<Contact> contactList = db.getAllStudentsPending();
        for (Contact contact : contactList) {
            Log.d("dbharry", "\nId: " + contact.getId() + "\n" +
                    "Name: " + contact.getName() + "\n" +
                    "Phone Number: " + contact.getPhoneNumber() + "\n");
//            sb.append("\nID: "+contact.getId()).append("\nName: "+contact.getName()).append("\nNumber"+contact.getPhoneNumber());
            contactArrayList.add(contact);
        }
        System.out.println("contactArrayList" + contactArrayList);
        recyclerViewAdapter = new FeesPendingAdapter(FeesPendingActivity.this, contactArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        // Inside your activity or fragment
        mButton = findViewById(R.id.filter);
        mPrefs = getSharedPreferences(sharedPreferencesKey, this.MODE_PRIVATE);
        savedDate = mPrefs.getLong(buttonClickedKey,0);
        firstTimeUsed = mPrefs.getBoolean(firstTimeUsedKey,true);//default is true if no value is saved
        checkPrefs();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDbHandler db=new MyDbHandler(getApplicationContext());
//        db.getAllStudentsPending();
                List<Contact> contactList = db.getAllContacts();
                for (Contact contact : contactList) {
                    Log.d("LogAshhad","Inserted Pending");
//                    if(contact.getPhoneNumber()==getCurrentDate())
//                    {
//                        db.addPendingStudent(contact);
//                    }
//            sb.append("\nID: "+contact.getId()).append("\nName: "+contact.getName()).append("\nNumber"+contact.getPhoneNumber());
                    db.addPendingStudent(contact);
                }
                db.close();
                saveClickedTime();
                recreate();
                }
            }
        );
        addStudent = findViewById(R.id.addStudent);
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(FeesPendingActivity.this);
                View promptsView = li.inflate(R.layout.prompts, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        FeesPendingActivity.this);
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
                                        MyDbHandler db=new MyDbHandler(FeesPendingActivity.this);
                                        String name=userInput.getText().toString();
                                        Calendar calendar = Calendar.getInstance();
//                                        calendar=Calendar.getInstance();
                                        calendar.set(datePicker.getYear(), datePicker.getMonth(),datePicker.getDayOfMonth());
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                        String strDate = format.format(calendar.getTime());
                                        db.addStudent(name);
                                        Contact harry = new Contact();
//                                        harry.setPhoneNumber("9090909090");
                                        harry.setName(name);
                                        harry.setPhoneNumber(strDate);
                                        db.addContact(harry);
                                        db.close();
                                        Toast.makeText(FeesPendingActivity.this, name +" Added", Toast.LENGTH_LONG).show();
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
    private void checkPrefs(){
        if(firstTimeUsed==false){
            if(savedDate>0){
                Calendar currentCal = Calendar.getInstance();
                // Create an instance of Calendar and set it to the saved date
                Calendar savedCal = Calendar.getInstance();
                savedCal.setTimeInMillis(savedDate); // Set the time in millis from saved in sharedPrefs
                // Calculate the difference between the current date and the saved date in milliseconds
                long timeDifference = currentCal.getTimeInMillis() - savedCal.getTimeInMillis();
                // Calculate the difference in days
//                (timeDifference / (24 * 60 * 60 * 1000));
//                int daysDifference = (int) (timeDifference / ( 60 * 1000));
                int timDifference=(int) (timeDifference / (60 * 1000));
                int daysDifference = (int) (timeDifference / (24 * 60 * 60 * 1000));
                if (daysDifference >= 30) {
                    mButton.setVisibility(View.VISIBLE);
                } else {
                    mButton.setVisibility(View.GONE);
                }
            }
        }else{
            //just set the button visible if app is used the first time
            mButton.setVisibility(View.VISIBLE);

        }
    }
    private void saveClickedTime(){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        Calendar cal = Calendar.getInstance();
        long millis = cal.getTimeInMillis();
        mEditor.putLong(buttonClickedKey,millis);
        mEditor.putBoolean(firstTimeUsedKey,false); //the button is clicked first time, so set the boolean to false.
        mEditor.commit();
        //hide the button after clicked
        mButton.setVisibility(View.GONE);

    }

    public void hom(View v){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }public void fee(View v){
        startActivity(new Intent(getApplicationContext(),FeesPendingActivity.class));
    }public void cont(View v){
        startActivity(new Intent(getApplicationContext(),SettingActivity.class));
    }
    public void refresh(){
        recreate();
    }
}