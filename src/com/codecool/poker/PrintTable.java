package com.codecool.poker;

public class PrintTable {
    private final int width = 65;
    private final int weigth = 35;
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
        addString(title1, 5, 7);
        addString(title2, 5, 8);
        addString(title3, 5, 9);
        addString(title1, 13, 3);
        addString(title2, 13, 4);
        addString(title3, 13, 5);
        addString(title1, 48, 26);
        addString(title2, 48, 27);
        
    }

    private void addCards(Player player, int firstX, int firstY, boolean isHorizontal) {
        
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

