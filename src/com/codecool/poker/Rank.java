package com.codecool.poker;

public enum Rank {
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "T"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K"),
    ACE(14, "A");

    private int cardStrength;
    private String symbol;

    private Rank(int cardStrength, String symbol) {
        this.cardStrength = cardStrength;
        this.symbol = symbol;
    }

    public int getCardStrength() {
        return this.cardStrength;
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}