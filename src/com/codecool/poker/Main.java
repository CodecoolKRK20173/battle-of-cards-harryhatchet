package com.codecool.poker;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Deck deck = new Deck();
        deck.shuffle();
        List<Card> drawnCards = new ArrayList<>();

        drawnCards = deck.drawCards(5);

        for (Card card : drawnCards) {
            System.out.println(card.getRank().toString() + card.getSuit().toString());
        }
    }
}