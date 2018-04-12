package com.codecool.poker;

import java.util.List;
import java.util.Scanner;
import java.lang.StringBuilder;


public class HumanPlayer extends Player {

    private Table table;
    private Hand hand;
    private String name;
    private Scanner sc = new Scanner(System.in);

    private boolean hasActed = false;
    private int chips=100;
    private int bet=0;
    private boolean isFold = false;
    private int minRaise;
    private Position position = Position.UTG;


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
        this.isFold = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public HumanPlayer(Table table) {
        this.table = table;
    }

    public void throwChips(int bet) {
        this.chips -= bet;
        this.bet += bet;
    }

    public int changeCards() {
        boolean[] cardStatus = {false, false, false, false, false};
        int cardToDissmiss;
        do {
            System.out.println(this.name + ", choose cards to change or press 0 to quit");
            cardToDissmiss = sc.nextInt();
            if (cardToDissmiss != 0) {
                cardStatus[cardToDissmiss - 1] = true;
            }
        }
        while (cardToDissmiss != 0);

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
        System.out.println(this.name + ", choose your action: ");
        String action = sc.next();
        switch (action) {
            case "fold":
                this.hasActed = true;
                return fold();
            case "call":
                this.hasActed = true;
                return makeCall();
            case "check":
                this.hasActed = true;
                return makeCheck();
            case "raise":
                System.out.println("Choose raise size: ");
                int raiseSize = sc.nextInt();
                this.hasActed = true;
                return makeRaise(raiseSize);
            default:
                return 0;
        }
    }

    private int makeRaise (int playersRaise) {
        minRaise = table.getActiveBet() + table.getDiff();
        if (playersRaise < minRaise) {
            playersRaise = minRaise;
        }
        
        while ((minRaise + playersRaise) > getChips()) {                             // Excessive bet control
            System.out.println("Please choose avaliable size of bet");
            playersRaise = sc.nextInt();
        }
        throwChips(playersRaise);
        return playersRaise;
    }

    private int makeCall() {
        if ((table.getActiveBet() - getBet()) > getChips() && getChips() > 0) {
            throwChips(getChips());
            return getChips();
        } else {
            int callAmount = table.getActiveBet() - getBet();
            throwChips(table.getActiveBet() - getBet());
            return callAmount;
        }
    }

    private int makeCheck(){
        return 0;
    }
    
}