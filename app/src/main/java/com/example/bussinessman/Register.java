package com.example.bussinessman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText txtUserName,txtPassword,txtName,txtPhone;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtName=findViewById(R.id.txtName);
        txtPassword=findViewById(R.id.txtPassword);
        txtPhone=findViewById(R.id.txtPhone);
        txtUserName=findViewById(R.id.txtUserName);
        button=findViewById(R.id.button);
    }


    //***************************************************************************

    class RegistrationThread extends AsyncTask {
        String path;
        String name,password,username,mobile;

        public RegistrationThread(String name,String password,String mobile,String username) {
            this.mobile=mobile;
            this.name=name;
            this.password=password;
            this.username=username;
            this.path=Utilities.register(username,password,name,mobile);

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {

                String result = Utilities.downloadString(path);


                return result;
            } catch (Exception ex) {
                return ex;
            }
        }

        @Override
        public void onPostExecute(Object result) {
            try {
                super.onPostExecute(result);
                if (result instanceof Exception)
                    throw (Exception) result;

                //  String data=Utilities.register(""+txtUserName.getText(),""+txtPassword.getText(),""+txtName.getText(),""+txtPhone.getText());
                String data=""+result;
                setTitle("Registration Sucessfully"+data);
            }
            catch (Exception ex) {
                System.out.println(ex);
                Toast.makeText(Register.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                setTitle("Registred not Sucessfully");
            }
        }
    }
    //***************************************************************************
    public void FAdd(View view) {


        RegistrationThread thread =new RegistrationThread(""+txtName.getText(),""+txtPassword.getText(),""+txtPhone.getText(),""+txtUserName.getText());
        thread.execute();

    }

    public void FSub(View view) {
        Intent intent=new Intent(Register.this,MainActivity.class);
        startActivity(intent);
    }
}
