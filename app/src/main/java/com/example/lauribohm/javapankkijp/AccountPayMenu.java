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

public class AccountPayMenu extends AppCompatActivity {

    EditText moneyPay;
    EditText toAcct;

    TextView acctBalance;
    TextView acctType;
    TextView info;

    Button save;
    Button goBack;

    Spinner spinnerAccts;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();

    private int chosenListNbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_pay_menu);

        moneyPay = (EditText) findViewById(R.id.PaySum);
        toAcct = (EditText) findViewById(R.id.acctforR);

        acctBalance = (TextView) findViewById(R.id.acctB);
        acctType = (TextView) findViewById(R.id.acctM);
        info = (TextView) findViewById(R.id.textView49);

        save = (Button) findViewById(R.id.button6);
        goBack = (Button) findViewById(R.id.button12);

        spinnerAccts = findViewById(R.id.spinner3);

        //Catches who is logged in

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        //If there is no accounts it will give error message and will not start searching accounts

        if (bank.acctPointer.size() == 0) {
            Toast.makeText(AccountPayMenu.this, "Sinulla ei ole tilejä, palaa luomaan",Toast.LENGTH_SHORT).show();
        }

        //searches accounts and make them available for spinner

        else {
            bank.spinnerForAccts(usernm, spinnerAccts, this);
        }

        //if there is not this setOnClick..., then the chosen List number would always be null and code would crash in part:
        //'bank.acctPointer.get(chosenListNbr).getFrozen().equals("true"))'
        //because the code will automatically run the 'spinnerForAccounts' and obviously there no choice from user immediately

        toAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //gets chosen account

                chosenListNbr = bank.spinnerForAccts(usernm, spinnerAccts, AccountPayMenu.this);

                //check if account is freezed and gives error message

                if (bank.acctPointer.get(chosenListNbr).getFrozen().equals("true")) {
                    Toast.makeText(AccountPayMenu.this, "Tili on jäädytetty, maksuja/siirtoja ei voida suorittaa.",Toast.LENGTH_SHORT).show();
                    info.setText("Tili on jäädytetty, maksuja/siirtoja ei voida suorittaa.");

                }

                //prints account balance on the screen

                else {
                    String balance = String.valueOf(bank.acctPointer.get(chosenListNbr).getMoney());
                    acctBalance.setText(balance);
                    acctType.setText(bank.acctPointer.get(chosenListNbr).getAcctMode().toString());
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double number = Double.parseDouble(moneyPay.getText().toString());

                //checks if user has enough money
                //also adds new transaction to transaction class

                if (number < bank.acctPointer.get(chosenListNbr).getMoney()) {
                    double number2 = (bank.acctPointer.get(chosenListNbr).getMoney() - number);
                    bank.acctPointer.get(chosenListNbr).setMoney(number2);

                    String num2 = String.valueOf((int) number2);
                    acctBalance.setText(num2);

                    info.setText("Maksu suoritettu");
                    Toast.makeText(AccountPayMenu.this, "Maksu suoritettu",Toast.LENGTH_SHORT).show();

                    bank.transPointer.add(new Transactions(bank.acctPointer.get(chosenListNbr).getAcctNbr(), "Tililtä: "
                            + bank.acctPointer.get(chosenListNbr).getAcctNbr() + "\nmaksettiin: " + number + "\ntilille: " + toAcct.getText().toString()));
                }

                //Gives error message if user does not have enough money

                else {
                    Toast.makeText(AccountPayMenu.this, "Ei tarpeeksi katetta maksun suorittamiseen",Toast.LENGTH_SHORT).show();
                    info.setText("Ei tarpeeksi katetta maksun suorittamiseen");
                }

                    //checks if recipient is this banks user
                    //and adds money to his/her acct and also adds to new transaction to this acct

                    for (int i = 0; i < bank.acctPointer.size(); i++) {

                        if (bank.acctPointer.get(i).getAcctNbr().equals(toAcct.getText().toString())) {

                            double number3 = (bank.acctPointer.get(i).getMoney() + number);
                            bank.acctPointer.get(i).setMoney(number3);

                            bank.transPointer.add(new Transactions(toAcct.getText().toString().trim(), "Vastaanotettu tililtä: " +
                                    bank.acctPointer.get(chosenListNbr).getAcctNbr() + "\nrahaa: " + number));
                        }
                    }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user is bankLeader

                if (bOrU == 1) {

                    Intent intent = new Intent(AccountPayMenu.this, BankLeaderSelectActivityMenu.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

                //if user is normal user

                else {

                    Intent intent = new Intent(AccountPayMenu.this, MainActicity.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

            }
        });
    }
}
