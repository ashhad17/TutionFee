package com.example.tutionfee.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;
import com.example.tutionfee.data.model.Contact;
import com.example.tutionfee.params.Params;
import java.util.ArrayList;
import java.util.List;
public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY," + Params.KEY_NAME
                + " TEXT , "+Params.KEY_DATE
                + " TEXT )";
        Log.d("dbharry", "Query being run is : "+ create);
        db.execSQL(create);
db.execSQL("CREATE TABLE " + Params.TABLE1_NAME + "("
        + Params.KEY_ID + " INTEGER PRIMARY KEY," + Params.KEY_NAME
        + " TEXT , "+Params.KEY_DATE
        + " TEXT )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_DATE, contact.getPhoneNumber());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbharry", "Successfully inserted");
        db.close();


    }
    public void addFeesDetails(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Fees_Date", getCurrentDate());
        db.insert(contact.getName(), null, values);
        Log.d("dbharry", "Successfully inserted");
        db.close();
    }
    public void addPendingStudent(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_DATE, contact.getPhoneNumber());
        db.insert(Params.TABLE1_NAME, null, values);
        Log.d("dbharry", "Successfully inserted");
        db.close();
    }
    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
//        dateTimeDisplay.setText(date);
        return date;
    }
    public void addStudent(String Table_Name)
    {
        SQLiteDatabase dbs = this.getWritableDatabase();
        dbs.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        String query = "CREATE TABLE " + Table_Name + "(ID TEXT PRIMARY KEY,Fees_Date TEXT);";
        dbs.execSQL(query);
        dbs.close();
    }
    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
//        int id=0;
        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
//                id++;
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }
    public List<Contact> getAllStudents(String name){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Generate the query to read from the database
        String select = "SELECT * FROM " + name;
        Cursor cursor = db.rawQuery(select, null);
        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
//                if (cursor.getString(0) != null) {addP
//                    try {
//
//                        contact.setId(Integer.parseInt(cursor.getString(0)));
//                        contact.setPhoneNumber(cursor.getString(1));
//                    } catch(NumberFormatException e) {
//                        // Deal with the situation like
//
////                        contact.setId(Integer.parseInt(cursor.getString(0)));
//                        contact.setPhoneNumber(cursor.getString(1));
//                    }
//                }
//                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setPhoneNumber(cursor.getString(1));
//                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }
    public List<Contact> getAllStudentsPending(){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE1_NAME +" ORDER BY "+Params.KEY_DATE+" ASC";
        Cursor cursor = db.rawQuery(select, null);
int id=1;
        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
//        values.put(Params.KEY_PHONE, contact.getPhoneNumber());

        //Lets update now
        return db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});


    }
    public void deleteStudent(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_NAME+"=?",new String[]{String.valueOf(name)});
        db.execSQL("DROP TABLE IF EXISTS "+name);
        db.close();
    }
    public void deleteContact(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_NAME+"=?",new String[]{String.valueOf(name)});
        db.close();
    }
    public void deletePendingName(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Params.TABLE1_NAME,Params.KEY_NAME+"=?",new String[]{String.valueOf(name)});
        db.close();
    }
    public void deletePending(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Params.TABLE1_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    public int getCount(){
        String query = "SELECT  * FROM " + Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }


}
