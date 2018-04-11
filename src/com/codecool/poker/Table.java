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
import java.util.Random;

public class Table extends Pane {
    
    private final int NUM_OF_PLAYERS = 4;
    private List<Player> players;
    private List<Player> activePlayers;
    private Deck deck;
    //private List<Card> deck = new ArrayList<>();

    //private Pile restDeck;
    private List<Hand> playersHands = FXCollections.observableArrayList();
    private int activeBet;
    private int pot;

    private static double STOCK_GAP = 1;
    private static double FOUNDATION_GAP = 0;
    private static double TABLEAU_GAP = 30;
    
    public Table() {
        this.players = new ArrayList<Player>();
        this.activePlayers = new ArrayList<Player>();
        initPlayers();
        this.deck = new Deck();
        //deck = Card.createNewDeck();
        initHands();
        deck.shuffle();
        List<Card> drawnCards = new ArrayList<>();
    }

    public void initHand() {
        int dealerIndex = players.indexOf(chooseDealer());
        Player dealer = players.get(dealerIndex);
        dealer.setDealer();

        int smallBlindIndex = (dealerIndex + 1) % NUM_OF_PLAYERS;
        Player smallBlind = players.get(smallBlindIndex);
        smallBlind.setSmallBlind();
        smallBlind.postSmallBlind();

        int bigBlindIndex = (dealerIndex + 2) % NUM_OF_PLAYERS;
        Player bigBlind = players.get(bigBlindIndex);
        bigBlind.setBigBlind();
        bigBlind.postBigBlind();

        int utgIndex = (dealerIndex + 3) % NUM_OF_PLAYERS;
        Player utg = players.get(utgIndex);
        utg.setUTG();

        activePlayers.add(smallBlind);
        activePlayers.add(bigBlind);
        activePlayers.add(utg);
        activePlayers.add(dealer);

        this.deck = new Deck();
        dealCards();
    }

    private void initPlayers() {
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            Player newPlayer = new HumanPlayer();
            players.add(newPlayer);
        }
    }

    private Player chooseDealer() {
        Random randomizer = new Random();
        Player dealer = this.players.get(randomizer.nextInt(this.players.size()));
        return dealer;
    }

    private Player getSmallBlind() {
        for (Player player : this.players) {
            if (player.isSmallBlind()) {
                return player;
            }
        }
        return null;
    }

    private Player getBigBlind() {
        for (Player player : this.players) {
            if (player.isBigBlind()) {
                return player;
            }
        }
        return null;
    }

    private Player getUTG() {
        for (Player player : this.players) {
            if (player.isUTG()) {
                return player;
            }
        }
        return null;
    }

    private Player getDealer() {
        for (Player player : this.players) {
            if (player.isDealer()) {
                return player;
            }
        }
        return null;
    }

    private Player getNextPlayer(Player currentPlayer) {
        int currentPlayerIndex = this.activePlayers.indexOf(currentPlayer);
        
        return this.activePlayers.get((currentPlayerIndex + 1) % this.activePlayers.size());
    }

    private void dealCards() {
        for (Player player : this.players) {
            //layer.setHand(new Hand(this.deck.drawCards(5)));
        }
    }

    private boolean isBettingFinished() {
        boolean isTrue = true;

        for (Player player : this.activePlayers) {
            if (player.getBet() != this.activeBet) {
                isTrue = false;
            }
        }

        return isTrue;
    }

    public void playHand() {
        initHand();
        playRound(1);
        if (activePlayers.size() > 1) {
            exchangeCards();
            playRound(2);
        }
        //determineWinners();
    }

    private void playRound(int round) {
        Player currentPlayer = null;

        if (round == 1) {
            currentPlayer = getUTG();
        }
        else {
            currentPlayer = this.activePlayers.get(0);
        }

        do {
            currentPlayer.makeAction();
            if (currentPlayer.isFold()) {
                this.activePlayers.remove(currentPlayer);
            }
            currentPlayer = getNextPlayer(currentPlayer);
        }
        while (!isBettingFinished());
    }

    private void exchangeCards() {
        for (Player player : activePlayers) {
            //to jest dobre int numOfCardsToExchange = player.changeCards();
            int numOfCardsToExchange = 2;
            List<Card> newCards = deck.drawCards(numOfCardsToExchange);
            player.getHand().addCards(newCards);
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