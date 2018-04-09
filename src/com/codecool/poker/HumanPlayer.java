package com.codecool.poker;

import Hand;

public class HumanPlayer extends Player {

    private Hand hand;
    private int chips;
    private int bet;
    private boolean isFold;
    private boolean isDealer = false;
    private boolean isSmallBlind = false;
    private boolean isBigBlind = false;

    public HumanPlayer(Hand hand, int bet) {
        this.hand = hand;
        this.bet = bet;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public int getBet() {
        return bet;
    }

    public Hand getHand() {
        return hand;
    }

    public void unFold() {
        isFold = !isFold;
    }

    public boolean getFold() {
        if (isFold) {
            return true;
        }
        return false;
    }

    public void takeAction(int bet) {

    }

    public void switchDealer() {
        isDealer = !isDealer;
    }

    public void switchSmallBlind() {
        isSmallBlind = !isSmallBlind;
    }

    public void switchBigBlind() {
        isBigBlind = !isBigBlind;
    }

}