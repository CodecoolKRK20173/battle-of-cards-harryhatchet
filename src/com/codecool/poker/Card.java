package com.codecool.poker;

import java.lang.Comparable;

public class Card implements Comparable<Card> {

    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    @Override
    public int compareTo(Card other) {
        return rank.compareTo(other.getRank());
    }

    @Override
    public String toString() {
        String output = this.rank.toString() + this.suit.toString();
        return output;
    }
}