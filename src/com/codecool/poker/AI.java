package com.codecool.poker;

import java.util.Iterator;
import java.util.Random;

public class AI extends Player {

    private Hand hand;
    private int chips = 100;
    private int bet;
    private boolean isFold = false;
    private int round = 1;
    private Position position = Position.UTG;
    private Table table;

    private double chanceOfWinning = 0;
    private boolean closeToFlush = false;
    private boolean closeToStrit = false;
    private boolean bluff = false;
    private int numberOfCardsToChange = 0;
    private boolean allIn = false;

    public AI(Table table) {
        this.table = table;
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
        if (isFold || allIn) {
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
            return false;
        } else {
            return ifShouldNotPlay();
        }
    }

    private boolean ifShouldNotPlay() {
        chanceOfWinning = 0;
        chanceOfWinning += addChanceForFlush();
        chanceOfWinning += addChanceForStrit();
        chanceOfWinning += chanceForGoodDraw();
        boolean wantsToPlay =  makeDecision();
        return !wantsToPlay;
    }

    private double addChanceForStrit() {
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
        double chanceForStrit = 0;
        if (almostStrit) {
            int highestCardRank = getHighestCardRank();
            chanceForFlush += 0.17 * (0.5 + 0.01 * highestCardRank);
            closeToStrit = true;
        }
        return chanceForStrit;
    }

    private double addChanceForFlush() {
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
        double chanceForFlush = 0;
        if (almostFlush) {
            int highestCardRank = getHighestCardRank(mostCommonSuit);
            chanceForFlush += 0.25 * (0.6 + 0.01 * highestCardRank);
            closeToFlush = true;
        }
        return chanceForFlush;
    }

    private double chanceForGoodDraw() {

        int numOfCards = 0;

        if (closeToFlush || closeToStrit) {
            numOfCards = 1;
        } else {
            int highestCardRank = getHighestCardRank();
            if (highestCardRank > 12) {
                numOfCards = 4;
            } else {
                numOfCards = 5;
            }
        }

        numberOfCardsToChange = numOfCards;

        // 1 - chanceForFail gives us chance for success
        // 0.1 is chance of winning for single pair 
        return ((1 - chanceForFail(numOfCards)) * 0.1);
    }

    private double chanceForFail(int numOfCardsToChange) {
        if (numOfCardsToChange == 0) {
            return 1.0;
        }
        double percentChance = (47-((5 - numOfCardsToChange)*3))/47;
        return percentChance * chanceForFail(numOfCardsToChange - 1);
    }

    private boolean makeDecision() {

        // AI goes in if has more than 20% chance of winning
        if (chanceOfWinning > 0.2) {
            return true;
        }

        Random generator = new Random();
        
        // AI goes in no matter how bad cards it has once in 50 takes
        if (generator.nextInt(101) <= 2) {
            bluff = true;
            return true;
        }

        // AI goes bluffs if it has chanceOfWinning < 5% once in 50 takes
        if (chanceOfWinning < 0.05 && generator.nextInt(101) <= 2) {
            bluff = true;
            return true;
        }

        // AI randomly chooses to fold if chanceOfWinning is to small
        // (lower than random double in range <0, 0.25>)
        if (generator.nextDouble() / 4 > chanceOfWinning) {
            return false;
        }

        int highestBet = 50; //table.getMaxBet(); #Not yet implemented

        // AI goes in if it plays for less than (chanceOfWinning + random<0, 0.25>) percent of its chips
        return (highestBet / (chips + bet) < generator.nextDouble() / 4 + chanceOfWinning);
    }

    private int getHighestCardRank(String suit) {
        int highestRank = -1;
        for (Card c : hand.getCards()) {
            if (c.getRank().getCardStrength() > highestRank && c.getSuit().equals(suit)) {
                highestRank = c.getRank();
            }
        }
        return highestRank;
    }

    private int getHighestCardRank() {
        int highestRank = -1;
        for (Card c : hand.getCards()) {
            if (c.getRank().getCardStrength()  > highestRank) {
                highestRank = c.getRank();
            }
        }
        return highestRank;
    }

    public void placeBet() {
        int bet = 0;
        if (isSmallBlind() && round == 1) {
            bet = 1;
        } else if (isBigBlind() && round == 1) {
            bet = 2;
        } else {
            bet = chooseBet();
        }
        round++;
        this.chips -= bet;
        this.bet += bet;
        this.allIn = chips == 0;
    }

    private int chooseBet() {
        int bet;
        int highestBet = 50; //table.getMaxBet(); #Not yet implemented
        Random generator = new Random();
        if (bluff) {
            bet = raise();
        } else {
            if (highestBet == 0 && chanceOfWinning < 0.1) {
                bet = 0;
            } else if (highestBet / (chips + bet) < (chips + bet) * chanceOfWinning) {
                bet = raise((int)((chips + bet) * chanceOfWinning));
            } else {
                bet = call();
            }
        }
        return bet;
    }

    private int raise() {
        // Go all IN
        return this.chips - this.bet;
    }

    private int raise(int goalNumOfChips) {

        Random generator = new Random();
        int minRaisedBet = table.getMaxBet() + table.getDiff();
        if (minRaisedBet > this.chips + this.bet) {
            return this.chips;
        } else {
            int increasedBet = goalNumOfChips + generator.nextInt(10);
            if (increasedBet > this.chips + this.bet) {
                return goalNumOfChips - this.chips;
            } else {
                return increasedBet - this.chips;
            }
        }
    }

    private int call() {
        int maxBet = table.getMaxBet();
        if (maxBet < this.chips + this.bet) {
            return maxBet - this.bet;
        } else {
            return this.chips;
        }
    }
}