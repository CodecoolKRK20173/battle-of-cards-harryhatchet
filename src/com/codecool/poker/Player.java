package com.codecool.poker;


public abstract class Player {
    private Hand hand;
    private boolean hasActed;
    private int chips;
    private int bet;
    private boolean isFold;
    private Position position;
    private String name;

    public abstract void postSmallBlind();
    public abstract void postBigBlind();
    public abstract int makeAction();
    public abstract void resetFold();
    public abstract int changeCards();
    
    public int getChips() {
        return chips;
    }
    public Hand getHand() {
        return hand;
    }
    
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void resetBet() {
        this.bet = 0;
    }

    public int getBet() {
        return bet;
    }

    public int fold() {
        this.isFold = true;
        return 0;
    }

    public boolean isFold() {
        return this.isFold;
    }

    public void setDealer() {
        this.position = Position.DEALER;
    }

    public void setSmallBlind() {
        this.position = Position.SMALL_BLIND;
    }

    public void setBigBlind() {
        this.position = Position.BIG_BLIND;
    }

    public void setUTG() {
        this.position = Position.UTG;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public boolean hasActed() {
        return this.hasActed;
    }

    public void setHasActed(boolean hasActed) {
        this.hasActed = hasActed;
    }

    public void addChips(int newChips) {
        this.chips += newChips;
    }

    public boolean isDealer() {
        return position.equals(Position.DEALER);
    }

    public boolean isSmallBlind() {
        return position.equals(Position.SMALL_BLIND);
    }

    public boolean isBigBlind() {
        return position.equals(Position.BIG_BLIND);
    }

    public boolean isUTG() {
        return position.equals(Position.UTG);
    }

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