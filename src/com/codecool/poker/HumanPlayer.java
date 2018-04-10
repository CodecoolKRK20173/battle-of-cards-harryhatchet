package com.codecool.poker;

import Hand;

public class HumanPlayer extends Player {

    private Hand hand;
    private int chips;
    private int bet;
    private boolean isFold = false;
    private boolean isDealer = false;
    private boolean isSmallBlind = false;
    private boolean isBigBlind = false;

    public HumanPlayer(Hand hand) {
        this.hand = hand;
        this.chips = 100;
    }

    public int getChips() {
        return chips;
    }

    public int getBet() {
        return bet;
    }

    public Hand getHand() {
        return hand;
    }

    public void fold() {
        isFold = !isFold;
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

    public int placeBet(int customBet) {
        bet = 0;
        if (isSmallBlind) {
            bet = bet +1;
        } else if (isBigBlind) {
            bet = bet + 2;
        } else {
            bet = bet + customBet;
        }
        return bet;
    }

}