package com.example.fightlog;


import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Process_request implements Runnable{
    private volatile String response="";
    private String request="";

    public void run() {
        try {
            Document d = Jsoup.connect(request).get();
            Log.w("network", d.text());
            response = d.text();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String runProcess(String in_request)
    {


        try {

            Process_request check_login = new Process_request();
            check_login.set_path(in_request);
            Thread t = new Thread(check_login);
            t.start();
            t.join();
            return check_login.return_response();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void set_path(String path)
    {
        request = path;
    }

    public String return_response()
    {
        return response;
    }

}