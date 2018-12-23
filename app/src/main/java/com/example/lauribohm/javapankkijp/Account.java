package com.example.lauribohm.javapankkijp;

// has accounts and cards informations

public class Account {

    private double money, payLimit, takeLimit;
    private String owner, acctNbr, acctMode, card, region, frozen, dead;

    public Account (String o, String mode, String nbr, double m, double limit, String r, String c, double t, String f, String d) {

        owner = o;              // account and card info
        acctMode = mode;        // account info
        acctNbr = nbr;          // account info
        money = m;              // account info
        payLimit = limit;       // card info
        region = r;             // card info
        card = c;               // card info
        takeLimit = t;          // card info
        frozen = f;             // account info
        dead = d;               // card info
    }

    public String getOwner() {
        return owner;
    }

    public String getAcctMode() {
        return acctMode;
    }

    public String getAcctNbr() {
        return acctNbr;
    }

    public double getMoney() {
        return money;
    }

    public double getPayLimit() {
        return payLimit;
    }

    public String getRegion() {
        return region;
    }

    public String getCard() {
        return card;
    }

    public double getTakeLimit() {
        return takeLimit;
    }

    public String getFrozen() {
        return frozen;
    }

    public String getDead() {
        return dead;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setPayLimit(double payLimit) {
        this.payLimit = payLimit;
    }

    public void setTakeLimit(double takeLimit) {
        this.takeLimit = takeLimit;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }
}
