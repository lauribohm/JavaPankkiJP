package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CardPayMenu extends AppCompatActivity {

    TextView payL;
    TextView takeL;
    TextView region;

    EditText sum1;
    EditText sum2;

    Button pay;
    Button take;
    Button goBack;

    Spinner spinner;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();

    int chosenCardArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_pay_menu);

        payL = (TextView) findViewById(R.id.textView59);
        takeL = (TextView) findViewById(R.id.textView58);
        region = (TextView) findViewById(R.id.textView62);

        sum1 = (EditText) findViewById(R.id.cash1);
        sum2 = (EditText) findViewById(R.id.cash2);

        pay = (Button) findViewById(R.id.button26);
        take = (Button) findViewById(R.id.button24);
        goBack = (Button) findViewById(R.id.button25);

        spinner = findViewById(R.id.spinner);

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
            Toast.makeText(CardPayMenu.this, "Sinulla ei ole pankkikortteja, palaa luomaan kortti", Toast.LENGTH_SHORT).show();
        }

        //otherwise add accounts which have card to spinner
        //and also run spinner for regions

        else {

            bank.spinnerForCards(usernm, spinner, this);
        }

        //shows cards old information when clicked

        sum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chosenCardArray = bank.spinnerForCards(usernm, spinner, CardPayMenu.this);

                region.setText(bank.acctPointer.get(chosenCardArray).getRegion());

                String PL = String.valueOf(bank.acctPointer.get(chosenCardArray).getPayLimit());
                String TL = String.valueOf(bank.acctPointer.get(chosenCardArray).getTakeLimit());

                payL.setText(PL);
                takeL.setText(TL);
            }
        });

        //checks if there is enough money and given sum is not too big
        //also adds new transaction

        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bank.acctPointer.get(chosenCardArray).getDead().equals("false")){

                    double limitT =  Double.parseDouble(sum1.getText().toString());
                    if (limitT <= bank.acctPointer.get(chosenCardArray).getTakeLimit() && limitT <= bank.acctPointer.get(chosenCardArray).getMoney()) {

                        //sets new money

                        double newM = bank.acctPointer.get(chosenCardArray).getMoney()-limitT;
                        bank.acctPointer.get(chosenCardArray).setMoney(newM);

                        //adding new transaction

                        bank.transPointer.add(new Transactions(bank.acctPointer.get(chosenCardArray).getAcctNbr(),
                                "Korttilta nostettiin rahaa, arvo: " + limitT));

                        Toast.makeText(CardPayMenu.this, "Nosto suoritettiin onnistuneesti", Toast.LENGTH_SHORT).show();
                    }

                    //error message

                    else {

                        Toast.makeText(CardPayMenu.this, "Nostoa ei voitu suorittaa, nostoraja on: "
                                + bank.acctPointer.get(chosenCardArray).getTakeLimit(), Toast.LENGTH_SHORT).show();
                    }

                }

                else {

                    Toast.makeText(CardPayMenu.this, "Kortti on kuoletettu", Toast.LENGTH_SHORT).show();

                }

            }
        });

        //checks if there is enough money and the given sum does not exceed the limits
        //also adds new transaction

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //there cant be already a card

                if (bank.acctPointer.get(chosenCardArray).getDead().equals("false")){

                    double limitP =  Double.parseDouble(sum2.getText().toString());

                    if (limitP <= bank.acctPointer.get(chosenCardArray).getPayLimit() && limitP <= bank.acctPointer.get(chosenCardArray).getMoney()) {

                        //sets new money

                        double newM = bank.acctPointer.get(chosenCardArray).getMoney()-limitP;
                        bank.acctPointer.get(chosenCardArray).setMoney(newM);

                        //adds transaction

                        bank.transPointer.add(new Transactions(bank.acctPointer.get(chosenCardArray).getAcctNbr(),
                                "Korttimaksu suoritettu, arvo: " + limitP));

                        Toast.makeText(CardPayMenu.this, "Korttimaksu suoritettu onnistuneesti", Toast.LENGTH_SHORT).show();
                    }

                    //error message

                    else {

                        Toast.makeText(CardPayMenu.this, "Maksua ei voitu suorittaa, maksuraja on: "
                                + bank.acctPointer.get(chosenCardArray).getPayLimit(), Toast.LENGTH_SHORT).show();
                    }

                }

                else {

                    Toast.makeText(CardPayMenu.this, "Kortti on kuoletettu", Toast.LENGTH_SHORT).show();

                }


            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user is bankLeader

                if (bOrU == 1) {

                    Intent intent = new Intent(CardPayMenu.this, BankLeaderSelectActivityMenu.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }

                //if user is normal user

                else {

                    Intent intent = new Intent(CardPayMenu.this, MainActicity.class);
                    intent.putExtra("usersname", usernm);
                    intent.putExtra("bankOrUser", bOrU);
                    startActivity(intent);
                }
            }
        });


    }
}
