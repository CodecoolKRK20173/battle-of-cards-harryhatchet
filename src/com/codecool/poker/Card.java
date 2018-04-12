package com.codecool.poker;

import javafx.scene.effect.DropShadow;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


import java.util.Map;
import java.util.HashMap;

public class Card extends ImageView {

    private Rank rank;
    private Suit suit;
    private boolean faceDown;
    private boolean isChosen;
    
    private Image backFace;
    private Image frontFace;
    private HandView containingHandView;
    private DropShadow dropShadow;

    static Image cardBackImage;
    private static final Map<String, Image> cardFaceImages = new HashMap<>();
    public static final int WIDTH = 150;
    public static final int HEIGHT = 215;
    public static final int CARD_IMAGE_WIDTH = 75;
    public static final int CARD_IMAGE_HEIGHT = 105;
    public static final int CARD_MOVE = 20;
    
    
    public Card(Rank rank, Suit suit, boolean faceDown) {
        this.rank = rank;
        this.suit = suit;
        this.faceDown = faceDown;
        this.dropShadow = new DropShadow(2, Color.gray(0, 0.75));
        backFace = cardBackImage;
        frontFace = cardFaceImages.get(getShortName());
        setImage(faceDown ? backFace : frontFace);
        setEffect(dropShadow);
        setOnMouseClicked(onMouseClickedHandler);
    }

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public void setContainingHandView(HandView containingHandView) {
        this.containingHandView = containingHandView;
    }

    public String getShortName() {
        return "S" + suit + "R" + rank.getCardStrength();
    }

    public static void loadCardImages() {
        cardBackImage = new Image("card_images/card_back.png", CARD_IMAGE_WIDTH, CARD_IMAGE_HEIGHT, true, true);

        for (Suit suit: Suit.values()) {
            for (Rank rank: Rank.values()) {
                String cardName = suit.toString() + rank.getCardStrength();
                String cardId = "S" + suit + "R" + rank.getCardStrength();
                String imageFileName = "card_images/" + cardName + ".png";
                cardFaceImages.put(cardId, new Image(imageFileName, CARD_IMAGE_WIDTH, CARD_IMAGE_HEIGHT, true, true));
            }
        }
    }

    public void moveCardView(PaneName paneName) {
        int move;
        if (!isChosen) {
            isChosen = true;
            move = CARD_MOVE;
        } else {
            isChosen = false;
            move = 0;
        }
        switch (paneName) {
            case TOP:
                this.setTranslateY(move);
                break;
            case LEFT:
                this.setTranslateX(move);
                break;
            case RIGHT:
                this.setTranslateX(-move);
                break;
            case BOTTOM:
                this.setTranslateY(-move);
                break;
            default:
                break;
        }
    }

    private EventHandler<MouseEvent> onMouseClickedHandler = e -> {
        System.out.println("clicked " + this.containingHandView.getPaneName());
        moveCardView(this.containingHandView.getPaneName());
    };
}