package com.codecool.poker;

import java.util.Iterator;
import java.util.Random;

public class AI extends Player {

    private Hand hand;
    private int chips = 100;
    private int bet = 0;
    private boolean fold = false;
    private Position position = Position.UTG;
    private Table table;
    private String name;

    private boolean hasActed;
    private double chanceOfWinning = 0;
    private boolean closeToFlush = false;
    private boolean closeToStrit = false;
    private boolean bluff = false;
    private int numberOfCardsToChange = -1;
    private boolean allIn = false;
    private Suit mostCommonSuit = Suit.CLUBS; // Implementation requires a default Suit.
    private int rankOfCardOutOfOrder = -1;

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

    public int fold() {
        this.fold = true;
        return 0;
    }

    public boolean isFold() {
        return this.fold;
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

    public boolean hasActed() {
        return this.hasActed;
    }

    public void setHasActed(boolean hasActed) {
        this.hasActed = hasActed;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void resetBet() {
        this.bet = 0;
    }

    public void resetFold() {
        this.fold = false;
    }

    public int makeAction() {
        if (fold || allIn || hasActed) {
            this.hasActed = true;
            System.out.println("Fold: " + fold + " | allIn: " + allIn + " | hasActed: " + hasActed);
            return 0;
        }

        System.out.println("~~Action of: " + this.name + " ~~");
        this.hasActed = true;

        boolean shouldFold = checkIfShouldFold();
        if (shouldFold) {
            System.out.println("ShouldFold!");
            fold();
            return 0;
        } else {
            return placeBet();
        }
    }

    private boolean checkIfShouldFold() {
        int points = hand.getHandPoints().getPoints();

        if (points > 1) {
            System.out.println("Has a lot of points!: " + points);
            this.chanceOfWinning = points / 10.0;
            return false;
        } else {
            return ifShouldNotPlay();
        }
    }

    private boolean ifShouldNotPlay() {
        System.out.println("if should not play");
        this.chanceOfWinning = 0;
        this.chanceOfWinning += addChanceForFlush();
        this.chanceOfWinning += addChanceForStrit();
        this.chanceOfWinning += chanceForGoodDraw();
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
            } else {
                rankOfCardOutOfOrder = current;
            }
            previous = current;
        }

        boolean almostStrit = offByOne == 4;
        double chanceForStrit = 0;
        if (almostStrit) {
            int highestCardRank = getHighestCardRank();
            chanceForStrit += 0.17 * (0.5 + 0.01 * highestCardRank);
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

        while (handIterator.hasNext()) {
            String suit = handIterator.next();
            switch (suit) {
            case "h":
                hearths++;
                mostCommonSuit = (hearths == 4 ? Suit.HEARTS : mostCommonSuit);
                break;
            case "d":
                diamonds++;
                mostCommonSuit = (diamonds == 4 ? Suit.DIAMONDS : mostCommonSuit);
                break;
            case "c":
                clubs++;
                mostCommonSuit = (clubs == 4 ? Suit.CLUBS : mostCommonSuit);
                break;
            case "s":
                spades++;
                mostCommonSuit = (spades == 4 ? Suit.SPADES : mostCommonSuit);
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

        this.numberOfCardsToChange = numOfCards;

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
        System.out.println("MAKE DECISION");
        // AI goes in if has more than 20% chance of winning
        if (chanceOfWinning > 0.2) {
            return true;
        }

        Random generator = new Random();
        
        // AI goes in no matter how bad cards it has once in 50 takes
        if (generator.nextInt(101) <= 2) {
            System.out.println("BLUFF");
            this.bluff = true;
            return true;
        }

        // AI goes bluffs if it has chanceOfWinning < 5% once in 50 takes
        if (chanceOfWinning < 0.05 && generator.nextInt(101) <= 2) {
            this.bluff = true;
            return true;
        }

        // AI randomly chooses to fold if chanceOfWinning is to small
        // (lower than random double in range <0, 0.25>)
        if (generator.nextDouble() / 4 > chanceOfWinning) {
            return false;
        }

        int highestBet = table.getActiveBet();

        // AI goes in if it plays for less than (chanceOfWinning + random<0, 0.25>) percent of its chips
        return (highestBet / (chips + bet) < generator.nextDouble() / 4 + chanceOfWinning);
    }

    private int getHighestCardRank(Suit suit) {
        int highestRank = -1;
        for (Card c : hand.getCards()) {
            if (c.getRank().getCardStrength() > highestRank && c.getSuit().equals(suit)) {
                highestRank = c.getRank().getCardStrength();
            }
        }
        return highestRank;
    }

    private int getHighestCardRank() {
        int highestRank = -1;
        for (Card c : hand.getCards()) {
            if (c.getRank().getCardStrength()  > highestRank) {
                highestRank = c.getRank().getCardStrength();
            }
        }
        return highestRank;
    }

    public int placeBet() {
        int bet = chooseBet();
        System.out.println("Choosen bet: " + bet);
        throwChips(bet);
        System.out.println("Current bet total: " + this.bet);
        System.out.println(this.position + " | COW: " + this.chanceOfWinning);
        return bet;
    }

    private int chooseBet() {
        int chosenBet;
        int highestBet = table.getActiveBet();
        Random generator = new Random();
        System.out.println("Curr chips: " + chips + " | curr bet: " + bet);
        if (this.bluff) {
            System.out.println("Choose bet: bluff - " + this.bluff);
            chosenBet = bluffHighBet();
        } else {
            if (highestBet == 0 && chanceOfWinning < 0.1) {
                System.out.println("HB = 0 && chance of win = " + chanceOfWinning);
                chosenBet = 0;
            } else if (highestBet / (this.chips + this.bet) < (this.chips + this.bet) * chanceOfWinning) {
                chosenBet = raise();
            } else {
                chosenBet = call();
            }
        }
        return chosenBet;
    }

    private int bluffHighBet() {
        // Go all IN
        return this.chips - this.bet;
    }

    private int raise() {

        Random generator = new Random();
        int minRaisedBet = table.getActiveBet() + table.getDiff();
        System.out.println("Raise! MinRaisedBet: " + minRaisedBet);
        if (minRaisedBet > this.chips + this.bet) {
            System.out.println("allIn from raise!");
            return this.chips;
        } else {
            int increasedBet = minRaisedBet + generator.nextInt((int)(chanceOfWinning * 100));
            System.out.println("Goal num of chips: " + increasedBet);
            if (increasedBet > this.chips + this.bet) {
                System.out.println("Not enough for increased bet!");
                return chips;
            } else {
                System.out.println("Increased bet! Jahar!");
                return increasedBet - this.bet;
            }
        }
    }

    private int call() {
        int maxBet = table.getActiveBet();
        if (maxBet < this.chips + this.bet) {
            System.out.println("Call for max bet!: " + (maxBet - this.bet) + " | MB: " + maxBet + " | B: " + bet);
            return maxBet - this.bet;
        } else {
            System.out.println("All in from Call!");
            return this.chips;
        }
    }

    public void postSmallBlind() {
        throwChips(1);
    }

    public void postBigBlind() {
        throwChips(2);
    }

    public void throwChips(int bet) {
        this.chips -= bet;
        this.bet += bet;
        this.allIn = this.chips == 0;
    }

    public int changeCards() {
        if (this.closeToFlush || this.closeToStrit) {
            discardSingleCard();
            return 1;
        }

        if (this.numberOfCardsToChange > 3) {
            return discardCards();
        }

        return optimalDiscardForCurrentHand(); // AI already has a high chance for win. Discard unwanted cards
    }

    private void discardSingleCard() {
        if (closeToFlush) {
            for (int i = 4; i >= 0; i--) {
                if (!hand.getCards().get(i).getSuit().equals(mostCommonSuit)){
                    hand.getCards().remove(i);
                }
            }
        } else {
            for (int i = 4; i >= 0; i--) {
                if (hand.getCards().get(i).getRank().getCardStrength() == rankOfCardOutOfOrder){
                    hand.getCards().remove(i);
                }
            }
        }
    }

    private int discardCards() {
        if (this.numberOfCardsToChange == 5) {
            for (int i = 4; i >= 0; i--) {
                hand.getCards().remove(i);
            }
            return 5;
        } else {
            int highestRank = getHighestCardRank();
            for (int i = 4; i >= 0; i--) {
                if (hand.getCards().get(i).getRank().getCardStrength() != highestRank) {
                    hand.getCards().remove(i);
                }
            }
            return 4;
        }
    }

    private int optimalDiscardForCurrentHand() {
        return 1;
    }

    public int makeRaise(int playersRaise) {
        return 1;
    }
    public int makeCall() {
        return 1;
    }
    public int makeCheck() {
        return 1;
    }
}