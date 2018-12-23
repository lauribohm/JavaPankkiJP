package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordChange extends AppCompatActivity {

    EditText oldPass;
    EditText newPass;
    EditText newPass2;

    Button save;
    Button goBack;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();

    int chosenUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        oldPass = (EditText) findViewById(R.id.editText);
        newPass = (EditText) findViewById(R.id.editText3);
        newPass2 = (EditText) findViewById(R.id.editText2);

        save = (Button) findViewById(R.id.button20);
        goBack = (Button) findViewById(R.id.button21);

        //Catches who is logged in

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        //Catches right array to users

        for (int i = 0; i < bank.userPointer.size(); i++) {

            if (usernm.equals(bank.userPointer.get(i).getUserName())) {

                chosenUser = i;
            }
        }

        //checks if old password is correct and new1 and new2 are the same
        //then sets new password to users info

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldPass.getText().toString().equals(bank.userPointer.get(chosenUser).getPassword())) {

                    if (newPass.getText().toString().equals(newPass2.getText().toString())) {

                        bank.userPointer.get(chosenUser).setPassword(newPass.getText().toString());

                        Toast.makeText(PasswordChange.this, "Salasana vaihsettu onnistuneesti",Toast.LENGTH_SHORT).show();
                    }

                    //error message

                    else {

                        Toast.makeText(PasswordChange.this, "Uudet salasanat eivät täsmää",Toast.LENGTH_SHORT).show();
                    }
                }

                //error message

                else {

                    Toast.makeText(PasswordChange.this, "Vanha salasana väärin",Toast.LENGTH_SHORT).show();
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user is bankLeader

                if (bOrU == 1) {

                    Intent intent = new Intent(PasswordChange.this, BankLeaderSelectActivityMenu.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

                //if user is normal user

                else {

                    Intent intent = new Intent(PasswordChange.this, MainActicity.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

            }
        });
    }
}
