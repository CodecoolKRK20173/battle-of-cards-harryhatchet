package com.codecool.poker;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Deck deck = new Deck();
        System.out.println("s");
        deck.shuffle();
        List<Card> drawnCards = new ArrayList<>();
        Hand hand = new Hand(deck.drawCards(5));
        

        for (Card card : hand.getCards()) {
            System.out.println(card.getRank().toString() + card.getSuit().toString());
        }
    }
}