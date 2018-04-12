package com.codecool.poker;

public enum Suit {
    HEARTS("hearts"),
    DIAMONDS("diamonds"),
    SPADES("spades"),
    CLUBS("clubs");

    private String symbol;

    private Suit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}