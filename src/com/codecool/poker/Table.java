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
        this.deck = new Deck();
        dealCards();
        setAllToUTG();
        this.pot = 0;
        int dealerIndex = players.indexOf(chooseDealer());
        Player dealer = players.get(dealerIndex);
        dealer.setDealer();
        // dealer.setName("dealer");

        int smallBlindIndex = (dealerIndex + 1) % NUM_OF_PLAYERS;
        Player smallBlind = players.get(smallBlindIndex);
        if (smallBlind.getChips() == 0) {
            smallBlind = getNextActivePlayer(smallBlind);
        }
        smallBlind.setSmallBlind();
        // smallBlind.setName("SB");
        this.pot += 1;
        smallBlind.postSmallBlind();

        Player bigBlind = getNextActivePlayer(smallBlind);
        if (bigBlind.getChips() >= 2) {
            this.pot += 2;
            this.activeBet = 2;
        } else {
            this.pot += bigBlind.getChips();
            this.activeBet = bigBlind.getChips();
        }
        bigBlind.setBigBlind();
        // bigBlind.setName("BB");
        bigBlind.postBigBlind();
        // this.activeBet = 2;

        // int utgIndex = (dealerIndex + 3) % NUM_OF_PLAYERS;
        // Player utg = players.get(utgIndex);
        // utg.setUTG();
        // // utg.setName("UTG");
        System.out.println("SB: " + smallBlind.getName() + "\nBB: " + bigBlind.getName());
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
                System.out.println("FOUND SMALL BLIND FROM LINE 78");
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

        for (int i = 1; i < NUM_OF_PLAYERS; i++) {
            nextPlayer = this.players.get((currentPlayerIndex + i) % this.players.size());
            System.out.println("Next player: " + nextPlayer.getName() + " -> !isFold " + !nextPlayer.isFold() + 
            ", chips != 0" + (nextPlayer.getChips() != 0) + ", !hasActed " + (!nextPlayer.hasActed()) + ", has 5 cards " + (nextPlayer.getHand().getCards().size() == 5));
            if (!nextPlayer.isFold() && nextPlayer.getChips() != 0 && !nextPlayer.hasActed() && nextPlayer.getHand().getCards().size() == 5) {
                System.out.println("Found next player!");
                return nextPlayer;
            }
        }
        // System.out.println("Next player is null!");
        return currentPlayer;
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
        System.out.println("\nACTIVE PLAYERS\n");
        int numOfActivePlayers = 0;
        for (Player player : this.players) {
            if (!player.isFold() && player.getChips() != 0 && player.getHand().getCards().size() == 5) {
                numOfActivePlayers++;
                System.out.println(player.getName());
            }
        }
        System.out.println("\nCount active players: " + numOfActivePlayers);
        return numOfActivePlayers;
    }

    private void dealCards() {
        for (Player player : this.players) {
            player.setHand(new Hand(this.deck.drawCards(1)));
            if (player.getChips() > 0)
                player.setHand(new Hand(this.deck.drawCards(5)));
        }
    }

    private boolean isBettingFinished() {
        int playersThatFolded = 0;
        for (Player player : this.players) {
            if (player.isFold()) {
                playersThatFolded++;
            }
        }
        System.out.println("Betting is finished? folded: " + playersThatFolded);
        if (playersThatFolded == 3) {
            System.out.println("Betting done: 3 folded!");
            return true;
        }

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
        resetFolds();
        resetPlayersAction();
        resetPlayersBets();
        initHand();
        pt.update();
        pt.printTable();

        showHands();
        playRound(1);
        System.out.println("\n\nEND OF TURN !!\n\n\n");
        if (countActivePlayers() > 1) {
            exchangeCards();
            resetPlayersBets();
            resetPlayersAction();
            showHands();
            playRound(2);
        }
        try {
            List<Player> winners = getWinners();
            awardPot(winners);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No winner in this round!");
        }
        System.out.println("END OF THIS PLAY! ~~~~~~~~\n~!~");
    }

    private void playRound(int round) {
        Player currentPlayer = null;
        Player previousPlayer = null;

        if (round == 1) {
            currentPlayer = getUTG();
        }
        else {
            this.previousBet = 0;
            this.activeBet = 0;
            currentPlayer = getSmallBlind();
            if (currentPlayer.isFold() || currentPlayer.getChips() == 0) {
                // System.out.println("Curr is fold!");
                previousPlayer = currentPlayer;
                currentPlayer = getNextActivePlayer(currentPlayer);
            }
        }
        // System.out.println("currentPlayer == previousPlayer " + (currentPlayer == previousPlayer));
        // if (currentPlayer == previousPlayer) {
        //     System.out.println("Null incoming!");
        //     return;
        // }
        
        if (currentPlayer == previousPlayer) {
            System.out.println("Null curr player!");
            return;
        }

        do {
        // for (int i = 0; i < 12; i++){
            System.out.println(currentPlayer.getName() + " chips: " + currentPlayer.getChips());
            int raiseSize = currentPlayer.makeAction();
            this.pot += raiseSize;
            if (raiseSize > this.activeBet) {
                System.out.println("\n\n\nreset! raiseSize: " + raiseSize + " | actBet: " + this.activeBet);
                resetPlayersAction(currentPlayer);
                this.activeBet = currentPlayer.getBet();
            }
            System.out.println("Acted? " + currentPlayer.hasActed());
            previousPlayer = currentPlayer;
            currentPlayer = getNextActivePlayer(currentPlayer);
            if (currentPlayer == previousPlayer) {
                System.out.println("Null curr player!");
                return;
            }
            System.out.println("Is betting finished? " + isBettingFinished());
            System.out.println("POT: " + this.pot + "\n~~~~~~~~~~\n");
        }
        while (!isBettingFinished());
    }

    private List<Player> getWinners() {
        List<Hand> allHands = new ArrayList<>();
        List<Hand> winningHands = new ArrayList<>();
        List<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (!player.isFold() && player.getHand().getCards().size() == 5) {
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
        System.out.println("\n!!WINNERS!\n" + this.pot);
        for (Player winner : winners) {
            System.out.println(winner.getName());
            winner.addChips(this.pot);
        }
        this.pot = 0;
    }

    public int getDiff() {
        int difference = activeBet - previousBet;
        return difference;
    }

    private void exchangeCards() {
        for (Player player : players) {
            if (player.getHand().getCards().size() == 5) {
                int numOfCardsToExchange = player.changeCards();
                List<Card> newCards = deck.drawCards(numOfCardsToExchange);
                player.getHand().addCards(newCards);
            }
        }
    }

    private void resetPlayersBets() {
        System.out.println("Reset bets!");
        int sum = 0;
        for (Player player : players) {
            player.resetBet();
            sum += player.getChips();
            System.out.println(player.getName() + " | " + player.getBet() + " Chips left: " + player.getChips());
        }
        System.out.println("TOTAL CHIPS LEFT: " + sum);
    }

    private void resetPlayersAction(Player currentPlayer) {
        for (Player player : players) {
            if (!player.isFold() && !player.equals(currentPlayer) && player.getChips() > 0)
                player.setHasActed(false);
        }
    }

    private void resetPlayersAction() {
        for (Player player : players) {
            if (!player.isFold() && player.getChips() > 0) {
                player.setHasActed(false);
            }
        }
    }

    private void resetFolds() {
        for (Player player: players) {
            if (player.getChips() > 0) {
                player.resetFold();
            } else {
                player.fold();
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

    private void setAllToUTG() {
        for (Player player : players) {
            player.setUTG();
        }
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