package com.codecool.poker;


public abstract class Player {

    public abstract boolean getFold();
    public abstract int getChips();
    public abstract void addChips(int newChips);
    public abstract Hand getHand();
    public abstract void setHand(Hand hand);
    public abstract void resetBet();
    public abstract void resetFold();
    public abstract int getBet();
    public abstract boolean placeBet();
    public abstract void fold();
    public abstract void setDealer();
    public abstract void setSmallBlind();
    public abstract void setBigBlind();
    public abstract void setUTG();
    public abstract boolean getCurrentDealer();
    public abstract boolean getCurrentSmallBlind();
    public abstract boolean getCurrentBigBlind();
    public abstract boolean getCurrentUTG();

}