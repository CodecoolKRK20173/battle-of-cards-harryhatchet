package com.codecool.poker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class HandView extends FlowPane {
    private Hand hand;
    private PaneName paneName;

    public HandView(Hand hand, PaneName paneName) {
        if (paneName.equals(PaneName.TOP) || paneName.equals(PaneName.BOTTOM)) {
            this.setOrientation(Orientation.HORIZONTAL);
        } else {
            this.setOrientation(Orientation.VERTICAL);
        }
        this.paneName = paneName;
        this.hand = hand;    

        this.setStyle("-fx-background-color: DAE6F3;");
        //this.setPadding(new Insets(0, 5, 0, 5));
        this.setVgap(0.5); //the amount of vertical space between each node
        this.setHgap(1); //the amount of horizontal space between each node
        this.setAlignment(Pos.CENTER);
        this.setPrefWidth(150);
        this.setPrefHeight(120);

        if (this.getOrientation().equals(Orientation.HORIZONTAL)) {
            //this.setHgap(15); //the amount of horizontal space between each node
        }

        //this.setBlurredBackground();
        //this.setLayoutX(x);
        //this.setLayoutY(y);
    }



    public Hand getHand() {
        return this.hand;
    }

    public PaneName getPaneName() {
        return this.paneName;
    }

    public void setPaneName(PaneName paneName) {
        this.paneName = paneName;
    }
/*
    private void layoutCard(Card card) {
        card.relocate(card.getLayoutX() + card.getTranslateX(), card.getLayoutY() + card.getTranslateY());
        card.setTranslateX(0);
        card.setTranslateY(0);
        card.setLayoutX(getLayoutX());
        card.setLayoutY(getLayoutY() + (cards.size() - 1) * cardGap);
    }
*/
    public void setBlurredBackground() {
        setPrefSize(Card.WIDTH, Card.HEIGHT);
        BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.0, 0.2), null, null);
        Background background = new Background(backgroundFill);
        GaussianBlur gaussianBlur = new GaussianBlur(10);
        setBackground(background);
        setEffect(gaussianBlur);
    }
}