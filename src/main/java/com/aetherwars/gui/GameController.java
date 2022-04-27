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
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
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

    public GameController(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setInitialGame() {
        player1NameLabel.setText(player1.getName());
        player2NameLabel.setText(player2.getName());
        numOfCardsLabel.setText(Integer.toString(player1.getPlayerDeck().getCardTotal()));
        setManaGrid();
        startDrawPhase();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @Override
    public void onEvent(Event event) {
        if (event instanceof ClickCardEvent) {
            ClickCardEvent clickCardEvent = (ClickCardEvent) event;
            try {
                cardInfoBox.getChildren().clear();
                FXMLLoader cardInfoLoader = new FXMLLoader(AetherWars.class.getResource("view/card-info.fxml"));
                cardInfoLoader.setControllerFactory(c -> new CardInfoController());
                HBox infoBox = cardInfoLoader.load();
                CardInfoController controller = cardInfoLoader.getController();
                controller.setCardInfo(clickCardEvent.getCard(), clickCardEvent.isCharCardInField());
                cardInfoBox.getChildren().add(infoBox);
            }
            catch (IOException e){
                e.printStackTrace();
            }

            if (GameState.getCurrentPhase() == Phase.PLAN) {
                manaStackPane.setDisable(false);
                trashButton.setDisable(false);
            }
        }
        if (event instanceof ManaUsedEvent) {

        }
        if (event instanceof AddCardToHandEvent) {

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

    public void onNexPhaseButtonClick() {
        GameState.changeToNextPhase();
        if (GameState.getCurrentPhase() == Phase.DRAW) {
            GameState.changeTurn();
            prepareNewTurn();
            startDrawPhase();
        }
        else if (GameState.getCurrentPhase() == Phase.PLAN) {

        }
        else if (GameState.getCurrentPhase() == Phase.ATTACK) {

        }
        else if (GameState.getCurrentPhase() == Phase.END) {

        }

    }

    private void startDrawPhase() {
        cardSelectionBox.setVisible(true);
        handBox.setDisable(true);

        Deck currentDeck;
        if (GameState.getCurrentPlayerId() == 1) {
            currentDeck = player1.getPlayerDeck();
        }
        else {
           currentDeck = player2.getPlayerDeck();
        }

        List<Card> drawnCard = currentDeck.draw();

        for (Card card : drawnCard) {
            System.out.println(card.getName());
            try {
                FXMLLoader handCardLoader = new FXMLLoader(AetherWars.class.getResource("view/hand-card.fxml"));
                handCardLoader.setControllerFactory(c -> new HandCardController());
                StackPane handCard = handCardLoader.load();
                HandCardController controller = handCardLoader.getController();
                controller.setCard(card);
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

    @Override
    public void publish(Event event) {
        EventBroker.sendEvent(this, event);
    }
}
