#!/bin/bash
find src -name '*.java' > sources.txt
mkdir -p out
javac @sources.txt -d out && java -cp out:resources com.codecool.poker.Poker
