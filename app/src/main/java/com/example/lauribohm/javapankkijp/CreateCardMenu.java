package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateCardMenu extends AppCompatActivity {

    EditText payLimit;
    EditText takeLimit;

    TextView info;

    Button save;
    Button goBack;

    private Spinner spinner;
    private Spinner spinnerRegion;

    private int accountChosen;
    private String regionChosen;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_menu);

        payLimit = (EditText) findViewById(R.id.payLimit);
        takeLimit = (EditText) findViewById(R.id.takeLimit);

        info = (TextView) findViewById(R.id.textView50);

        spinner = findViewById(R.id.spinnerC);
        spinnerRegion = findViewById(R.id.spinnerR);

        save = (Button) findViewById(R.id.button3);
        goBack = (Button) findViewById(R.id.back);

        //Catches who is logged in

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        //Checks if there is accounts for creating a card

        if (bank.acctPointer.size() != 0) {

            bank.spinnerForAccts(usernm, spinner, this);
            bank.spinnerForRegion(spinnerRegion, this);
        }

        // if there is not will give error message

        else {

            info.setText("Sinulla ei ole tilejä, palaa luomaan tili");
            Toast.makeText(CreateCardMenu.this, "Sinulla ei ole tilejä, palaa luomaan tili",Toast.LENGTH_SHORT).show();
        }

        //checks for which account user wants to create a card
        //checks also if it ios possible to create a card for this account
        //example if it is saveAccount or there is already a card for account
        //in the end it will create a card if there is nothing to stop

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accountChosen = bank.spinnerForAccts(usernm, spinner, CreateCardMenu.this);

                System.out.println(bank.acctPointer.get(accountChosen).getAcctMode());

                if (bank.acctPointer.get(accountChosen).getAcctMode().equals("Säästötili")) {

                    info.setText("Säästötilille ei voida luoda korttia, valitse toinen");
                    Toast.makeText(CreateCardMenu.this, "Säästötilille ei voida luoda korttia, valitse toinen",Toast.LENGTH_SHORT).show();
                }

                else if (bank.acctPointer.get(accountChosen).getCard().equals("true")) {

                    info.setText("Tilillä on jo kortti, valitse toinen");
                    Toast.makeText(CreateCardMenu.this, "Tilillä on jo kortti, valitse toinen",Toast.LENGTH_SHORT).show();

                }

                else {

                    //adds limits etc to card and creates card by stting it value as "true"

                    regionChosen = bank.spinnerForRegion(spinnerRegion, CreateCardMenu.this);

                    double limitT = Double.parseDouble(takeLimit.getText().toString());
                    double limitP = Double.parseDouble(payLimit.getText().toString());

                    if (bank.acctPointer.get(accountChosen).getCard().equals("false")) {

                        bank.acctPointer.get(accountChosen).setCard("true");
                        bank.acctPointer.get(accountChosen).setRegion(regionChosen);
                        bank.acctPointer.get(accountChosen).setPayLimit(limitP);
                        bank.acctPointer.get(accountChosen).setTakeLimit(limitT);

                        Toast.makeText(CreateCardMenu.this, "Kortti luotu onnistuneesti", Toast.LENGTH_SHORT).show();
                        info.setText("Kortti luotu onnistuneesti");
                    }

                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user is bankLeader

                if (bOrU == 1) {

                    Intent intent = new Intent(CreateCardMenu.this, BankLeaderSelectActivityMenu.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

                //if user is normal user

                else {

                    Intent intent = new Intent(CreateCardMenu.this, MainActicity.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }
            }
        });

    }
}
