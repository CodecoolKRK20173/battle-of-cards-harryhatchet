package com.codecool.poker;

import java.util.*;

public class Table {

    private final int NUM_OF_PLAYERS = 4;
    private List<Player> players;
    private Player currentPlayer = null;
    private Deck deck;
    private int activeBet;
    private int previousBet;
    private HandComparator handComparator;

    private PrintTable pt;

    private int pot;

    public Table() {
        this.players = new ArrayList<Player>();
        initPlayers();
        this.pt = new PrintTable(this);
    }

    public void initHand() {
        this.pot = 0;
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
        for (int i = 0; i < 0; i++) {
            Player newPlayer = new HumanPlayer(this);
            players.add(newPlayer);
            newPlayer.setName("Player " + (i + 1));
        }
        for (int i = 0; i < 4; i++) {
            Player newPlayer = new AI(this);
            players.add(newPlayer);
            newPlayer.setName("AI " + (i + 1));
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

    private Player getNextActivePlayer() {
        int currentPlayerIndex = this.players.indexOf(currentPlayer);
        Player nextPlayer = null; 

        for (int i = 1; i < NUM_OF_PLAYERS; i++) {
            nextPlayer = this.players.get((currentPlayerIndex + i) % this.players.size());
            if (!nextPlayer.isFold()) {
                return nextPlayer;
            }
        }

        return null;
    /*
        int i = 1;
        do {
            nextPlayer = this.players.get((currentPlayerIndex + i) % this.players.size());
            i++;
        }
        while (nextPlayer.isFold() && i < NUM_OF_PLAYERS);

        return nextPlayer;*/
    }

    private int countActivePlayers() {
        int numOfActivePlayers = 0;
        for (Player player : this.players) {
            if (!player.isFold() && player.getChips() != 0) {
                numOfActivePlayers++;
            }
        }
        return numOfActivePlayers;
    }

    private void dealCards() {
        for (Player player : this.players) {
            player.setHand(new Hand(this.deck.drawCards(5)));
        }
    }

    private boolean isBettingFinished() {
        boolean isTrue = true;

        for (Player player : this.players) {
            if (!player.hasActed()) {
                isTrue = false;
            }
        }

        return isTrue;
    }

    public void showHands() {
        for (Player player : players) {
            if (!player.isFold()) {
                pt.printTable();
            }
        }
    }

    public void playHand() {
        initHand();
        pt.update();
        pt.printTable();

        showHands();
        playRound(1);
        if (countActivePlayers() > 1) {
            exchangeCards();
            resetPlayersBets();
            resetPlayersAction();
            showHands();
            playRound(2);
        }
        List<Player> winners = getWinners();
        awardPot(winners);
    }

    private void playRound(int round) {

        if (round == 1) {
            currentPlayer = getUTG();
        }
        else {
            this.activeBet = 0;
            currentPlayer = getSmallBlind();
            if (currentPlayer.isFold()) {
                currentPlayer = getNextActivePlayer();
            }
        }

        do {
            int raiseSize = currentPlayer.makeAction();
            this.pot += raiseSize;
            if (raiseSize > this.activeBet) {
                resetPlayersAction(currentPlayer);
                this.activeBet = currentPlayer.getBet();
            }
            pt.printTable();
            currentPlayer = getNextActivePlayer();
        }
        while (!isBettingFinished());
    }

    private List<Player> getWinners() {
        List<Hand> allHands = new ArrayList<>();
        List<Hand> winningHands = new ArrayList<>();
        List<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (!player.isFold()) {
                allHands.add(player.getHand());
            }
        }

        handComparator = new HandComparator(allHands);
        winningHands = handComparator.getBestHands();

        for (Hand winningHand : winningHands) {
            for (Player player : players) {
                if (player.getHand().compareTo(winningHand) == 0) {
                    winners.add(player);
                }
            }
        }
        return winners;
    }

    private void awardPot(List<Player> winners) {
        for (Player winner : winners) {
            winner.addChips(this.pot);
        }
    }

    public int getDiff() {
        int difference = activeBet - previousBet;
        return difference;
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

    private void resetPlayersAction(Player currentPlayer) {
        for (Player player : players) {
            if (!player.isFold() && !player.equals(currentPlayer))
                player.setHasActed(false);
        }
    }

    private void resetPlayersAction() {
        for (Player player : players) {
            if (!player.isFold()) {
                player.setHasActed(false);
            }
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
    

    public boolean isGameFinished() {
        for (Player player : players) {
            if (player.getChips() == 100 * NUM_OF_PLAYERS) {
                return true;
            }
        }
        return false;
    }
}