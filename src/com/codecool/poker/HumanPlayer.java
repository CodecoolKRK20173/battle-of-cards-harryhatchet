package com.codecool.poker;

import java.util.List;
import java.util.Scanner;


public class HumanPlayer extends Player {

    Table table;
    Hand hand;
    Scanner sc = new Scanner(System.in);

    private int chips=100;
    private int bet=0;
    private boolean isFold = false;
    private boolean isDealer = false;
    private boolean isSmallBlind = false;
    private boolean isBigBlind = false;
    private boolean isUTG = false;
    int minRaise;

    public HumanPlayer(Table table) {
        this.table = table;
    }

    public void addChips(int newChips) {
        this.chips += newChips;
    }

    public void throwChips(int bet) {
        this.chips -= bet;
        this.bet = bet;
    }

    public int getChips() {
        return chips;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    // public int placeBet(int customBet) {
    //     if (isSmallBlind) {

    // }

    public void resetBet() {
        this.bet = 0;
    }

    public boolean isFold() {
        return this.isFold;
    }

    public void resetFold() {
        isFold = false;
    }

    public int changeCards() {
        boolean[] cardStatus = {false, false, false, false, false};
        int cardToDissmiss;
        do {
            System.out.println("Choose cards to change or press 0 to quit");
            cardToDissmiss = sc.nextInt();
            cardStatus[cardToDissmiss-1] = true;
        } while (cardToDissmiss == 0);

        int discard = 0;
        for (int i=4; i >= 0; i--) {
            if (cardStatus[i]) {
                hand.getCards().remove(i);
                discard++;
            }
        }
        return discard;
    }

    public void postSmallBlind() {
        throwChips(1);
    }

    public void postBigBlind() {
        throwChips(2);
    }

    public int makeAction() {
        System.out.println("Choose your action: ");
        String action = sc.next();
        switch (action) {
            case "fold":
                fold();
                return 0;
            case "call":
                return makeCall();
            case "raise":
                System.out.println("Choose raise size: ");
                int raiseSize = sc.nextInt();
                return makeRaise(raiseSize);
        }
        return 1;
    }

    public int makeRaise (int raise) {
        while (raise > getChips()) {
            System.out.println("Please choose available size of bet");
            raise = sc.nextInt();
        }/*
            if (table.getDiff() == 1) {
                minRaise = table.getMaxBet() + 2;
            } else {
                minRaise = table.getMaxBet() + table.getDiff();
            }
            bet = minRaise + playersRaise;
            if (bet > getChips()) {
                bet = getChips();
            }
            throwChips(bet);
            return bet;*/
            minRaise = 4;
            if (raise < minRaise) {
                raise = minRaise;
            }
            if (raise > getChips()) {
                raise = getChips();
            }
            throwChips(raise);
            return raise;
    }    

    public int makeCall() {
        throwChips(this.table.getActiveBet());
        return bet;
        }

    public int getBet() {
        return bet;
    }

    public void fold() {
        isFold = !isFold;
    }

    public void setDealer() {
        isDealer = true;
        isSmallBlind = false;
        isBigBlind = false;
        isUTG = false;
    }

    public void setSmallBlind() {
        isDealer = false;
        isSmallBlind = true;
        isBigBlind = false;
        isUTG = false;
    }

    public void setBigBlind() {
        isDealer = false;
        isSmallBlind = false;
        isBigBlind = true;
        isUTG = false;
    }

    public void setUTG() {
        isDealer = false;
        isSmallBlind = false;
        isBigBlind = false;
        isUTG = true;
    }

    public boolean isDealer() {
        return this.isDealer;
    }

    public boolean isSmallBlind() {
        return this.isSmallBlind;
    }

    public boolean isBigBlind() {
        return this.isBigBlind;
    }

    public boolean isUTG() {
        return this.isUTG;
    }
}