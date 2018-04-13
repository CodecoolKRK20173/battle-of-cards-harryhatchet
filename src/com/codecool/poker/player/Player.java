package com.codecool.poker.player;


import com.codecool.poker.table.Table;
import com.codecool.poker.hand.Hand;
import com.codecool.poker.card.*;


public abstract class Player {
    public abstract void postSmallBlind();
    public abstract void postBigBlind();
    public abstract int makeAction();
    public abstract void resetFold();
    public abstract int changeCards();

    public abstract int getChips();
    public abstract int getBet();
    public abstract Hand getHand();
    public abstract void addChips(int newChips);
    public abstract int fold();
    public abstract boolean isFold();
    public abstract void setDealer();
    public abstract void setSmallBlind();
    public abstract void setBigBlind();
    public abstract void setUTG();
    public abstract boolean isDealer();
    public abstract boolean isSmallBlind();
    public abstract boolean isBigBlind();
    public abstract boolean isUTG();
    public abstract boolean hasActed();
    public abstract void setHasActed(boolean hasActed);
    public abstract void setHand(Hand hand);
    public abstract void resetBet();
    public abstract String getName();
    public abstract void setName(String name);

    public String toString() {
        StringBuilder output = new StringBuilder();
        if (isSmallBlind()) {
            output.append("SB: ");
        }
        else if (isBigBlind()) {
            output.append("BB: ");
        }
        else if (isDealer()) {
            output.append("D: ");
        }
        else if (isUTG()) {
            output.append("UTG: ");
        }
        output.append(getHand().toString());
        output.append("\tstack: ");
        output.append(getChips());
        return output.toString();
    }

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