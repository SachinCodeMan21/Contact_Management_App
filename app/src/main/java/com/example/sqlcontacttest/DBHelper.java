package com.example.sqlcontacttest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlcontacttest.models.Contact;
import com.example.sqlcontacttest.models.DbContact;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    //Database variables
    private static final String DATABASE_NAME ="Contact.db";
    private static final int DATABASE_VERSION = 4;

    //Table Variables
    private static final String Table_NAME ="Contact";
    private static  String Id ="contact_id";
    private static final String PERSON_NAME ="contact_name";
    private static final String PERSON_EMAIL ="contact_email";
    private static final String PERSON_PHONE ="contact_phone_no";
    private static final String PERSON_AGE ="contact_age";
    private static final String PERSON_IMG ="contact_img";

    //Sql queries
    private static final String CREATE_TABLE ="CREATE TABLE " + Table_NAME + " ( " +
            Id + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            PERSON_NAME + " TEXT ," +
            PERSON_EMAIL + " TEXT," +
            PERSON_PHONE + " int ," +
            PERSON_AGE + " int ," +
            PERSON_IMG + " int " +
            ")";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Table_NAME;



    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public long addContact(Contact contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERSON_NAME,contact.getContact_name());
        values.put(PERSON_EMAIL,contact.getContact_email());
        values.put(PERSON_PHONE,contact.getContact_phone());
        values.put(PERSON_AGE,contact.getContact_age());
        values.put(PERSON_IMG,contact.getContact_img());

        long id = db.insert(Table_NAME,null,values);
        db.close();
        return id;

    }

    @SuppressLint("Range")
    public DbContact getContact(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(Table_NAME,new String[]{Id,PERSON_NAME,PERSON_EMAIL,PERSON_PHONE,PERSON_AGE,PERSON_IMG},
                Id +"=?" ,new String[]{String.valueOf(id)},null,null,null,null );
        if (cursor!=null)
            cursor.moveToFirst();

        assert cursor != null;
        DbContact contact = new DbContact(
                cursor.getInt(cursor.getColumnIndex(Id)),cursor.getString(cursor.getColumnIndex(PERSON_NAME)), cursor.getString( cursor.getColumnIndex(PERSON_EMAIL)),
                   cursor.getInt(cursor.getColumnIndex(PERSON_PHONE)),cursor.getInt(cursor.getColumnIndex(PERSON_AGE)),cursor.getInt(cursor.getColumnIndex(PERSON_IMG)));
            cursor.close();

        return contact;
    }

    @SuppressLint("Range")
    public ArrayList<DbContact> getAllContacts(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DbContact> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + Table_NAME ,null);
            while(cursor.moveToFirst()){
                DbContact contact = new DbContact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(Id)));
                contact.setContact_name(cursor.getString(cursor.getColumnIndex(PERSON_NAME)));
                contact.setContact_email(cursor.getString(cursor.getColumnIndex(PERSON_EMAIL)));
                contact.setContact_phone(cursor.getInt(cursor.getColumnIndex(PERSON_PHONE)));
                contact.setContact_age(cursor.getInt(cursor.getColumnIndex(PERSON_AGE)));
                contact.setContact_img(cursor.getInt(cursor.getColumnIndex(PERSON_IMG)));

                list.add(contact);
            }

            cursor.close();
            db.close();
            return list;
    }

    public void updateContact(DbContact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERSON_NAME,contact.getContact_name());
        values.put(PERSON_EMAIL,contact.getContact_email());
        values.put(PERSON_PHONE,contact.getContact_phone());
        values.put(PERSON_AGE,contact.getContact_age());
        values.put(PERSON_IMG,contact.getContact_img());

        db.update(Table_NAME,values,Id + " =  ?",new String[]{String.valueOf(contact.getId())});

    }

    public void deleteContact(DbContact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_NAME,Id + " =  ?",new String[]{String.valueOf(contact.getId())});

    }



}
