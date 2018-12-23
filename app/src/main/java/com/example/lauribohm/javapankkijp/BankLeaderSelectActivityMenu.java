package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//This is like a MainActicity for BankLeader

public class BankLeaderSelectActivityMenu extends AppCompatActivity {

    TextView userLogged;
    Button show;
    Button remove;
    Button createA;
    Button createC;
    Button cardSettings;
    Button showt;
    Button removeU;
    Button goBack;
    Button logOut;

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_leader_select_menu);

        userLogged = (TextView) findViewById(R.id.whichUser);
        show = (Button) findViewById(R.id.showInfo);
        remove = (Button) findViewById(R.id.remove);
        createA = (Button) findViewById(R.id.createA);
        createC = (Button) findViewById(R.id.createC);
        cardSettings = (Button) findViewById(R.id.setupC);
        showt = (Button) findViewById(R.id.showTrans);
        removeU = (Button) findViewById(R.id.removeU);
        goBack = (Button) findViewById(R.id.backTo);
        logOut = (Button) findViewById(R.id.logOut);

        //Catches who is logged in

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        userLogged.setText(usernm);

        show.setOnClickListener(new View.OnClickListener() { //VALMIS
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankLeaderSelectActivityMenu.this, UserChangeInformationMenu.class);
                intent.putExtra("usersname", usernm);
                intent.putExtra("bankOrUser", bOrU);
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankLeaderSelectActivityMenu.this, ControlAccountsAndCards.class);
                intent.putExtra("usersname", usernm);
                intent.putExtra("bankOrUser", bOrU);
                startActivity(intent);
            }
        });

        createA.setOnClickListener(new View.OnClickListener() { //VALMIS
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankLeaderSelectActivityMenu.this, CreateAccountMenu.class);
                intent.putExtra("usersname", usernm);
                intent.putExtra("bankOrUser", bOrU);
                startActivity(intent);
            }
        });

        createC.setOnClickListener(new View.OnClickListener() { //VALMIS
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankLeaderSelectActivityMenu.this, CreateCardMenu.class);
                intent.putExtra("usersname", usernm);
                intent.putExtra("bankOrUser", bOrU);
                startActivity(intent);
            }
        });

        cardSettings.setOnClickListener(new View.OnClickListener() { //VALMIS
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankLeaderSelectActivityMenu.this, CardChangeMenu.class);
                intent.putExtra("usersname", usernm);
                intent.putExtra("bankOrUser", bOrU);
                startActivity(intent);
            }
        });

        showt.setOnClickListener(new View.OnClickListener() { //VALMIS
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankLeaderSelectActivityMenu.this, TransactionMenu.class);
                intent.putExtra("usersname", usernm);
                intent.putExtra("bankOrUser", bOrU);
                startActivity(intent);
            }
        });

        removeU.setOnClickListener(new View.OnClickListener() { // VALMIS
            @Override
            public void onClick(View v) {

                //If bankleader wants to remove user this will first delete all his/her accounts
                //and the same time cards and after that this will remove whole user

                for (int j = 0; j < bank.acctPointer.size(); j++) {

                    if(bank.acctPointer.get(j).getOwner().equals(usernm)) {
                        bank.acctPointer.remove(j);
                    }
                }

                for (int i = 0; i < bank.userPointer.size(); i++) {

                    if(usernm.equals(bank.userPointer.get(i).getUserName())) {
                        bank.userPointer.remove(i);
                    }
                }

                userLogged.setText("Käyttäjä poisttettu, palaa valitsemaan toinen");
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() { //VALMIS
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankLeaderSelectActivityMenu.this, BankLeaderSelectUserMenu.class);
                intent.putExtra("bankOrUser", bOrU);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankLeaderSelectActivityMenu.this, LogIn.class);
                startActivity(intent);
            }
        });
    }
}
