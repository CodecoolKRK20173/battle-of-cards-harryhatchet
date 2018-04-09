package com.codecool.poker;

import java.util.List;
import java.util.ArrayList;

public class Hand {
    private List<Card> cards;
    private int handPoints;

    public Hand() {
        this.cards = new ArrayList<>();
        this.handPoints = 100;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void replaceCards(Card cardToDelete, Card cardToAdd) {
        this.cards.remove(cardToDelete);
        this.cards.add(cardToAdd);
    }

    public static void main(String[] args) {
        System.out.println("Test main");
    }
}