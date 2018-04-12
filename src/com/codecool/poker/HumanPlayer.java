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

    public void throwChips(int bet) {
        this.chips -= bet;
        this.bet += bet;
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