package com.example.bussinessman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtUserName, txtPassword;
    Button button;
    TextView tv;
    CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private    Boolean saveLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            utilite.Create(this);
            setTitle("Created");
        }
        catch (Exception ex){
            System.out.println(ex);
        }

        txtPassword = findViewById(R.id.txtPassword);
        txtUserName = findViewById(R.id.txtUserName);
        tv = findViewById(R.id.tv);
        button = findViewById(R.id.button);
        try {
            utilite.select(this,txtUserName,txtPassword);
            setTitle("Selected");
        }
        catch (Exception ex){
            System.out.println(ex);
        }
        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();


        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            txtUserName.setText(loginPreferences.getString("username", ""));
            txtPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);

        }
    }

    class LoginThread extends AsyncTask {
        String path;
        String password, username;

        public LoginThread(String password, String username) {

            this.password = password;
            this.username = username;
            this.path = Utilities.login(username, password);

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

                String data = "" + result;
                data = data.trim();
                setTitle(data);

                if (data.equals("1")) {
                    Utilities.username="" + txtUserName.getText();
                    utilite.Update(MainActivity.this,""+txtUserName.getText(),""+txtPassword.getText());
                    setTitle("updated");
                    Intent intent = new Intent(MainActivity.this,Userlist.class);

                    startActivity(intent);
                }

            } catch (Exception ex) {
                System.out.println(ex);
                Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                setTitle("Not Registered Username");
            }
        }
    }

    public void FAdd (View view){

        String username = txtUserName.getText().toString();
        String  password = txtPassword.getText().toString();

        if (saveLoginCheckBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", username);
            loginPrefsEditor.putString("password", password);
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }
        LoginThread thread = new LoginThread("" + txtPassword.getText(), "" + txtUserName.getText());
        thread.execute();




    }


    public void FSub (View view){
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);


    }
}
