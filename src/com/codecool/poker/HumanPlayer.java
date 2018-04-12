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

    public HumanPlayer(Table table) {
        this.table = table;
    }

    public void addChips(int newChips) {
        this.chips += newChips;
    }

    public void throwChips(int bet) {
        this.chips -= bet;
        this.bet += bet;
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasActed() {
        return this.hasActed;
    }

    public void setHasActed(boolean hasActed) {
        this.hasActed = hasActed;
    }

    // public int placeBet(int customBet) {
    //     if (isSmallBlind) {
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

    public void resetBet() {
        this.bet = 0;
    }

    public boolean isFold() {
        return this.isFold;
    }

    public void resetFold() {
        if (isFold == true && getChips() > 0) {
            isFold = false;
        }
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
        }
        return 1;
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

    public int getBet() {
        return bet;
    }

    public int fold() {
        isFold = !isFold;
        return 0;
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
        output.append(this.chips);
        return output.toString();
    }
}