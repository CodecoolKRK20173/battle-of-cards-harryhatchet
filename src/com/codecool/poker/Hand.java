package com.codecool.poker;

public class Hand {
    List<Card> cards;
    int handPoints;

    public Hand() {
        this.cards = new ArrayList<>();
        this.handPoints = 100;
    }

    public static void main(String[] args) {
        System.out.println("Test main");
    }
}