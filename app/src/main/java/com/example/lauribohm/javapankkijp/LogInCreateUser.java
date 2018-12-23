package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//for creating a new user and saving user to class

public class LogInCreateUser extends AppCompatActivity {

    EditText usersname;
    EditText pass;
    EditText urName;
    EditText addr;
    EditText town;

    Button save;

    //Gives this class access to Bank class

    Bank bank = new Bank().getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_create_user);

        usersname = (EditText) findViewById(R.id.usersname);
        pass = (EditText) findViewById(R.id.password);
        urName = (EditText) findViewById(R.id.urname);
        addr = (EditText) findViewById(R.id.addr);
        town = (EditText) findViewById(R.id.town);

        save = (Button) findViewById(R.id.savendback);

        //adds given info to users 'profile'

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bank.userPointer.add(new Users (usersname.getText().toString(), pass.getText().toString(),
                        urName.getText().toString(), addr.getText().toString(), town.getText().toString()));

                Intent intent = new Intent(LogInCreateUser.this, LogIn.class);
                startActivity(intent);
            }
        });
    }


}
