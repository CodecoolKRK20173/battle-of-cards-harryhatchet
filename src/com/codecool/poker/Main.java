package com.codecool.poker;

import java.util.*;

public class Main {

    public static void main(String[] args) {

       
        List<Card> drawnCards = new ArrayList<>();
        Hand hand;
        int i = 0;
        do { 
            Deck deck = new Deck();
            hand = new Hand(deck.drawCards(5));
            i++;
            
        } while (hand.getHandPoints() != 10 );
        for (Card card : hand.getCards()) {
            System.out.println(card.getRank().toString() + card.getSuit().toString());
        }
        System.out.println(hand.getHandPoints());
        System.out.println(i);
    }
}