package com.codecool.poker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Poker extends Application {

    private static final double WINDOW_WIDTH = 1400;
    private static final double WINDOW_HEIGHT = 900;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Table table = new Table();
        //table.setTableBackground(new Image("/table/green.png"));

        Scene scene = new Scene(table, WINDOW_WIDTH, WINDOW_HEIGHT);
        MenuBar menuBar = new MenuBar();
        
        Menu menuFile = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        
        exit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });        

        menuFile.getItems().addAll(exit);      
        menuBar.getMenus().addAll(menuFile);
        //table.getChildren().addAll(menuBar);

        primaryStage.setTitle("Poker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}