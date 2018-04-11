package com.codecool.poker;

import java.util.Iterator;

import Hand;

public class AI extends Player {

    private Hand hand;
    private int chips = 100;
    private int bet;
    private boolean isFold = false;
    private int round = 1;
    private Position position = Position.UTG;

    private double chanceOfWinning = 0;
    private boolean closeToFlush = false;
    private boolean closeToStrit = false;

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

    public boolean isDealer() {
        return position.equals(Position.DEALER);
    }

    public boolean isSmallBlind() {
        return position.equals(Position.SMALL_BLIND);
    }

    public boolean isBigBlind() {
        return position.equals(Position.BIG_BLIND);
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
        chanceOfWinning = 0;
        chanceOfWinning += addChanceForFlush();
        chanceOfWinning += addChanceForStrit();
        int numOfCardsToChange = getNumOfCardsToChange();
        return makeADecision();
    }

    private int addChanceForStrit() {
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

        boolean almostStrit = offByOne == 4;
        int chanceForStrit = 0;
        if (almostStrit) {
            int highestCardRank = getHighestCardRank();
            chanceForFlush += 0.17 * (0.5 + 0.01 * highestCardRank);
            closeToStrit = true;
        }
        return chanceForStrit;
    }

    private int addChanceForFlush() {
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

        boolean almostFlush = hearths == 4 || diamonds == 4 || clubs == 4 || spades == 4;
        int chanceForFlush = 0;
        if (almostFlush) {
            int highestCardRank = getHighestCardRank(mostCommonSuit);
            chanceForFlush += 0.25 * (0.6 + 0.01 * highestCardRank);
            closeToFlush = true;
        }
        return chanceForFlush;
    }

    private int getHighestCardRank(String suit) {
        int highestRank = -1;
        for (Card c : hand.getCards()) {
            if (c.getRank() > highestRank && c.getSuit().equals(suit)) {
                highestRank = c.getRank();
            }
        }
        return highestRank;
    }

    private int getHighestCardRank() {
        int highestRank = -1;
        for (Card c : hand.getCards()) {
            if (c.getRank() > highestRank) {
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