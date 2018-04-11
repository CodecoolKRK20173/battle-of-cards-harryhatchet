package com.codecool.poker;

import Hand;

public abstract class Player {

    public abstract boolean getFold();
    public abstract int getChips();
    public abstract int getBet();
    public abstract Hand getHand();
    public abstract void takeAction();
    public abstract boolean placeBet();
    public abstract void fold();
    public abstract void switchDealer();
    public abstract void switchSmallBlind();
    public abstract void switchBigBlind();

    public enum Position {
        DEALER, SMALL_BLIND, BIG_BLIND, UTG;
    }

}