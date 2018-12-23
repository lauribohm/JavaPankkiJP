package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

// for login

public class LogIn extends AppCompatActivity {

    Button login;
    Button create;

    EditText usersnm;
    EditText pass;

    TextView message;

    //Gives this class access to Bank class

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        login = (Button) findViewById(R.id.login);
        create = (Button) findViewById(R.id.createauser);

        usersnm = (EditText) findViewById(R.id.un);
        pass = (EditText) findViewById(R.id.pa);

        message = (TextView) findViewById(R.id.message);

        //will transfer user to creating a user information

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LogIn.this, LogInCreateUser.class);
                startActivity(intent);
            }
        });

        //checks if user is bankleader
        //if not, it will check if username and password are correct
        //if not correct error message will appear

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usersnm.getText().toString().equals("Berlin")) {

                    if (pass.getText().toString().equals("b")) {

                        Intent intent = new Intent(LogIn.this, BankLeaderSelectUserMenu.class);
                        intent.putExtra("bankOrUser", 1);
                        startActivity(intent);
                    }
                }

                else {

                    for (int j = 0; j < bank.userPointer.size(); j++) {

                        if (usersnm.getText().toString().equals(bank.userPointer.get(j).getUserName())) {

                            if (pass.getText().toString().equals(bank.userPointer.get(j).getPassword())) {

                                Intent intent = new Intent(LogIn.this, MainActicity.class);
                                String usersname = usersnm.getText().toString();

                                //will pass forward username and this "bankOrUser" which tells if user really is normal or bankleader
                                //this is crucial for programs correct functioning

                                intent.putExtra("usersname", usersname);
                                intent.putExtra("bankOrUser", 0);
                                startActivity(intent);
                                System.out.println(usersnm.getText().toString());
                            }

                            else {
                                message.setText("Käyttäjätunnus tai salasana väärin.");
                            }
                        }

                        else {

                            message.setText("Käyttäjätunnus tai salasana väärin.");
                        }

                    }
                }
            }
        });
    }
}
