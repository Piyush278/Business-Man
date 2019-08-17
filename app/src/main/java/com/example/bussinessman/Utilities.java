package com.example.bussinessman;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

public class Utilities {

    public static String username="";
  //  public static String  to="";
    public static String register(String username, String password, String name, String mobile) {
        try {

            name = encode(name);
            password = encode(password);
            username = encode(username);
            mobile = encode(mobile);
            String url = "http://prahladghat.adorecatering.co.in/register.aspx?name=" + name + "&password=" + password + "&username=" + username + "&mobile=" + mobile;
            url = url.trim();

            return url;
        } catch (Exception ex) {
            throw ex;
        }
    }


    public static String encode(String data) {
        try {
            data = "" + data;
            data = data.trim();
            data = URLEncoder.encode(data, "UTF-8");
            return data;
        } catch (UnsupportedEncodingException ex) {
            return "";
        }
    }

    public static String login(String username, String password) {
        try {

            password = encode(password);
            username = encode(username);

            String url = "http://prahladghat.adorecatering.co.in/login.aspx?password=" + password + "&username=" + username;
            url = url.trim();

            return url;
        } catch (
                Exception ex) {
            throw ex;
        }

    }






    public static String downloadString(String location) throws IOException {

        try {

            URL url = new URL(location);
            URLConnection connection = url.openConnection();
            connection.connect();
            Scanner scanner = new Scanner(connection.getInputStream());
            String str = "";
            while (scanner.hasNextLine()) {
                str = str + scanner.nextLine();


            }


            return str;
        }
        catch (Exception ex){
            throw ex;
        }
    }
}
