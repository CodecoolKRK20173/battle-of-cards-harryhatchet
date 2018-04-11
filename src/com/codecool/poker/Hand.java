package com.codecool.poker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Hand extends Pane {
    private int handPoints;
    private double cardGap;
    private ObservableList<Card> cards = FXCollections.observableArrayList();

    public Hand(String name, double cardGap) {
        //this.cards = cards;
        this.cardGap = cardGap;        
        this.handPoints = 100;   
        //sortCards();
    }

    public void addCard(Card card) {
        this.cards.add(card);
        //card.setContainingPile(this);
        card.toFront();
        layoutCard(card);
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    private void replaceCards(Card cardToDelete, Card cardToAdd) {
        this.cards.remove(cardToDelete);
        this.cards.add(cardToAdd);
    }

    public ObservableList<Card> getCards() {
        return this.cards;
    }

    public double getCardGap() {
        return cardGap;
    }

    private Map<Integer, Integer> calculateCardsOccurrence() {
        Map<Integer, Integer> cardsOccurrence = new HashMap<>();
        Integer count;
        Integer strength;
        for(Card card: cards) {
            strength = card.getRank().getCardStrength();
            if(cardsOccurrence.containsKey(strength)) {
                count = cardsOccurrence.get(strength) + 1;
            } else {
                count = 1;
            }
            cardsOccurrence.put(strength, count);
        }
        return cardsOccurrence;
    }

    private void sortCards() {
        Map<Integer, Integer> cardsOccurrence = calculateCardsOccurrence();
        ObservableList<Card> sortedCards = FXCollections.observableArrayList();
        Integer count1;
        Integer count2;
        
        for(Card card: cards) {
            count1 = card.getRank().getCardStrength();

            for(int i = 0; i < sortedCards.size(); i++) {
                count2 = sortedCards.get(i).getRank().getCardStrength();

                if(cardsOccurrence.get(count1) > (cardsOccurrence.get(count2))) {
                    sortedCards.add(i, card);
                    break;
                } else if (cardsOccurrence.get(count1) == (cardsOccurrence.get(count2))) {
                    if(count1 > count2) {
                        sortedCards.add(i, card);
                        break;
                    }
                } 
            }
            if (!sortedCards.contains(card)) {
                sortedCards.add(card);
            }  
        }
        
        this.cards = sortedCards;
    }

    public static void main(String[] args) {
        System.out.println("Test main");
    }

    private void layoutCard(Card card) {
        card.relocate(card.getLayoutX() + card.getTranslateX(), card.getLayoutY() + card.getTranslateY());
        card.setTranslateX(0);
        card.setTranslateY(0);
        card.setLayoutX(getLayoutX());
        card.setLayoutY(getLayoutY() + (cards.size() - 1) * cardGap);
    }

    public void setBlurredBackground() {
        setPrefSize(Card.WIDTH, Card.HEIGHT);
        BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.0, 0.2), null, null);
        Background background = new Background(backgroundFill);
        GaussianBlur gaussianBlur = new GaussianBlur(10);
        setBackground(background);
        setEffect(gaussianBlur);
    }
}