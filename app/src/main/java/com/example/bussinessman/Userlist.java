package com.example.bussinessman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Userlist extends AppCompatActivity {
ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        lv = findViewById(R.id.lv);
        //  tv = findViewById(R.id.tv);

        userThread thread=new userThread("http://prahladghat.adorecatering.co.in/allusers.aspx");
        thread.execute();




    }
    public void FSu(View view) {
        try {

            TextView lv =(TextView) view;
            setTitle("" + lv.getText());
            Intent intent=new Intent(Userlist.this,Balance.class);
            intent.putExtra("to",""+lv.getText());
            startActivity(intent);
        }
        catch (Exception ex){
            setTitle(ex.getMessage());
        }
    }


    class userThread extends AsyncTask {
        String path;

        public userThread(String path) {
            this.path = path;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL(path);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                Scanner s = new Scanner(in);
                String result = "";
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    result = result + line;

                }
                return result;
            } catch (Exception ex) {
                return ex;
            }
        }

        @Override
        public void onPostExecute(Object result) {
            super.onPostExecute(result);
            try {
                if (result instanceof Exception) {
                    throw (Exception) result;

                }

                String data = "" + result;
                data = data.trim();
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("users");
                int n = jsonArray.length();
                users[] Users = new users[n];

                for (int i = 0; i <= n - 1; i++) {
                    JSONObject object = new JSONObject("" + jsonArray.get(i));

                    String user = object.getString("user");


                    users Users1 = new users(user);
                    Users[i] = Users1;

                }
                messageadapter adapter = new messageadapter(Userlist.this,Users);
                lv.setAdapter(adapter);





            } catch (Exception ex) {
                System.out.println(ex);
                //tv.setText(ex.getMessage());
                Toast.makeText(Userlist.this, ex.getMessage(), Toast.LENGTH_SHORT).show();


            }


        }
    }
}
