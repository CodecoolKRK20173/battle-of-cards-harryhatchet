package com.codecool.poker.properties;

public enum Suit {
    HEARTS("♥"),
    DIAMONDS("♦"),
    SPADES("♠"),
    CLUBS("♣");

    private String symbol;

    private Suit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}