package com.codecool.poker;

import java.util.Iterator;

import Hand;

public class AI extends Player {

    private Hand hand;
    private int chips = 100;
    private int bet;
    private boolean isFold = false;
    private boolean isDealer = false;
    private boolean isSmallBlind = false;
    private boolean isBigBlind = false;
    private double chanceOfWinning = 0;
    private int round = 1;

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
            this.chanceOfWinning = points / 10;
            return true;
        } else {
            return isCloseToHand();
        }
    }

    private boolean isCloseToHand() {
        boolean closeToHand = checkIfCloseToStrit();
        if (closeToHand) {
            return true;
        }

        closeToHand = checkIfCloseToFlush();
        if (closeToHand) {
            return true;
        }

        return shouldRiskAHighCard();
    }

    private boolean checkIfCloseToStrit() {
        Iterator<Integer> handIterator = hand.getRankIterator();
        int offByOne = 0;
        int previous = -1;

        while (handIterator.hasNext()) {
            int current = handIterator.next();
            if (previous + 1 == current) {
                offByOne++;
            }
            previous = current;
        }

        return offByOne == 4;
    }

    private boolean checkIfCloseToFlush() {
        Iterator<String> handIterator = hand.getSuitIterator();
        int hearths = 0;
        int diamonds = 0;
        int clubs = 0;
        int spades = 0;
        String mostCommonSuit;
        while (handIterator.hasNext()) {
            String suit = handIterator.next();
            switch (suit) {
            case "h":
                hearths++;
                mostCommonSuit = (hearths == 4 ? "h" : mostCommonSuit);
                break;
            case "d":
                diamonds++;
                mostCommonSuit = (diamonds == 4 ? "d" : mostCommonSuit);
                break;
            case "c":
                clubs++;
                mostCommonSuit = (clubs == 4 ? "c" : mostCommonSuit);
                break;
            case "s":
                spades++;
                mostCommonSuit = (spades == 4 ? "s" : mostCommonSuit);
                break;
            }
        }

        boolean closeToFlush = hearths == 4 || diamonds == 4 || clubs == 4 || spades == 4;
        if (closeToFlush) {
            int highestCardRank = getHighestCardRank(mostCommonSuit);
            chanceOfWinning = 0.24 + 0.01 * highestCardRank;
            return true;
        }
        return false;
    }

    private int getHighestCardRank(String suit) {
        int highestRank = -1;
        for (Card c : hand.getCards()) {
            if (c.getRank() > highestRank && c.getSuit().equals("suit")) {
                highestRank = c.getRank();
            }
        }
        return highestRank;
    }

    public int placeBet() {
        int bet = 0;
        if (isSmallBlind && round == 1) {
            bet = 1;
        } else if (isBigBlind && round == 1) {
            bet = 2;
        } else {
            bet = chooseBet();
        }
        round++;
        return bet;
    }
}