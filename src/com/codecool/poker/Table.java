package com.codecool.poker;

import java.util.*;

public class Table {

    private final int NUM_OF_PLAYERS = 4;
    private List<Player> players;
    private List<Player> activePlayers;
    private Deck deck;
    private int activeBet;
    private int previousBet;

    private int pot;

    public Table() {
        this.players = new ArrayList<Player>();
        this.activePlayers = new ArrayList<Player>();
        initPlayers();
    }

    public void initHand() {
        int dealerIndex = players.indexOf(chooseDealer());
        Player dealer = players.get(dealerIndex);
        dealer.setDealer();

        int smallBlindIndex = (dealerIndex + 1) % NUM_OF_PLAYERS;
        Player smallBlind = players.get(smallBlindIndex);
        smallBlind.setSmallBlind();
        this.pot += 1;
        smallBlind.postSmallBlind();
        this.pot += 2;

        int bigBlindIndex = (dealerIndex + 2) % NUM_OF_PLAYERS;
        Player bigBlind = players.get(bigBlindIndex);
        bigBlind.setBigBlind();
        bigBlind.postBigBlind();
        this.activeBet = 2;

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

    private Player getNextPlayer(Player currentPlayer) {
        int currentPlayerIndex = this.activePlayers.indexOf(currentPlayer);
        
        return this.activePlayers.get((currentPlayerIndex + 1) % this.activePlayers.size());
    }

    private void dealCards() {
        for (Player player : this.players) {
            player.setHand(new Hand(this.deck.drawCards(5)));
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

    public void showCards() {
        for (Player player : getPlayers()) {
            System.out.println(player.getHand().toString());
        }
    }

    public void playHand() {
        initHand();
        showCards();
        playRound(1);
        if (activePlayers.size() > 1) {
            exchangeCards();
            showCards();
            this.previousBet = 0;
            this.activeBet = 0;
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
            this.pot += currentPlayer.makeAction();
            this.previousBet = activeBet;
            this.activeBet = currentPlayer.getBet();
            if (currentPlayer.isFold()) {
                this.activePlayers.remove(currentPlayer);
            }
            currentPlayer = getNextPlayer(currentPlayer);
            System.out.println("POT: " + this.pot);
        }
        while (!isBettingFinished());
    }

    public int getDiff() {
        int difference = activeBet - previousBet;
        return difference;
    }

    private void exchangeCards() {
        for (Player player : activePlayers) {
            int numOfCardsToExchange = player.changeCards();
            List<Card> newCards = deck.drawCards(numOfCardsToExchange);
            player.getHand().addCards(newCards);
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