package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//for deleting, freezing etc.

public class ControlAccountsAndCards extends AppCompatActivity {

    TextView acctType;
    TextView acctMoney;

    TextView cardLimitP;
    TextView cardLimitT;
    TextView cardRegion;

    Button acctR;
    Button acctF;

    Button cardR;
    Button cardD;

    Button goBack;

    Button showA;
    Button showC;

    Spinner acctSpinner;
    Spinner cardSpinner;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();

    int chosenListNbr;
    int chosenCardArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_accounts_and_cards);

        acctType = (TextView) findViewById(R.id.acctType);
        acctMoney = (TextView) findViewById(R.id.acctMoney);

        cardLimitP = (TextView) findViewById(R.id.textView48);
        cardLimitT = (TextView) findViewById(R.id.textView47);
        cardRegion = (TextView) findViewById(R.id.textView46);

        acctR = (Button) findViewById(R.id.button10);
        acctF = (Button) findViewById(R.id.button13);

        cardR = (Button) findViewById(R.id.button15);
        cardD = (Button) findViewById(R.id.button14);

        showA = (Button) findViewById(R.id.showa);
        showC = (Button) findViewById(R.id.showc);

        goBack = (Button) findViewById(R.id.button16);

        acctSpinner = findViewById(R.id.spinner7);
        cardSpinner = findViewById(R.id.spinner8);

        //Catches who is logged in
        //now it is bankleader and this will in the end of this page pass nbr '1' forward

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        //makes different kinds of accounts and cards available to select for user

        bank.spinnerForAccts(usernm, acctSpinner, this);
        bank.spinnerForCards(usernm, cardSpinner, this);

        //will reveal selected account information

        showA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chosenListNbr = bank.spinnerForAccts(usernm, acctSpinner, ControlAccountsAndCards.this);

                String balance = String.valueOf(bank.acctPointer.get(chosenListNbr).getMoney());
                acctMoney.setText(balance);
                acctType.setText(bank.acctPointer.get(chosenListNbr).getAcctMode());
            }
        });

        //will reveal selected card information

        showC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chosenCardArray = bank.spinnerForCards(usernm, cardSpinner, ControlAccountsAndCards.this);

                String PL = String.valueOf(bank.acctPointer.get(chosenCardArray).getPayLimit());
                String TL = String.valueOf(bank.acctPointer.get(chosenCardArray).getTakeLimit());

                cardLimitP.setText(PL);
                cardLimitT.setText(TL);
                cardRegion.setText(bank.acctPointer.get(chosenCardArray).getRegion());

            }
        });

        //removes selected account
        //and at the same time cards that are created for this account

        acctR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bank.acctPointer.remove(chosenListNbr);
                acctMoney.setText("");
                acctType.setText("");

                bank.spinnerForAccts(usernm, acctSpinner, ControlAccountsAndCards.this);
                bank.spinnerForCards(usernm, cardSpinner, ControlAccountsAndCards.this);

                Toast.makeText(ControlAccountsAndCards.this, "Tili poistettu onnistuneesti",Toast.LENGTH_SHORT).show();
            }
        });

        //will freeze selected account by adding value "true"

        acctF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bank.acctPointer.get(chosenListNbr).setFrozen("true");

                Toast.makeText(ControlAccountsAndCards.this, "Tili jäädytetty onnistuneesti",Toast.LENGTH_SHORT).show();
            }
        });

        //will remove card by setting it value 'false'

        cardR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bank.acctPointer.get(chosenCardArray).setCard("false");
                bank.acctPointer.get(chosenCardArray).setPayLimit(0);
                bank.acctPointer.get(chosenCardArray).setTakeLimit(0);
                bank.acctPointer.get(chosenCardArray).setRegion("");

                cardLimitP.setText("");
                cardLimitT.setText("");
                cardRegion.setText("");

                bank.spinnerForCards(usernm, cardSpinner, ControlAccountsAndCards.this);

                Toast.makeText(ControlAccountsAndCards.this, "Kortti positettu onnistuneesti",Toast.LENGTH_SHORT).show();
            }
        });

        //will set selected card as dead

        cardD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bank.acctPointer.get(chosenCardArray).setDead("true");

                Toast.makeText(ControlAccountsAndCards.this, "Kortti kuoletettu onnistuneesti",Toast.LENGTH_SHORT).show();
            }
        });

        //returns back to bankLeader "main activity"

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ControlAccountsAndCards.this, BankLeaderSelectActivityMenu.class);
                intent.putExtra("usersname", usernm);
                intent.putExtra("bankOrUser", bOrU);
                startActivity(intent);
            }
        });
    }
}
