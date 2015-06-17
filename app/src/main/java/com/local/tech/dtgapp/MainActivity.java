package com.local.tech.dtgapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    WebControls web = new WebControls();
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons("login");
            }
        });
    }


    public void buttons(String buttonName){

        switch(buttonName){
            case "login":
                EditText username = (EditText)findViewById(R.id.usernameTxt);
                EditText password = (EditText)findViewById(R.id.passwordTxt);
                web.login(username.getText().toString(),password.getText().toString());
                break;
            case "test":
                break;
        }
    }

}
