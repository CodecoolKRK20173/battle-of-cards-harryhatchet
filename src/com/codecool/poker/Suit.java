package com.codecool.poker;

public enum Suit {
    HEARTS("h"),
    DIAMONDS("d"),
    SPADES("s"),
    CLUBS("c");

    private String symbol;

    private Suit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}