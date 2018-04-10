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
        this.deck = new Deck(); 
    }

    private initPlayers() {
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            players.add(new HumanPlayer());
        }
    }
}