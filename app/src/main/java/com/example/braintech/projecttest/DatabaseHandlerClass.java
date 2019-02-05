package com.example.braintech.projecttest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.braintech.projecttest.Model.UserModel;

public class DatabaseHandlerClass extends SQLiteOpenHelper {

    private static String KEY_TABLE = "User";
    private static String KEY_ID = "id";
    private static String KEY_NAME = "Name";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserDb";
    private static String KEY_EMAILADDRESS = "Email";
    private static String KEY_STATE = "State";
    private static String KEY_CITY = "City";
    private static String KEY_MOBILE = "Mobile";
    private static String KEY_PASSWORD = "Password";


    public DatabaseHandlerClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String Create = "CREATE TABLE "+KEY_TABLE+"("+KEY_ID+" INTEGER PRIMARY KEY ,"+KEY_NAME+" TEXT,"
                +KEY_EMAILADDRESS+" TEXT,"+KEY_STATE+" TEXT,"+KEY_CITY+" TEXT,"+KEY_MOBILE+" TEXT,"+KEY_PASSWORD+" TEXT"+")";
        db.execSQL(Create);

    }

    public int insertdata(UserModel userModel)
    {
        int i = 0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,userModel.getName());
        contentValues.put(KEY_EMAILADDRESS,userModel.getEmail());
        contentValues.put(KEY_STATE,userModel.getState());
        contentValues.put(KEY_CITY,userModel.getCity());
        contentValues.put(KEY_MOBILE,userModel.getMobile());
        contentValues.put(KEY_PASSWORD,userModel.getPassword());
        sqLiteDatabase.insert(KEY_TABLE,null,contentValues);
        i = 1;
        return i;
    }


    public Boolean checkmail(String email)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from User where Email=?",new String[]{email});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }

    public Boolean AuthenticateUser(String username,String password)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from User where Email=? and Password=?",
                new String[]{username,password});
        if (cursor.getCount() > 0)
        {
            Log.d("Cursor------>", String.valueOf(cursor.getCount()));
            return true;
        }
        else
            return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
