package com.codecool.poker;

import java.util.List;
import java.util.ArrayList;

public class Table {

    private final int NUM_OF_PLAYERS = 4;
    private List<Player> players;
    private Deck deck;

    private int pot;

    public Table() {
        initPlayers();
    }

    public initHand() {
        int dealerIndex = players.indexOf(chooseDealer());
        players.get(dealerIndex).setDealer();
        players.get((dealerIndex + 1) % 4).setSmallBlind();
        players.getSmallBlind().postSmallBlind();
        players.get((dealerIndex + 2) % 4).setBigBlind();
        players.getBigBlind().postBigBlind();
        players.get((dealerIndex + 3) % 4).setUTG();
        this.deck = new Deck();
        dealCards();
    }

    private initPlayers() {
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            players.add(new HumanPlayer());
        }
    }
}