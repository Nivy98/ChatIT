package product.chatit.DatabaseManager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import java.util.ArrayList;

import product.chatit.calllogdesc.Call;
import product.chatit.chatdesc.DisplayChats;
import product.chatit.contactdesc.DisplayContacts;
import product.chatit.messagefilter.DisplayMessage;


public class DatabaseContainer extends SQLiteOpenHelper {
    private static final String DatabaseContainerContacts = DatabaseContainer.class.getSimpleName();
    private static final String KEY_PHONE_CHATS = "chat_phone" ;
    private static final String KEY_NAME_CHATS = "chat_name" ;
    private static final String KEY_MESSAGE_CHATS = "chat_msg" ;
    private static final String KEY_TIME_CHATS = "chat_time" ;
//    private String name,phone,photo;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "chatit";
    private static final String TABLE_Name_Contacts= "Contacts";
    private static final String TABLE_Name_Chats= "Chats";
    private static final String TABLE_Name_Calls= "Calls";


    private static final String KEY_PHONE = "phone";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHOTO = "photo";

    private static final String KEY_PHONE_CALLS = "call_phone" ;
    private static final String KEY_NAME_Calls = "call_name" ;
    private static final String KEY_TYPE_Calls = "call_type" ;
    private static final String KEY_Time_Calls = "call_time" ;




    public DatabaseContainer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    String createTableContacts= "CREATE TABLE " + TABLE_Name_Contacts + "(" + KEY_PHONE + " VARCHAR PRIMARY KEY NOT NULL, " + KEY_NAME +" VARCHAR, "+KEY_PHOTO+ " VARCHAR);";
    String createTableChats="CREATE TABLE " + TABLE_Name_Chats + "(" + KEY_PHONE_CHATS + " VARCHAR, " + KEY_NAME_CHATS +" VARCHAR, "+KEY_MESSAGE_CHATS+ " VARCHAR, "+KEY_TIME_CHATS+ " VARCHAR);";
    String createTableCalls="CREATE TABLE " + TABLE_Name_Calls + "(" + KEY_NAME_Calls + " VARCHAR, " + KEY_PHONE_CALLS + " VARCHAR, " + KEY_TYPE_Calls +" VARCHAR, "+KEY_Time_Calls+ " VARCHAR);";

        db.execSQL(createTableContacts);
    db.execSQL(createTableChats);
    db.execSQL(createTableCalls);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Name_Contacts);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Name_Chats);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Name_Calls);
        onCreate(db);
    }

    public void insertCalls(Call calls)
    {

        ContentValues values = new ContentValues();

        values.put(KEY_NAME_Calls,calls.getName());
        values.put(KEY_PHONE_CALLS, calls.getPhone());
        values.put(KEY_TYPE_Calls,calls.getCall_type());
        values.put(KEY_Time_Calls,calls.getDate());
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(TABLE_Name_Calls, null, values);
        db.close();

    }

    public void insertData(DisplayContacts displayContacts)
    {

        ContentValues values = new ContentValues();

        values.put(KEY_NAME,displayContacts.getName());
        values.put(KEY_PHONE,displayContacts.getPhoneNum());
        values.put(KEY_PHOTO,displayContacts.getPhotoURI());
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(TABLE_Name_Contacts, null, values);
        db.close();

    }

    public void insertDataChats(DisplayMessage displaychats)
    {
        ContentValues values = new ContentValues();
//        values.put(KEY_NAME,displayContacts.getName());
        values.put(KEY_PHONE_CHATS, displaychats.getNumber());
        values.put(KEY_NAME_CHATS,displaychats.getName());
        values.put(KEY_MESSAGE_CHATS,displaychats.getContent());
        values.put(KEY_TIME_CHATS,displaychats.getDate());
        SQLiteDatabase db=this.getWritableDatabase();
        Log.d(DatabaseContainerContacts,String.valueOf(db.insert(TABLE_Name_Chats, null, values)));
        db.close();
    }

    public ArrayList<DisplayMessage> getChats()
    {
        ArrayList<DisplayMessage> chatsList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery="SELECT * FROM "+TABLE_Name_Chats;
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
//                Log.d(DatabaseContainerContacts,"CAMEDBChats");
                chatsList.add(new DisplayMessage(cursor.getString(cursor.getColumnIndex(KEY_NAME_CHATS)),cursor.getString(cursor.getColumnIndex(KEY_PHONE_CHATS)),cursor.getString(cursor.getColumnIndex(KEY_TIME_CHATS)),cursor.getString(cursor.getColumnIndex(KEY_MESSAGE_CHATS))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return chatsList;
    }


    public ArrayList<Call> getCalls()
    {
        ArrayList<Call> callsList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery="SELECT * FROM "+TABLE_Name_Calls ;
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Log.d(DatabaseContainerContacts,"CAMEDBCalls");
                callsList.add(new Call(cursor.getString(cursor.getColumnIndex(KEY_NAME_Calls)),cursor.getString(cursor.getColumnIndex(KEY_PHONE_CALLS)),cursor.getString(cursor.getColumnIndex(KEY_Time_Calls)),cursor.getInt(cursor.getColumnIndex(KEY_TYPE_Calls))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return callsList;
    }

    public ArrayList<DisplayContacts> getContacts()
    {
        ArrayList<DisplayContacts> contactsList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery="SELECT * FROM "+TABLE_Name_Contacts;
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Log.d(DatabaseContainerContacts,"CAMEDBcontacts");
                contactsList.add(new DisplayContacts(cursor.getString(cursor.getColumnIndex(KEY_PHONE)),cursor.getString(cursor.getColumnIndex(KEY_NAME)),cursor.getString(cursor.getColumnIndex(KEY_PHOTO))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return contactsList;
    }




}
