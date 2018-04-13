package com.codecool.poker;

import java.util.*;

public class PrintTable {
    private final int width = 80;
    private final int weigth = 40;
    private String[][] displayTable = new String[width][weigth];
    private Map<String, String> colorSign = new HashMap<>();

    private final String clear = "\033[H\033[2J";
    private final String stopColor = "\033[0m";
    private final String greenColor = "\033[1;32m";
    private final String redColor = "\033[1;31m";
    private final String blackColor = "\033[30m";
    private final String pinkColor = "\033[35m";
    private final String pinkBackground = "\033[45m";
    private final String whiteBackground = "\033[107m";
    private final String blackBackground = "\033[40m";
    private final String greyBackground = "\033[100m";

    Table table;

    public PrintTable(Table table) {
        this.table = table;
        initDisplayTable();
        initColorSignMap();
    }

    private void initColorSignMap() {
        String s = "0123456789AKQJT";
        String l;
        colorSign.put("#", pinkBackground + pinkColor + "#" + stopColor);
        colorSign.put("♥", whiteBackground + redColor + "♥" + stopColor);
        colorSign.put("♦", whiteBackground + redColor + "♦" + stopColor);
        colorSign.put("♠", whiteBackground + blackColor + "♠" + stopColor);
        colorSign.put("♣", whiteBackground + blackColor + "♣" + stopColor);
        colorSign.put("-", whiteBackground + "-" + stopColor);
        colorSign.put("D", blackBackground + redColor + "D" + stopColor);
        for (int i = 0; i < s.length(); i++) {
            l = String.valueOf(s.charAt(i));
            colorSign.put(l, greyBackground + greenColor + l + stopColor);
        }

    }

    public void printTable() {
        String sign;
        update();
        for (int y = 0; y < weigth; y++) {
            for (int x = 0; x < width; x++) {
                sign = displayTable[x][y];
                if (colorSign.containsKey(sign)) {
                    System.out.print(colorSign.get(sign));
                } else {
                    System.out.print(greyBackground + sign);
                }

            }
            System.out.println();
        }
    }

    private void initDisplayTable() {
        initTable();
        addFrame();
        addSubtitles();
    }

    private void addSubtitles() {
        String title1 = "Chips:";
        String title2 = "Bet:";
        String title3 = "Role:";

        addString(title1, width - 26, weigth - 9);
        addString(title2, width - 26, weigth - 8);
        addString(title3, width - 26, weigth - 7);

        addString(title1, 6, 9);
        addString(title2, 6, 10);
        addString(title3, 6, 11);

        addString(title1, 15, 5);
        addString(title2, 15, 6);
        addString(title3, 15, 7);

        addString(title1, width - 16, weigth - 13);
        addString(title2, width - 16, weigth - 12);
        addString(title3, width - 16, weigth - 11);

    }

    public void update() {
        List<Player> players = table.getPlayers();
        int centerX = (int) ((width - 14) / 2);
        int centerY = (int) ((weigth - 10) / 2);
        addCards(players.get(0), centerX, weigth - 4, true);
        addCards(players.get(1), 3, centerY, false);
        addCards(players.get(2), centerX, 2, true);
        addCards(players.get(3), width - 5, centerY, false);

        addInt(table.getPot(), centerX + 6, centerY + 4);

        addInt(players.get(0).getChips(), width - 20, weigth - 9);
        addInt(players.get(1).getChips(), 12, 9);
        addInt(players.get(2).getChips(), 21, 5);
        addInt(players.get(3).getChips(), width - 10, weigth - 13);

        addInt(players.get(0).getBet(), width - 20, weigth - 8);
        addInt(players.get(1).getBet(), 12, 10);
        addInt(players.get(2).getBet(), 21, 6);
        addInt(players.get(3).getBet(), width - 10, weigth - 12);

        addString(players.get(0).roleToString(), width - 20, weigth - 7);
        addString(players.get(1).roleToString(), 12, 11);
        addString(players.get(2).roleToString(), 21, 7);
        addString(players.get(3).roleToString(), width - 10, weigth - 11);
    }

    private void addInt(int number, int firstX, int firstY) {
        addString(Integer.toString(number) + "   ", firstX, firstY);
    }

    private void addCards(Player player, int firstX, int firstY, boolean isHorizontal) {
        String card;
        List<Card> cards = player.getHand().getCards();
        int x = 0, y = 0, j = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (isHorizontal) {
                x = i * 3;
            } else {
                y = i * 2;
                j = 1;
            }
            if (player.equals(table.getCurrentPlayer())) {
                card = cards.get(i).toString();
            } else {
                card = "--";
            }

            addString("--", firstX + x + 1, firstY + y, !isHorizontal);
            addString(card, firstX + x, firstY + y, !isHorizontal);
        }
    }

    private void initTable() {
        this.displayTable = new String[width][weigth];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < weigth; y++) {
                displayTable[x][y] = " ";
            }
        }
    }

    private void addFrame() {
        String sign = "#";
        for (int x = 0; x < width; x++) {
            displayTable[x][0] = sign;
            displayTable[x][weigth - 1] = sign;
        }
        for (int y = 0; y < weigth; y++) {
            displayTable[0][y] = sign;
            displayTable[width - 1][y] = sign;
        }
    }

    private void addString(String str, int firstX, int firstY) {
        addString(str, firstX, firstY, true);
    }

    private void addString(String str, int firstX, int firstY, boolean isHorizontal) {
        int x = 0, y = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isHorizontal) {
                x = i;
            } else {
                y = i;
            }
            displayTable[firstX + x][firstY + y] = Character.toString(str.charAt(i));
        }
    }
}
