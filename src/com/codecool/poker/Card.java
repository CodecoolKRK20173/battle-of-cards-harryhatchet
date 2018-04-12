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

    @Override
    public int hashCode() {
        return rank.getCardStrength() * 31 + (int) suit.toString().charAt(0) * 29;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card) {
            Card card = (Card) obj;
            if (card.getRank().getCardStrength() == this.getRank().getCardStrength()) {
                if(card.getSuit().toString().equals(this.getSuit().toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}