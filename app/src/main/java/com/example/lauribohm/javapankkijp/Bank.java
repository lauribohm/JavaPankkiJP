package com.example.lauribohm.javapankkijp;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//All the spinners are here which other activities are using because the same spinners are needed many times

public class Bank {

    private static Bank bank = new Bank();

    public static Bank getInstance() {
        return bank;
    }

    ArrayList<Users> userPointer = new ArrayList<>();
    ArrayList<Account> acctPointer = new ArrayList<>();
    ArrayList<Transactions> transPointer = new ArrayList<>();

    int chosenListNbr;
    int chosenCardArray = 0;
    String regionChosen;
    String accountTypeChosen;

    //Spinner for searching/selecting accounts

    public int spinnerForAccts (String usernm, Spinner spinner, Context context) {

        final List<String> accts = new ArrayList<>();

        accts.add("");

        for (int i = 0; i < acctPointer.size(); i++){

            if (usernm.equals(acctPointer.get(i).getOwner())) {
                accts.add(acctPointer.get(i).getAcctNbr());
            }
        }
        ArrayAdapter<String> Accountsei = new ArrayAdapter(context,android.R.layout.simple_spinner_item, accts);

        Accountsei.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(Accountsei);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("")) {
                }
                else {
                    String accountChosen = parent.getItemAtPosition(position).toString();

                    for (int i = 0; i < acctPointer.size(); i++) {

                        if (accountChosen.equals(acctPointer.get(i).getAcctNbr())){
                            chosenListNbr = i;
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //returns array which account is been selscted

        return chosenListNbr;
    }

    //Spinner for searching/selecting cards
    //cards are selected by the account nummber
    //so this also checks if account has card
    //and if it does not, then spinner will not show those accounts

    public int spinnerForCards (String usernm, Spinner spinner, Context context) {

        final List<String> cards = new ArrayList<>();

        for (int i = 0; i < acctPointer.size(); i++){

            if (usernm.equals(acctPointer.get(i).getOwner())) {

                if (acctPointer.get(i).getCard().equals("true")) {

                    cards.add(acctPointer.get(i).getAcctNbr());
                }
            }
        }
        ArrayAdapter<String> Cards = new ArrayAdapter(context,android.R.layout.simple_spinner_item, cards);

        Cards.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(Cards);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("")) {
                }
                else {
                    String chosenCard = parent.getItemAtPosition(position).toString();

                    for (int i = 0; i < acctPointer.size(); i++) {

                        if (chosenCard.equals(acctPointer.get(i).getAcctNbr())) {
                            chosenCardArray = i;
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //returns array to card(account) which card(account) has been selected

        return chosenCardArray;
    }

    //Spinner for different useregions for card

    public String spinnerForRegion (Spinner spinner, Context context) {

        final List<String> Regions = new ArrayList<>();

        Regions.add("Kotimaa");
        Regions.add("Pohjoismaat");
        Regions.add("Eurooppa");
        Regions.add("Koko maailma");

        ArrayAdapter<String> DifRegions = new ArrayAdapter(context,android.R.layout.simple_spinner_item, Regions);

        DifRegions.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(DifRegions);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("")) {

                }
                else {

                    regionChosen = parent.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (regionChosen == null) {
            return "";
        }

        //returns which region has been selected

        else {
            return regionChosen;
        }
    }

    //spinner for different types of account

    public String spinnerForAccountType(Spinner spinner, Context context) {

        final List<String> accts = new ArrayList<>();

        accts.add("");
        accts.add("Normaalitili");
        accts.add("Säästötili");

        ArrayAdapter<String> Accountsei = new ArrayAdapter(context,android.R.layout.simple_spinner_item, accts);

        Accountsei.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(Accountsei);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals(null)) {

                }
                else {

                    accountTypeChosen = parent.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        System.out.println("mikä valittu: " + accountTypeChosen);

        if (accountTypeChosen == null) {
            return "";
        }

        //returns which type has been selected

        else {
            return accountTypeChosen;
        }
    }

}
