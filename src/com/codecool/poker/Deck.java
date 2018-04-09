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

    public List<Card> drawCards(int n) {
        Random generator = new Random();
        List<Card> drawnCards = new ArrayList<>();
        int i = 0;

        do {
            int cardIndex = 0;
            do {
                cardIndex = generator.nextInt(52);
            }
            while (drawnCards.contains(this.deck.get(cardIndex)));
            drawnCards.add(this.deck.get(cardIndex));

            i++;
        }
        while (i <= n);

        return drawnCards;
    }
}