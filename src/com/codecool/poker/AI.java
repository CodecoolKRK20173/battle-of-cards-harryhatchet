package com.codecool.poker;

import Hand;

public class AI extends Player {

    private Hand hand;
    private int chips = 100;
    private int bet;
    private boolean isFold = false;
    private boolean isDealer = false;
    private boolean isSmallBlind = false;
    private boolean isBigBlind = false;

    public int getChips() {
        return chips;
    }

    public int getBet() {
        return bet;
    }

    public Hand getHand() {
        return hand;
    }

    public void addChips(int newChips) {
        this.chips += newChips;
    }

    public void fold() {
        isFold = !isFold;
    }

    public void setDealer() {
        isDealer = true;
        isSmallBlind = false;
        isBigBlind = false;
    }

    public void setSmallBlind() {
        isDealer = true;
        isSmallBlind = false;
        isBigBlind = false;
    }

    public void setBigBlind() {
        isDealer = true;
        isSmallBlind = false;
        isBigBlind = false;
    }

    public void setUTG() {
        isDealer = false;
        isSmallBlind = false;
        isBigBlind = false;
    }

    public boolean isCurrentDealer() {
        return isDealer;
    }

    public boolean isCurrentSmallBlind() {
        return isSmallBlind;
    }

    public boolean isCurrentBigBlind() {
        return isBigBlind;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void resetBet() {
        this.bet = 0;
    }

    public void resetFold() {
        this.isFold = false;
    }

    public void takeAction() {
        if (isFold) {
            return;
        }

        boolean shouldFold = checkIfShouldFold();
        if (shouldFold) {
            fold();
        } else {
            placeBet();
        }
    }

    private boolean checkIfShouldFold() {
        int points = hand.getHandPoints();

        if (points > 0) {
            return true;
        } else {
            return isCloseToHand();
        }
    }

    public int placeBet() {
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