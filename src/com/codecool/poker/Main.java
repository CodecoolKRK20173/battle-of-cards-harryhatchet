package com.codecool.poker;

import java.util.*;

public class Main {

    public static void main(String[] args) {

       
        List<Card> drawnCards = new ArrayList<>();
        Hand hand1;
        Hand hand2;
        Deck deck = new Deck();

    
        hand1 = new Hand(deck.drawCards(5));
        hand2 = new Hand(deck.drawCards(5));
        
        for (Card card : hand1.getCards()) {
            System.out.println(card.getRank().toString() + card.getSuit().toString());
        }
        System.out.println();
        for (Card card : hand2.getCards()) {
            System.out.println(card.getRank().toString() + card.getSuit().toString());
        }
        System.out.println(hand1.compareTo(hand2));
    }
}