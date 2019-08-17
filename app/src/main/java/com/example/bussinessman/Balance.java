package com.example.bussinessman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Balance extends AppCompatActivity {
EditText Id,from,to,given,taken;
Button button,button1,button2,button3,button4,button5;
TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        Id=findViewById(R.id.Id);
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        taken=findViewById(R.id.taken);
        given=findViewById(R.id.given);
        t1=findViewById(R.id.t1);
        String TempHolder = getIntent().getStringExtra("to");

        to.setText(TempHolder);
        from.setText(Utilities.username);


    }


    public void Create(View view) {
        try {
            DBHelpher helper=new DBHelpher(this);
            SQLiteDatabase db=helper.getWritableDatabase();
            String query="create table customer(id text primary key, username text, toname text, given text, taken text)";
            db.execSQL(query);
            Toast.makeText(this,"Created",Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex){
            System.out.println(ex);
        }

    }

    public void Insert(View view) {
        try {
            DBHelpher helper=new DBHelpher(this);
            SQLiteDatabase db=helper.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("id",""+Id.getText());
            values.put("username",""+from.getText());
            values.put("toname",""+to.getText());
            values.put("given",""+given.getText());
            values.put("taken",""+taken.getText());


            db.insert("customer",null,values);
            Toast.makeText(this,"Inserted",Toast.LENGTH_SHORT).show();

           // setTitle("Inserted");
        }
        catch (Exception ex){
            setTitle(ex.getMessage());
        }
    }


    public void Select(View view) {
        try {
            DBHelpher helper = new DBHelpher(this);
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor cursor = db.query(false, "customer", new String[]{"id","username", "given", "taken"}, "toname=?", new String[]{""+to.getText()}, null, null, null, null);
            if (cursor.moveToFirst()) {
                Toast.makeText(this,"Selected",Toast.LENGTH_SHORT).show();

                Id.setText(""+cursor.getString(0));
                from.setText("" + cursor.getString(1));
                given.setText("" + cursor.getString(2));
                taken.setText("" + cursor.getString(3));

                int x=Integer.parseInt("" + taken.getText());
                int y=Integer.parseInt("" + given.getText());
                if(y>=x) {
                    t1.setText("Total Amount: Rs " + (y - x));
                }
                else if(x>y){
                    t1.setText("Total Amount: Rs " + (y - x));

                }

                return;
            } else {
                Id.setText("Not available");
                given.setText("Not found");
                taken.setText("Not found");


            }
        } catch (Exception ex) {
            setTitle(ex.getMessage());
        }
    }
    public void Update (View view){
        try {
            DBHelpher helper = new DBHelpher(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id","" +Id.getText());
            values.put("username", "" + from.getText());
            values.put("toname",""+to.getText());
            values.put("given", "" + given.getText());
            values.put("taken",""+taken.getText());
            db.update("customer", values, "id=?", new String[]{"" + Id.getText()});
            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();

           // setTitle("Updated");
        } catch (Exception ex) {
            setTitle(ex.getMessage());
        }
    }

    public void Delete (View view){
        try {
            DBHelpher helper = new DBHelpher(this);
            SQLiteDatabase db = helper.getWritableDatabase();

            db.delete("customer", "id=?", new String[]{"" + Id.getText()});
            Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show();
           // setTitle("Deleted");
        } catch (Exception ex) {
            setTitle(ex.getMessage());
        }
    }

    public void Clear(View view) {
        try{
            taken.setText("");
            given.setText("");
            Id.setText("");
        }
        catch (Exception ex){
            taken.setText(ex.getMessage());
            given.setText(ex.getMessage());
             Id.setText(ex.getMessage());
        }
    }
}
