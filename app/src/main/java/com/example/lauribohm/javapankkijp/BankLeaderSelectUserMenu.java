package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

//for selecting user

public class BankLeaderSelectUserMenu extends AppCompatActivity {

    Spinner spinner;
    Button button;
    Button logOut;

    Bank bank = Bank.getInstance();

    String userChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_leader_select_user_menu);

        spinner = findViewById(R.id.spinner6);
        button = (Button) findViewById(R.id.button9);
        logOut = (Button) findViewById(R.id.button11);

        //Catches who is logged in

        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        //spinner for selecting user

        final List<String> usTotal = new ArrayList<>();

        usTotal.add(0, "");

        for (int i = 0; i < bank.userPointer.size(); i++) {

            usTotal.add(bank.userPointer.get(i).getUserName());
        }

        ArrayAdapter<String> usersTotal = new ArrayAdapter(this, android.R.layout.simple_spinner_item, usTotal);

        usersTotal.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(usersTotal);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("")) {
                }

                else {
                    userChosen = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //will go forward to BankLeaders "main activity"

                Intent intent = new Intent(BankLeaderSelectUserMenu.this, BankLeaderSelectActivityMenu.class);
                intent.putExtra("usersname", userChosen);
                intent.putExtra("bankOrUser", bOrU);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankLeaderSelectUserMenu.this, LogIn.class);
                startActivity(intent);

            }
        });

    }
}
