package com.codecool.poker;

import Hand;

public abstract class Player {

    public abstract boolean getFold();
    public abstract int getChips();
    public abstract void setChips(int number);
    public abstract int getBet();
    public abstract Hand getHand();
    public abstract void takeAction();
    public abstract boolean placeBet();
    public abstract void unFold();
    public abstract void switchDealer();

}