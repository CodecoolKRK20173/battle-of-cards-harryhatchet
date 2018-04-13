package com.codecool.poker;

import java.util.*;
import com.codecool.poker.table.*;

public class Main {

    public static void main(String[] args) {
        Table table = new Table();
        PrintTable pt = new PrintTable(table);
        do {
            table.playHand();
        } while (!table.isGameFinished());
    }
}