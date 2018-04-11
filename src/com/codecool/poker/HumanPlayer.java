package com.codecool.poker;

import java.util.List;
import java.util.Scanner;


public class HumanPlayer extends Player {

    public HumanPlayer() {
        this.table = table;
    }

    Hand hand = new Hand(cards);
    Scanner sc = new Scanner(System.in);

    private int chips=100;
    private int bet=0;
    private boolean isFold = false;
    private int minRaise;

    public int getChips() {
        return chips;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
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

    public void addChips(int newChips) {
        chips += newChips;
    }

    private void throwChips(int bet) {
        chips -= bet;
    }

    public void resetBet() {
        this.bet = 0;
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
            System.out.println("Choose cards to change or press 0 to quit");
            cardToDissmiss = sc.nextInt();
            cardStatus[cardToDissmiss-1] = true;
        } while (cardToDissmiss == 0);

        int discard = 0;
        for (int i=4; i >= 0; i--) {
            if (cardStatus[i]) {
                hand.discard(i);
                discard++;
            }
        }
        return discard;
    }

    public int makeRaise (int playersRaise) {
        minRaise = table.getMaxBet() + table.getDiff();
        
        while ((minRaise + playersRaise) > getChips()) {                             // Excessive bet control
            System.out.println("Please choose avaliable size of bet");
            playersRaise = sc.nextInt();
        }
        bet = minRaise + playersRaise;
        throwChips(bet);
        return bet;
    }    

    public int makeCall() {
        if ((table.getMaxBet() - getBet()) > getChips() && getChips() > 0) {
            bet = throwChips(getChips());
        } else {
            bet = throwChips(table.getMaxBet() - getBet());
        }
        return bet;
    }

    public int makeCheck(){
        if (table.getMaxBet() == getBet()) {
            return bet;
        }
        return null;
    }

    public int getBet() {
        return bet;
    }

    private void fold() {
        isFold = !isFold;
    }
}