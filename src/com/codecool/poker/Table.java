package com.codecool.poker;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class Table extends Pane {
    
    private final int NUM_OF_PLAYERS = 4;
    private List<Player> players;
    private Deck deck;
    //private List<Card> deck = new ArrayList<>();

    //private Pile restDeck;
    private List<Hand> playersHands = FXCollections.observableArrayList();

    private static double STOCK_GAP = 1;
    private static double FOUNDATION_GAP = 0;
    private static double TABLEAU_GAP = 30;
    
    public Table() {
        initPlayers();
        this.deck = new Deck();
        //deck = Card.createNewDeck();
        iniHands();
        deck.shuffle();
        List<Card> drawnCards = new ArrayList<>();
    }

            
    private void initPlayers() {
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            players.add(new HumanPlayer());
        }
    }

    public void setTableBackground(Image tableBackground) {
        setBackground(new Background(new BackgroundImage(tableBackground,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }

    private void initHands() {
        int x;
        for (int i = 0; i < 4; i++) {
            x = 610 + i * 180;
            Hand playerHand = initOneHand("Hand " + i, FOUNDATION_GAP, x, 20);
            playersHands.add(playerHand);
        }
    }

    private Hand initOneHand(String name, double gap, int x, int y) {
        Hand hand = new Hand(name, gap);
        hand.setBlurredBackground();
        hand.setLayoutX(x);
        hand.setLayoutY(y);
        //hand.setCards(deck.drawCards(5));
        getChildren().add(hand);
        return hand;
    }
}