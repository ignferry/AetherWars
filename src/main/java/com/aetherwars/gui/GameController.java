package com.aetherwars.gui;

import com.aetherwars.AetherWars;
import com.aetherwars.card.Card;
import com.aetherwars.deck.Deck;
import com.aetherwars.event.*;
import com.aetherwars.gamestate.GameState;
import com.aetherwars.gamestate.Phase;
import com.aetherwars.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable, Publisher, Subscriber {
    @FXML private Label player1NameLabel;
    @FXML private Label player2NameLabel;
    @FXML private Label player1LifeLabel;
    @FXML private Label player2LifeLabel;
    @FXML private Label turnLabel;
    @FXML private AnchorPane player1FieldPane;
    @FXML private AnchorPane player2FieldPane;
    @FXML private HBox cardSelectionBox;
    @FXML private Rectangle drawRectangle;
    @FXML private Rectangle planRectangle;
    @FXML private Rectangle attackRectangle;
    @FXML private Rectangle endRectangle;
    @FXML private Button nextPhaseButton;
    @FXML private HBox handBox;
    @FXML private HBox cardInfoBox;
    @FXML private StackPane manaStackPane;
    @FXML private ProgressBar manaProgressBar;
    @FXML private Label numOfCardsLabel;
    @FXML private Button trashButton;

    private GridPane manaGrid;
    private Player player1;
    private Player player2;
    private boolean hasDrawn;
    private List<HandCardController> drawnCardControllers;
    private List<HandCardController> handCardControllers;

    public GameController(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setInitialGame() {
        player1NameLabel.setText(player1.getName());
        player2NameLabel.setText(player2.getName());
        numOfCardsLabel.setText(Integer.toString(player1.getPlayerDeck().getCardTotal()));
        drawRectangle.setFill(Color.rgb(0, 100, 0));
        this.drawnCardControllers = new ArrayList<>();
        this.handCardControllers = new ArrayList<>();
        setHand();
        setManaGrid();
        startDrawPhase();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @Override
    public void onEvent(Event event) {
        if (event instanceof ClickHandCardEvent) {
            // Menampilkan infobox untuk kartu yang bersangkutan
            ClickHandCardEvent clickHandCardEvent = (ClickHandCardEvent) event;
            Card card = clickHandCardEvent.getHandCardController().getCard();
            try {
                cardInfoBox.getChildren().clear();
                FXMLLoader cardInfoLoader = new FXMLLoader(AetherWars.class.getResource("view/card-info.fxml"));
                cardInfoLoader.setControllerFactory(c -> new CardInfoController());
                HBox infoBox = cardInfoLoader.load();
                CardInfoController controller = cardInfoLoader.getController();
                controller.setCardInfo(card, false);
                cardInfoBox.getChildren().add(infoBox);
            }
            catch (IOException e){
                e.printStackTrace();
            }

            // Memilih kartu untuk didraw, menambahkannya ke hand, dan mengembalikan sisanya ke deck
            if (GameState.getCurrentPhase() == Phase.DRAW && !hasDrawn) {
                getCurrentPlayer().getHand().add(card);
                addCardToHand(card);

                for (HandCardController c : drawnCardControllers) {
                    EventBroker.removeObject(c);
                }

                drawnCardControllers.remove(clickHandCardEvent.getHandCardController());

                List<Card> leftOverCard = new ArrayList<>();
                for (HandCardController c : drawnCardControllers) {
                    leftOverCard.add(c.getCard());
                }
                getCurrentPlayer().getPlayerDeck().returnCard(leftOverCard);

                numOfCardsLabel.setText(Integer.toString(getCurrentPlayer().getPlayerDeck().getCardTotal()));
                cardSelectionBox.getChildren().clear();
                cardSelectionBox.setVisible(false);
                handBox.setDisable(false);
                hasDrawn = true;
                nextPhaseButton.setDisable(false);
            }

            if (GameState.getCurrentPhase() == Phase.PLAN) {
                manaStackPane.setDisable(false);
                trashButton.setDisable(false);
            }
            else {
                manaStackPane.setDisable(true);
                trashButton.setDisable(true);
            }
        }
        if (event instanceof ManaUsedEvent) {

        }
        if (event instanceof AddCardToFieldEvent) {

        }
        if (event instanceof AttackEvent) {

        }
    }

    public void onTrashButtonClick() {

    }

    public void onManaStackPaneClick() {

    }

    @FXML
    public void onNextPhaseButtonClick(MouseEvent e) {
        GameState.changeToNextPhase();
        if (GameState.getCurrentPhase() == Phase.DRAW) {
            GameState.changeTurn();
            prepareNewTurn();
            startDrawPhase();
            drawRectangle.setFill(Color.rgb(0, 100, 0));
            endRectangle.setFill(Color.rgb(85,85,85));
        }
        else if (GameState.getCurrentPhase() == Phase.PLAN) {
            planRectangle.setFill(Color.rgb(0, 100, 0));
            drawRectangle.setFill(Color.rgb(85,85,85));
        }
        else if (GameState.getCurrentPhase() == Phase.ATTACK) {
            attackRectangle.setFill(Color.rgb(0, 100, 0));
            planRectangle.setFill(Color.rgb(85,85,85));
        }
        else if (GameState.getCurrentPhase() == Phase.END) {
            endRectangle.setFill(Color.rgb(0, 100, 0));
            attackRectangle.setFill(Color.rgb(85,85,85));
        }

    }

    private void startDrawPhase() {
        hasDrawn = false;

        cardSelectionBox.setVisible(true);
        handBox.setDisable(true);

        Deck currentDeck = getCurrentPlayer().getPlayerDeck();

        List<Card> drawnCard = currentDeck.draw();

        drawnCardControllers = new ArrayList<>();

        for (Card card : drawnCard) {
            System.out.println(card.getName());
            try {
                FXMLLoader handCardLoader = new FXMLLoader(AetherWars.class.getResource("view/hand-card.fxml"));
                handCardLoader.setControllerFactory(c -> new HandCardController());
                StackPane handCard = handCardLoader.load();
                HandCardController controller = handCardLoader.getController();
                controller.setCard(card);
                drawnCardControllers.add(controller);
                cardSelectionBox.setAlignment(Pos.CENTER);
                HBox.setMargin(handCard, new Insets(20,80,20,80));
                cardSelectionBox.getChildren().add(handCard);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void prepareNewTurn() {
        this.turnLabel.setText(Integer.toString(GameState.getCurrentRound()));
        this.manaProgressBar.setProgress(1);
        this.setManaGrid();

        for (HandCardController c : handCardControllers) {
            EventBroker.removeObject(c);
        }

        handBox.getChildren().clear();

        this.drawnCardControllers = new ArrayList<>();
        this.handCardControllers = new ArrayList<>();

        setHand();
    }

    private void setManaGrid() {
        manaStackPane.getChildren().remove(manaGrid);

        manaGrid = new GridPane();
        manaGrid.setMinWidth(30);
        manaGrid.setMinHeight(150);
        manaGrid.setPrefWidth(30);
        manaGrid.setPrefHeight(150);
        manaGrid.setMaxWidth(30);
        manaGrid.setMaxHeight(150);
        manaGrid.setGridLinesVisible(true);
        manaGrid.setId("manaGrid");

        for (int i = 0; i < Math.min(GameState.getCurrentRound(), 10); i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight((double) 100 / Math.min(GameState.getCurrentRound(), 10));
            manaGrid.getRowConstraints().add(row);
        }

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        manaGrid.getColumnConstraints().add(column);
        manaStackPane.getChildren().add(manaGrid);
    }

    private void setHand() {
        List<Card> hand = getCurrentPlayer().getHand();

        if (hand.size() == 5) {
            hand.remove(0);
        }

        for (Card card : hand) {
            addCardToHand(card);
        }
    }

    private void addCardToHand(Card card) {
        try {
            FXMLLoader handCardLoader = new FXMLLoader(AetherWars.class.getResource("view/hand-card.fxml"));
            handCardLoader.setControllerFactory(c -> new HandCardController());
            StackPane handCard = handCardLoader.load();
            HandCardController controller = handCardLoader.getController();
            controller.setCard(card);
            handBox.setAlignment(Pos.CENTER);
            HBox.setMargin(handCard, new Insets(5,5,5,5));
            handBox.getChildren().add(handCard);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Player getCurrentPlayer() {
        if (GameState.getCurrentPlayerId() == 1) {
            return player1;
        }
        else {
            return player2;
        }
    }

    @Override
    public void publish(Event event) {
        EventBroker.sendEvent(this, event);
    }
}
