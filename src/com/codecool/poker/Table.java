package com.codecool.poker;

import java.util.*;

public class Table {

    private final int NUM_OF_PLAYERS = 4;
    private List<Player> players;
    private Deck deck;
    private int activeBet;

    private int pot;

    public Table() {
        this.players = new ArrayList<Player>();
        initPlayers();
    }

    public void initHand() {
        int dealerIndex = players.indexOf(chooseDealer());
        Player dealer = players.get(dealerIndex);
        dealer.setDealer();
        dealer.setName("dealer");

        int smallBlindIndex = (dealerIndex + 1) % NUM_OF_PLAYERS;
        Player smallBlind = players.get(smallBlindIndex);
        smallBlind.setSmallBlind();
        smallBlind.setName("SB");
        this.pot += 1;
        smallBlind.postSmallBlind();
        this.pot += 2;

        int bigBlindIndex = (dealerIndex + 2) % NUM_OF_PLAYERS;
        Player bigBlind = players.get(bigBlindIndex);
        bigBlind.setBigBlind();
        bigBlind.setName("BB");
        bigBlind.postBigBlind();
        this.activeBet = 2;

        int utgIndex = (dealerIndex + 3) % NUM_OF_PLAYERS;
        Player utg = players.get(utgIndex);
        utg.setUTG();
        utg.setName("UTG");

        this.deck = new Deck();
        dealCards();
    }

    private void initPlayers() {
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            Player newPlayer = new HumanPlayer(this);
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

    private Player getNextActivePlayer(Player currentPlayer) {
        int currentPlayerIndex = this.players.indexOf(currentPlayer);
        Player nextPlayer = null; 
        do {
            nextPlayer = this.players.get((currentPlayerIndex + 1) % this.players.size());
        }
        while (nextPlayer.isFold());

        return nextPlayer;
    }

    private void dealCards() {
        for (Player player : this.players) {
            player.setHand(new Hand(this.deck.drawCards(5)));
        }
    }

    private boolean isBettingFinished() {
        boolean isTrue = true;

        for (Player player : this.players) {
            if (!player.isFold() && player.getBet() != this.activeBet) {
                isTrue = false;
            }
        }

        return isTrue;
    }

    public void showHands() {
        for (Player player : players) {
            if (!player.isFold()) {
                System.out.println(player);
            }
        }
    }

    public void playHand() {
        initHand();
        showHands();
        playRound(1);
        if (players.size() > 1) {
            exchangeCards();
            resetPlayersBets();
            showHands();
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
            this.activeBet = 0;
            currentPlayer = getSmallBlind();
        }

        do {
            this.pot += currentPlayer.makeAction();
            this.activeBet = currentPlayer.getBet();
            currentPlayer = getNextActivePlayer(currentPlayer);
            System.out.println("POT: " + this.pot);
        }
        while (isBettingFinished());
    }

    private void exchangeCards() {
        for (Player player : players) {
            int numOfCardsToExchange = player.changeCards();
            List<Card> newCards = deck.drawCards(numOfCardsToExchange);
            player.getHand().addCards(newCards);
        }
    }

    private void resetPlayersBets() {
        for (Player player : players) {
            player.resetBet();
        }
    }

    public int getPot() {
        return this.pot;
    }

    public int getActiveBet() {
        return this.activeBet;
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}