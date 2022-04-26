package com.aetherwars.gui;

import com.aetherwars.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.net.URL;
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
    @FXML private GridPane manaGrid;
    @FXML private Label numOfCardsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @Override
    public void onEvent(Event event) {
        if (event instanceof HoverCardEvent) {

        }
    }

    @Override
    public void publish(Event event) {
        EventBroker.sendEvent(this, event);
    }
}
