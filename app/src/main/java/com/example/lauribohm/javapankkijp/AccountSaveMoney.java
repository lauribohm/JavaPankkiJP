package com.example.lauribohm.javapankkijp;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AccountSaveMoney extends AppCompatActivity {

    TextView oldBalance;
    TextView acctType;

    Spinner spinnerAccts;
    EditText deposit;

    Button save;
    Button goBack;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();

    int chosenListNbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_save_money);

        oldBalance = (TextView) findViewById(R.id.acctB);
        acctType = (TextView) findViewById(R.id.acctM);

        deposit = (EditText) findViewById(R.id.newMo);

        spinnerAccts = findViewById(R.id.spinner4);

        save = (Button) findViewById(R.id.button7);
        goBack = (Button) findViewById(R.id.button17);

        //Catches who is logged in

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        //If there is no accounts it will give error message and will not start searching accounts

        if (bank.acctPointer.size() == 0) {
            Toast.makeText(AccountSaveMoney.this, "Sinulla ei ole tilejä, palaa luomaan",Toast.LENGTH_SHORT).show();
        }

        //searches accounts and make them available for spinner

        else {
            bank.spinnerForAccts(usernm, spinnerAccts, this);
        }

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chosenListNbr = bank.spinnerForAccts(usernm, spinnerAccts, AccountSaveMoney.this);

                String balance = String.valueOf(bank.acctPointer.get(chosenListNbr).getMoney());
                oldBalance.setText(balance);
                acctType.setText(bank.acctPointer.get(chosenListNbr).getAcctMode().toString());

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //adds the money to account

                double number = Double.parseDouble(deposit.getText().toString());
                double number2 = (number + bank.acctPointer.get(chosenListNbr).getMoney());

                bank.acctPointer.get(chosenListNbr).setMoney(number2);

                String balance = String.valueOf(bank.acctPointer.get(chosenListNbr).getMoney());
                oldBalance.setText(balance);

                Toast.makeText(AccountSaveMoney.this, "Talletus onnistui",Toast.LENGTH_SHORT).show();

                bank.transPointer.add(new Transactions(bank.acctPointer.get(chosenListNbr).getAcctNbr(), "Tilille: " + bank.acctPointer.get(chosenListNbr).getAcctNbr() +
                "\ntalletettiin rahaa: " + number));
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user is bankLeader

                if (bOrU == 1) {

                    Intent intent = new Intent(AccountSaveMoney.this, BankLeaderSelectActivityMenu.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

                //if user is normal user

                else {

                    Intent intent = new Intent(AccountSaveMoney.this, MainActicity.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }
            }
        });


    }
}
