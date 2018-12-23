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

//for changing card information

public class CardChangeMenu extends AppCompatActivity {

    TextView oldTakeLimit;
    TextView oldPayLimit;
    TextView oldCardRegion;

    TextView info;

    EditText newTakeLimit;
    EditText newPayLimit;

    Button save;
    Button goBack;

    Spinner spinnerRegion;
    Spinner spinnerCardAccts;

    Bank bank = Bank.getInstance();

    String regionChosen = null;
    int chosenCardArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_change_menu);

        oldTakeLimit = (TextView) findViewById(R.id.oldTakeL);
        oldPayLimit = (TextView) findViewById(R.id.oldPayL);
        oldCardRegion = (TextView) findViewById(R.id.oldRegion);
        info = (TextView) findViewById(R.id.textView51);

        newTakeLimit = (EditText) findViewById(R.id.newTakeL);
        newPayLimit = (EditText) findViewById(R.id.newPayL);

        save = (Button) findViewById(R.id.button4);
        goBack = (Button) findViewById(R.id.button18);

        spinnerRegion = findViewById(R.id.spinnerNewR);
        spinnerCardAccts = findViewById(R.id.spinner2);

        //Catches who is logged in

        final String usernm = (String)getIntent().getSerializableExtra("usersname");
        final int bOrU = (int)getIntent().getSerializableExtra("bankOrUser");

        //Checks if there is any cards created for current user

        int counter = 0;

        for (int i = 0; i < bank.acctPointer.size(); i++) {

            if (bank.acctPointer.get(i).getCard().equals("true")) {
                counter = counter+1;
            }
        }

        //if there is not will give error message

        if (counter == 0) {
            Toast.makeText(CardChangeMenu.this, "Sinulla ei ole pankkikortteja, palaa luomaan kortti", Toast.LENGTH_SHORT).show();
            info.setText("Sinulla ei ole pankkikortteja, palaa luopmaan kortti");
        }

        //otherwise add accounts which have card to spinner
        //and also run spinner for regions

        else {

            bank.spinnerForCards(usernm, spinnerCardAccts, this);
            bank.spinnerForRegion(spinnerRegion, this);
        }

        //shows cards old information when clicked

        newPayLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chosenCardArray = bank.spinnerForCards(usernm, spinnerCardAccts, CardChangeMenu.this);

                oldCardRegion.setText(bank.acctPointer.get(chosenCardArray).getRegion());

                String PL = String.valueOf(bank.acctPointer.get(chosenCardArray).getPayLimit());
                String TL = String.valueOf(bank.acctPointer.get(chosenCardArray).getTakeLimit());

                oldPayLimit.setText(PL);
                oldTakeLimit.setText(TL);
            }
        });

        //Check which information has been changed and saves all the new

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regionChosen = bank.spinnerForRegion(spinnerRegion, CardChangeMenu.this);

                if (!newTakeLimit.getText().toString().equals("")){
                    double limitT =  Double.parseDouble(newTakeLimit.getText().toString());
                    bank.acctPointer.get(chosenCardArray).setTakeLimit(limitT);

                    String TL = String.valueOf(bank.acctPointer.get(chosenCardArray).getTakeLimit());
                    oldTakeLimit.setText(TL);
                }

                if (!newPayLimit.getText().toString().equals("")) {
                    double limitP =  Double.parseDouble(newPayLimit.getText().toString());
                    bank.acctPointer.get(chosenCardArray).setPayLimit(limitP);

                    String PL = String.valueOf(bank.acctPointer.get(chosenCardArray).getPayLimit());
                    oldPayLimit.setText(PL);
                }

                if (regionChosen != "") {
                    bank.acctPointer.get(chosenCardArray).setRegion(regionChosen);
                    oldCardRegion.setText(bank.acctPointer.get(chosenCardArray).getRegion());
                }

                Toast.makeText(CardChangeMenu.this, "Kortin tiedot päivitetty", Toast.LENGTH_SHORT).show();
                info.setText("Kortin tiedot päivitetty");
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user is bankLeader

                if (bOrU == 1) {

                    Intent intent = new Intent(CardChangeMenu.this, BankLeaderSelectActivityMenu.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

                //if user is normal user

                else {

                    Intent intent = new Intent(CardChangeMenu.this, MainActicity.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }
            }
        });
    }
}
