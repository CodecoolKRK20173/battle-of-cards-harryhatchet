package com.codecool.poker;


public abstract class Player {


    public abstract int getBet();
    public abstract boolean getFold();
    public abstract int getChips();
    public abstract Hand getHand();
    
    public abstract void setHand(Hand hand);
    public abstract void setDealer();
    public abstract void setSmallBlind();
    public abstract void setBigBlind();
    public abstract void setUTG();

    public abstract void addChips(int newChips);
    public abstract void resetBet();
    public abstract void resetFold();

    public abstract boolean isDealer();
    public abstract boolean isSmallBlind();
    public abstract boolean isBigBlind();
    public abstract boolean isUTG();

    public enum Position {
        DEALER, SMALL_BLIND, BIG_BLIND, UTG;
    }

}