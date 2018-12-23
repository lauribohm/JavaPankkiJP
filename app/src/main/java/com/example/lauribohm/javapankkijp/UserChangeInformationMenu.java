
package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//for changing users info

public class UserChangeInformationMenu extends AppCompatActivity {

    EditText newName;
    EditText newAddress;
    EditText newCity;

    TextView oldName;
    TextView oldAddress;
    TextView oldCity;

    Button save;
    Button goBack;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_information_menu);

        newName = (EditText) findViewById(R.id.newN);
        newAddress =(EditText) findViewById(R.id.newA);
        newCity = (EditText) findViewById(R.id.newC);

        oldName = (TextView) findViewById(R.id.oldN);
        oldAddress = (TextView) findViewById(R.id.oldA);
        oldCity = (TextView) findViewById(R.id.oldC);

        save = (Button) findViewById(R.id.button5);
        goBack = (Button) findViewById(R.id.button19);

        //Catches who is logged in

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

       //checks right user and prints info

        int k = -1;

        for (int i = 0; i < bank.userPointer.size(); i++) {

            if (usernm.equals(bank.userPointer.get(i).getUserName())) {

                k = i;

                oldName.setText(bank.userPointer.get(k).getName().toString());
                oldAddress.setText(bank.userPointer.get(k).getAddress().toString());
                oldCity.setText(bank.userPointer.get(k).getCity().toString());
            }
        }

        final int chosenArray = k;

        //checks which info is new and saves them

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!newName.getText().toString().equals("")) {
                    bank.userPointer.get(chosenArray).setName(newName.getText().toString());
                    oldName.setText(bank.userPointer.get(chosenArray).getName().toString());
                }

                if (!newAddress.getText().toString().equals("")) {
                    bank.userPointer.get(chosenArray).setAddress(newAddress.getText().toString());
                    oldAddress.setText(bank.userPointer.get(chosenArray).getAddress().toString());
                }

                if (!newCity.getText().toString().equals("")) {
                    bank.userPointer.get(chosenArray).setCity(newCity.getText().toString());
                    oldCity.setText(bank.userPointer.get(chosenArray).getCity().toString());
                }

                Toast.makeText(UserChangeInformationMenu.this, "Tallennus onnistui",Toast.LENGTH_SHORT).show();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bOrU == 1) {

                    Intent intent = new Intent(UserChangeInformationMenu.this, BankLeaderSelectActivityMenu.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

                else {

                    Intent intent = new Intent(UserChangeInformationMenu.this, MainActicity.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

            }
        });
    }
}
