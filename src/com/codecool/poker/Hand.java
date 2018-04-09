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

    public addCard(Card card) {
        this.handPoints.add(card);
    }

    public static void main(String[] args) {
        System.out.println("Test main");
    }
}