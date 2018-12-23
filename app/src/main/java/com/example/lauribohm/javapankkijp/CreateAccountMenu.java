//VALMIS

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//for creating account

public class CreateAccountMenu extends AppCompatActivity {

    EditText acctnbr;
    EditText money;

    Button save;
    Button goBack;

    private Spinner spinnerType;
    private String accountTypeChosen;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_menu);

        acctnbr = (EditText) findViewById(R.id.nbr);
        money = (EditText) findViewById(R.id.sum);

        save = (Button) findViewById(R.id.button2);
        goBack = (Button) findViewById(R.id.backto);

        spinnerType = findViewById(R.id.spinnerType);

        //Catches who is logged in

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        bank.spinnerForAccountType(spinnerType, this);

        //checks which type of account user wants to create
        // and creates account

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accountTypeChosen = bank.spinnerForAccountType(spinnerType, CreateAccountMenu.this);

                double number = Double.parseDouble(money.getText().toString());
                bank.acctPointer.add(new Account(usernm, accountTypeChosen, acctnbr.getText().toString(),  number, 0, null, "false", 0, "false", "false"));

                System.out.println(accountTypeChosen);

                Toast.makeText(CreateAccountMenu.this, "Tili luotu onnistuneesti",Toast.LENGTH_SHORT).show();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user is bankLeader

                if (bOrU == 1) {

                    Intent intent = new Intent(CreateAccountMenu.this, BankLeaderSelectActivityMenu.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

                //if user is normal user

                else {

                    Intent intent = new Intent(CreateAccountMenu.this, MainActicity.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

            }
        });
    }
}
