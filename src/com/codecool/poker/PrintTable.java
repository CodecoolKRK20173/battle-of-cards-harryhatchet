package com.codecool.poker;

import java.util.*;


public class PrintTable {
    private final int width = 80;
    private final int weigth = 40;
    private String[][] displayTable = new String[width][weigth];

    private final String whiteColor = "\033[0m";
    private final String greenColor = "\033[32m";
    private final String blueColor = "\033[37m";
    private final String blueBackground = "\033[46m";
    private final String redColor = "\033[31m";
    private final String brownColor = "\033[30m";
    private final String yellowColor = "\033[33m";
    private final String brownBackground = "\033[40m";
    private final String pinkColor = "\033[35m";

    Table table;

    public PrintTable(Table table) {
        this.table = table;
        initDisplayTable();
    }

    public void printTable() {
        for(int y = 0; y < weigth; y++) {
            for(int x = 0; x < width; x++) {
                System.out.print(displayTable[x][y]);
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
        String title1 = "Stack:";
        String title2 = "Pot:";
        String title3 = "Role:";
        addString(title1, 6, 9);
        addString(title2, 6, 10);
        addString(title3, 6, 11);
        addString(title1, 14, 5);
        addString(title2, 14, 6);
        addString(title3, 14, 7);
        addString(title1, width - 26, weigth - 9);
        addString(title2, width - 26, weigth - 8);
        addString(title3, width - 26, weigth - 7);
        addString(title1, width - 16, weigth - 13);
        addString(title2, width - 16, weigth - 12);
        addString(title3, width - 16, weigth - 11);
        
    }

    public void update() {
        addCards(table.getPlayers().get(0), (int) ((width - 8) / 2), weigth - 4, true);
        addCards(table.getPlayers().get(1), 3, (int) ((weigth - 8) / 2), false);
        addCards(table.getPlayers().get(2), (int) ((width - 8) / 2), 2, true);
        addCards(table.getPlayers().get(3), width - 5, (int) ((weigth - 8) / 2), false);
    }

    private void addCards(Player player, int firstX, int firstY, boolean isHorizontal) {
        Card card;
        List<Card> cards = player.getHand().getCards();
        int x = 0, y = 0;
        for(int i = 0; i < cards.size(); i++) {
            if (isHorizontal) {
                x = i * 2;
            } else{
                y = i;
            }
            card = cards.get(i);
            addString(card.toString(), firstX + x, firstY + y, !isHorizontal);
        }
    }

    private void initTable() {
        this.displayTable = new String[width][weigth];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < weigth; y++) {
                displayTable[x][y] = " ";
            }
        }
    }

    private void addFrame() {
        String sign = "#";
        for(int x = 0; x < width; x++) {
            displayTable[x][0] = sign;
            displayTable[x][weigth - 1] = sign;
        }
        for(int y = 0; y < weigth; y++) {
            displayTable[0][y] = sign;
            displayTable[width - 1][y] = sign;
        }
    }

    private void addString(String str, int firstX, int firstY) {  
        addString(str, firstX, firstY, true);
    }  

    private void addString(String str, int firstX, int firstY, boolean isHorizontal) {
        int x = 0, y = 0;
        for(int i = 0; i < str.length(); i++) {
            if (isHorizontal) {
                x = i;
            } else{
                y = i;
            }
            displayTable[firstX + x][firstY + y] = Character.toString(str.charAt(i));
        }
    }
}

