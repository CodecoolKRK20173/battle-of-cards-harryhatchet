package com.codecool.poker;


public abstract class Player {

    public abstract int getChips();
    public abstract Hand getHand();
    
    public abstract void setHand(Hand hand);

    public abstract void resetBet();
    public abstract void resetFold();

    public abstract void postSmallBlind();
    public abstract void postBigBlind();
    public abstract int getBet();
    public abstract int makeAction();

    public abstract boolean isFold();
    public abstract int fold();

    public abstract String getName();
    public abstract void setDealer();
    public abstract void setSmallBlind();
    public abstract void setBigBlind();
    public abstract void setUTG();
    public abstract void setName(String name);
    public abstract void setHasActed(boolean hasActed);

    public abstract void addChips(int newChips);
    public abstract int changeCards();

    public abstract boolean isDealer();
    public abstract boolean isSmallBlind();
    public abstract boolean isBigBlind();
    public abstract boolean isUTG();
    public abstract boolean hasActed();

    public String roleToString() {
        if (isDealer()) {
            return "D";
        }
        return " ";
    }

    public enum Position {
        DEALER, SMALL_BLIND, BIG_BLIND, UTG;
    }

}