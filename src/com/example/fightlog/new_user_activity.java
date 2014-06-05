package com.example.fightlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 *
 */
public class new_user_activity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
    }

    public void onClick(View v){}

    public void create_new_user_button(View v){

        String output="";

        EditText new_user_username = (EditText)findViewById(R.id.new_user_username);
        EditText new_user_password = (EditText)findViewById(R.id.new_user_password);
        EditText new_user_first_name = (EditText)findViewById(R.id.new_user_first_name);
        EditText new_user_last_name = (EditText)findViewById(R.id.new_user_last_name);
        EditText new_user_nick = (EditText)findViewById(R.id.new_user_nick);


        if(
                !new_user_username.getText().toString().trim().equals("")&&
                !new_user_password.getText().toString().trim().equals("")&&
                !new_user_first_name.getText().toString().trim().equals("")&&
        !new_user_last_name.getText().toString().trim().equals("")&&
        !new_user_nick.getText().toString().trim().equals(""))

        {

            String username = new_user_username.getText().toString().trim();
            String password = new_user_password.getText().toString().trim();
            String first_name = new_user_first_name.getText().toString().trim();
            String last_name = new_user_last_name.getText().toString().trim();
            String nick = new_user_nick.getText().toString().trim();

            String sRequest = "http://web.engr.illinois.edu/~jgpark2/FightLogs/users_table/insert_user.php?username="+username+"&password="+password+"&fname="+first_name+"&lname="+last_name+"&nick="+nick;

            System.out.println(sRequest);
            try {

                Process_request new_user = new Process_request();
                new_user.set_path(sRequest);
                Thread t = new Thread(new_user);
                t.start();
                t.join();
                output = new_user.return_response();
                Log.w("network", output);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(output.contains("query success"))
            {
                Toast.makeText(this, "New Account Created!", Toast.LENGTH_SHORT).show();
                login_activity.set_user(username, password);
                startActivity(new Intent(new_user_activity.this, user_homepage_activity.class));
            }
            else if(output.contains("Error"))
            {
                Toast.makeText(this, "Error inserting into database", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Please try again.", Toast.LENGTH_SHORT).show();
            }

        }
//        else
//        {
//          Toast.makeText(new_user_activity.this, " Please enter all the information", Toast.LENGTH_SHORT).show();
//        }
    }

}