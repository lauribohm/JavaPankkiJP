package com.example.lauribohm.javapankkijp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

//main activity, controls everything is normal user is being logged in

public class MainActicity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String usernm;
    int bOrU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_acticity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Catches who is logged in and will pass this info around programm

        usernm = (String)getIntent().getSerializableExtra("usersname");
        bOrU = (int)getIntent().getSerializableExtra("bankOrUser");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_acticity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.acctCr) { //VALMIS

            System.out.println("### " + usernm + " ###");
            Intent intent = new Intent(MainActicity.this, CreateAccountMenu.class);
            intent.putExtra("usersname", usernm);
            intent.putExtra("bankOrUser", bOrU);
            startActivity(intent);
        }
        else if (id == R.id.acctPa) { //VALMIS

            Intent intent = new Intent(MainActicity.this, AccountPayMenu.class);
            intent.putExtra("usersname", usernm);
            intent.putExtra("bankOrUser", bOrU);
            startActivity(intent);

        } else if (id == R.id.acctDe) { //VALMIS

            Intent intent = new Intent(MainActicity.this, AccountSaveMoney.class);
            intent.putExtra("usersname", usernm);
            intent.putExtra("bankOrUser", bOrU);
            startActivity(intent);

        } else if (id == R.id.cardCr) { //VALMIS

            Intent intent = new Intent(MainActicity.this, CreateCardMenu.class);
            intent.putExtra("usersname", usernm);
            intent.putExtra("bankOrUser", bOrU);
            startActivity(intent);

        } else if (id == R.id.cardPa) { //VALMIS

            Intent intent = new Intent(MainActicity.this, CardPayMenu.class);
            intent.putExtra("usersname", usernm);
            intent.putExtra("bankOrUser", bOrU);
            startActivity(intent);

        } else if (id == R.id.cardCh) { //VALMIS

            System.out.println(usernm);
            Intent intent = new Intent(MainActicity.this, CardChangeMenu.class);
            intent.putExtra("usersname", usernm);
            intent.putExtra("bankOrUser", bOrU);
            startActivity(intent);

        } else if (id == R.id.Trans) { //VALMIS

            System.out.println(usernm);
            Intent intent = new Intent(MainActicity.this, TransactionMenu.class);
            intent.putExtra("usersname", usernm);
            intent.putExtra("bankOrUser", bOrU);
            startActivity(intent);

        } else if (id == R.id.userSe) { //VALMIS

            System.out.println(usernm);
            Intent intent = new Intent(MainActicity.this, UserChangeInformationMenu.class);
            intent.putExtra("usersname", usernm);
            intent.putExtra("bankOrUser", bOrU);
            startActivity(intent);

        } else if (id == R.id.passCH) { //VALMIS

            System.out.println(usernm);
            Intent intent = new Intent(MainActicity.this, PasswordChange.class);
            intent.putExtra("usersname", usernm);
            intent.putExtra("bankOrUser", bOrU);
            startActivity(intent);

        } else if (id == R.id.logOut) { //VALMIS

            Intent intent = new Intent(MainActicity.this, LogIn.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
