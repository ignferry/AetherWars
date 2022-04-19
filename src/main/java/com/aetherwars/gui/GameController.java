package com.aetherwars.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class GameController {
    @FXML private Label player1NameLabel;
    @FXML private Label player2NameLabel;
    @FXML private Label player1LifeLabel;
    @FXML private Label player2LifeLabel;
    @FXML private Label turnLabel;
    @FXML private AnchorPane player1FieldPane;
    @FXML private AnchorPane player2FieldPane;
    @FXML private Rectangle drawRectangle;
    @FXML private Rectangle planRectangle;
    @FXML private Rectangle attackRectangle;
    @FXML private Rectangle endRectangle;
    @FXML private Button nextPhaseButton;
    @FXML private GridPane handGrid;
    @FXML private HBox cardInfoBox;
    @FXML private GridPane manaGrid;
    @FXML private Label numOfCardsLabel;
}
