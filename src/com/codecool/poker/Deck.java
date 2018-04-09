package com.codecool.poker;

import java.util.*;

public class Deck {

    private List<Card> deck = new ArrayList<>();

    public Deck() {
        for (Suit suit: Suit.values()) {
            for (Rank rank: Rank.values()) {
                this.deck.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(this.deck);
    }
}