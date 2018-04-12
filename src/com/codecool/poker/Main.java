package com.codecool.poker;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Table table = new Table();

        // while (!table.isGameFinished()) {
        //     table.playHand();
        // }

        for (int i = 0; i < 100; i++) {
            table.playHand();
            PrintTable pt = new PrintTable(table);
        }
        PrintTable pt = new PrintTable(table);
       
        table.playHand();
        pt.printTable();

    }
}