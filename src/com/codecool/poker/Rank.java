package com.codecool.poker;

public enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14);

    private int cardStrength;

    private Rank(int cardStrength) {
        this.cardStrength = cardStrength;
    }

    public int getCardStrength() {
        return this.cardStrength;
    }
}