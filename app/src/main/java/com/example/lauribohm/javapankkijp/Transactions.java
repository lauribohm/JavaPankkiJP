package com.example.lauribohm.javapankkijp;

//transactions are specified by account number
//note is the whole transaction text which is added example making a payment

public class Transactions {

    private String aNbr, note;

    public Transactions (String nbr, String n) {

        aNbr = nbr;
        note = n;
    }

    public String getNote() {
        return note;
    }

    public String getaNbr() {
        return aNbr;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setaNbr(String aNbr) {
        this.aNbr = aNbr;
    }
}
