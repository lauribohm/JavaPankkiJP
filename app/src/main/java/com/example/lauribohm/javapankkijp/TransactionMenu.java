package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

//for showing all the users transactions

public class TransactionMenu extends AppCompatActivity {

    TextView outPut;
    TextView moneyOnAcct;

    Button goBack;
    Button showT;

    Spinner spinnerAccts;

    int chosenListNbr;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_menu);

        outPut = (TextView) findViewById(R.id.scroll);
        moneyOnAcct = (TextView) findViewById(R.id.textView45);

        goBack = (Button) findViewById(R.id.button8);
        showT = (Button) findViewById(R.id.button);

        spinnerAccts = findViewById(R.id.spinner5);

        //Catches who is logged in

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        //Checks if there is accounts which possibly could have transactions

        if (bank.acctPointer.size() != 0) {
            bank.spinnerForAccts(usernm, spinnerAccts, this);
        }

        else {
            Toast.makeText(this, "Sinulla ei ole tilitapahtumia",Toast.LENGTH_SHORT).show();
        }

        //prints all the transactions of the account user has selected

        showT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chosenListNbr = bank.spinnerForAccts(usernm, spinnerAccts, TransactionMenu.this);

                String balance = String.valueOf(bank.acctPointer.get(chosenListNbr).getMoney());
                moneyOnAcct.setText(balance);

                String allTransactions = "***************\n\n";


                //will loop also transactions

                //for (int i = 0; i < bank.acctPointer.size(); i++) {

                    for (int j = 0; j < bank.transPointer.size(); j++) {

                        if (bank.transPointer.get(j).getaNbr().equals(bank.acctPointer.get(chosenListNbr).getAcctNbr())) {

                            System.out.println("tapahtuma " + bank.transPointer.get(j).getNote());
                            allTransactions = allTransactions + bank.transPointer.get(j).getNote() + "\n\n***************\n\n";
                        }
                    }
                //}

                //using scrollingmethod because there might be many transactions

                outPut.setMovementMethod(new ScrollingMovementMethod());
                outPut.setText(allTransactions);

            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user is bankLeader

                if (bOrU == 1) {

                    Intent intent = new Intent(TransactionMenu.this, BankLeaderSelectActivityMenu.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

                //if user is normal user

                else {

                    Intent intent = new Intent(TransactionMenu.this, MainActicity.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }
            }
        });
    }
}
