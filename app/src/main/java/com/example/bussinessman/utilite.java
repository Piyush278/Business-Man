package com.example.bussinessman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

public class utilite {

    public static void select(Context context, EditText txtUserName, EditText txtPassword)
    {
        try {
            DBHelpher helper = new DBHelpher(context);
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor cursor = db.query(false, "student", new String[]{"username", "password"}, "username=?,password=?", new String[]{"" + txtUserName.getText(),""+txtPassword}, null, null, null, null);
            if (cursor.moveToFirst()) {

                txtUserName.setText("" + cursor.getString(0));
                txtPassword.setText("" + cursor.getString(1));
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

    }



    public static void Create(Context context) {
        try {
            DBHelpher helpher = new DBHelpher(context);
            SQLiteDatabase db = helpher.getWritableDatabase();
            String query = "create table student(username text, password text)";
            db.execSQL(query);



            ContentValues values=new ContentValues();
            values.put("username","");
            values.put("password","");
            db.insert("student",null,values);


        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public static void Update (Context context,String username, String Password){
        try {

            DBHelpher helper = new DBHelpher(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Username", "" +username);
            values.put("Password", ""+Password);
            db.update("student", values, "Username=?,Password=?", new String[]{"" + username,""+Password});


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
