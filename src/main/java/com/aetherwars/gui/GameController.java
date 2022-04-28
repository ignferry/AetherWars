package com.aetherwars.gui;

import com.aetherwars.AetherWars;
import com.aetherwars.card.Card;
import com.aetherwars.card.CharacterCard;
import com.aetherwars.card.LevelSpellCard;
import com.aetherwars.card.SpellCard;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class GameController implements Initializable, Publisher, Subscriber {
    @FXML private Label player1NameLabel;
    @FXML private Label player2NameLabel;
    @FXML private Label player1LifeLabel;
    @FXML private Label player2LifeLabel;
    @FXML private ProgressBar player1ProgressBar;
    @FXML private ProgressBar player2ProgressBar;
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

    // Draw phase attributes
    private boolean hasDrawn;
    private List<HandCardController> drawnCardControllers;
    private List<HandCardController> handCardControllers;

    // Plan phase attributes
    private HandCardController selectedHandCardController;

    // Plan phase and attack phase attributes
    private Map<String, FieldCardController> fieldCardControllers;
    private FieldCardController selectedOwnFieldCardController;
    private List<String> occupiedSlots;
    private List<String> hasAttackedSlots;

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
        this.fieldCardControllers = new HashMap<>();
        this.occupiedSlots = new ArrayList<>();
        this.hasAttackedSlots = new ArrayList<>();

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
            selectedHandCardController = null;
            selectedOwnFieldCardController = null;

            // Menampilkan infobox untuk kartu yang bersangkutan
            ClickHandCardEvent clickHandCardEvent = (ClickHandCardEvent) event;
            Card card = clickHandCardEvent.getHandCardController().getCard();
            setCardInfo(card, false);

            // Memilih kartu untuk didraw, menambahkannya ke hand, dan mengembalikan sisanya ke deck
            if (GameState.getCurrentPhase() == Phase.DRAW && !hasDrawn && drawnCardControllers.contains(clickHandCardEvent.getHandCardController())) {
                for (HandCardController c : drawnCardControllers) {
                    EventBroker.removeObject(c);
                }

                drawnCardControllers.remove(clickHandCardEvent.getHandCardController());

                List<Card> leftOverCard = new ArrayList<>();
                for (HandCardController c : drawnCardControllers) {
                    leftOverCard.add(c.getCard());
                }
                getCurrentPlayer().getPlayerDeck().returnCard(leftOverCard);

                getCurrentPlayer().getHand().add(card);
                addCardToHand(card);

                numOfCardsLabel.setText(Integer.toString(getCurrentPlayer().getPlayerDeck().getCardTotal()));
                cardSelectionBox.getChildren().clear();
                cardSelectionBox.setVisible(false);
                hasDrawn = true;
                nextPhaseButton.setDisable(false);

                handBox.setDisable(false);
            }
            else if (GameState.getCurrentPhase() == Phase.PLAN) {
                selectedHandCardController = clickHandCardEvent.getHandCardController();

                trashButton.setDisable(false);
            }
            else {
                manaStackPane.setDisable(true);
                trashButton.setDisable(true);
            }
        }
        if (event instanceof ClickFieldCardEvent) {
            System.out.println(this.hasAttackedSlots);
            System.out.println(this.occupiedSlots);
            ClickFieldCardEvent clickFieldCardEvent = (ClickFieldCardEvent) event;
            CharacterCard card = clickFieldCardEvent.getFieldCardController().getCharacterCard();
            setCardInfo(card, true);

            if (GameState.getCurrentPhase() == Phase.PLAN || GameState.getCurrentPhase() == Phase.ATTACK) {
                // Set selectedOwnFieldCard
                if (isOwnFieldSlot(clickFieldCardEvent.getFieldCardController().getSlot())) {
                    selectedOwnFieldCardController = clickFieldCardEvent.getFieldCardController();
                    if (GameState.getCurrentPhase() == Phase.PLAN) {
                        manaStackPane.setDisable(false);
                        trashButton.setDisable(false);
                    }
                    else {
                        manaStackPane.setDisable(true);
                        trashButton.setDisable(true);
                    }
                }
                else {
                    manaStackPane.setDisable(true);
                    trashButton.setDisable(true);
                }

                // Menggunakan spell card
                if (selectedHandCardController != null) {
                    if (selectedHandCardController.getCard() instanceof SpellCard) {
                        int manacost;
                        if (selectedHandCardController.getCard() instanceof LevelSpellCard) {
                            LevelSpellCard levelSpellCard = (LevelSpellCard) selectedHandCardController.getCard();
                            manacost = levelSpellCard.getManaNeededAsPlayerLVL(card);
                        }
                        else {
                            manacost = selectedHandCardController.getCard().getManaNeeded();
                        }

                        if (getCurrentPlayer().getMana() >= manacost) {
                            SpellCard spellCard = (SpellCard) selectedHandCardController.getCard();
                            publish(new UseSpellEvent(clickFieldCardEvent.getFieldCardController(), spellCard));
                            publish(new RemoveHandCardEvent(selectedHandCardController));
                            EventBroker.removeObject(selectedHandCardController);

                            getCurrentPlayer().reduceMana(manacost);
                            setCurrentManaBar();

                            if (card.isDie()) {
                                this.occupiedSlots.remove(clickFieldCardEvent.getFieldCardController().getSlot());
                                publish(new RemoveFromFieldEvent(clickFieldCardEvent.getFieldCardController()));
                                EventBroker.removeObject(clickFieldCardEvent.getFieldCardController());
                            }

                            cardInfoBox.getChildren().clear();
                        }
                    }
                }
                // Attack
                else if (selectedOwnFieldCardController != null
                            && !isOwnFieldSlot(clickFieldCardEvent.getFieldCardController().getSlot())
                            && GameState.getCurrentPhase() == Phase.ATTACK) {
                    if (!hasAttackedSlots.contains(selectedOwnFieldCardController.getSlot())) {
                        publish(new AttackEvent(selectedOwnFieldCardController, card));
                        publish(new AttackEvent(clickFieldCardEvent.getFieldCardController(), selectedOwnFieldCardController.getCharacterCard()));

                        this.hasAttackedSlots.add(selectedOwnFieldCardController.getSlot());

                        if (card.isDie() && !selectedOwnFieldCardController.getCharacterCard().isDie()) {
                            publish(new AddExpEvent(selectedOwnFieldCardController, card.getLevel()));
                        }

                        if (selectedOwnFieldCardController.getCharacterCard().isDie() && !card.isDie()) {
                            publish(new AddExpEvent(clickFieldCardEvent.getFieldCardController(), selectedOwnFieldCardController.getCharacterCard().getLevel()));
                        }

                        if (card.isDie()) {
                            this.occupiedSlots.remove(clickFieldCardEvent.getFieldCardController().getSlot());
                            publish(new RemoveFromFieldEvent(clickFieldCardEvent.getFieldCardController()));
                            EventBroker.removeObject(clickFieldCardEvent.getFieldCardController());
                        }

                        if (selectedOwnFieldCardController.getCharacterCard().isDie()) {
                            this.occupiedSlots.remove(selectedOwnFieldCardController.getSlot());
                            publish(new RemoveFromFieldEvent(selectedOwnFieldCardController));
                            EventBroker.removeObject(selectedOwnFieldCardController);
                        }

                        selectedOwnFieldCardController = null;
                    }
                }
            }

            selectedHandCardController = null;
        }
    }

    @FXML
    public void onTrashButtonClick() {
        if (GameState.getCurrentPhase() == Phase.PLAN) {
            System.out.println(selectedHandCardController);
            if (selectedHandCardController != null) {
                publish(new RemoveHandCardEvent(selectedHandCardController));
                getCurrentPlayer().getHand().remove(selectedHandCardController.getCard());
                EventBroker.removeObject(selectedHandCardController);
                selectedHandCardController = null;
            }
            if (selectedOwnFieldCardController != null) {
                this.occupiedSlots.remove(selectedOwnFieldCardController.getSlot());
                publish(new RemoveFromFieldEvent(selectedOwnFieldCardController));
                EventBroker.removeObject(selectedOwnFieldCardController);
                selectedOwnFieldCardController = null;
            }
            cardInfoBox.getChildren().clear();
        }
    }

    @FXML
    public void onManaPaneClick() {
        if (GameState.getCurrentPhase() == Phase.PLAN
                && selectedOwnFieldCardController != null
                && getCurrentPlayer().getMana() > 0) {
            // TODO: Cek sudah level exp max atau belum
            publish(new AddExpEvent(selectedOwnFieldCardController, 1));
            getCurrentPlayer().reduceMana(1);
            setCurrentManaBar();

            setCardInfo(selectedOwnFieldCardController.getCharacterCard(), true);
        }
    }

    @FXML
    public void onNextPhaseButtonClick(MouseEvent e) {
        GameState.changeToNextPhase();
        if (GameState.getCurrentPhase() == Phase.DRAW) {
            manaStackPane.setDisable(true);
            trashButton.setDisable(true);
            nextPhaseButton.setDisable(true);

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
            manaStackPane.setDisable(true);
            trashButton.setDisable(true);
            attackRectangle.setFill(Color.rgb(0, 100, 0));
            planRectangle.setFill(Color.rgb(85,85,85));
        }
        else if (GameState.getCurrentPhase() == Phase.END) {
            endRectangle.setFill(Color.rgb(0, 100, 0));
            attackRectangle.setFill(Color.rgb(85,85,85));
        }
        cardInfoBox.getChildren().clear();
        selectedHandCardController = null;
        selectedOwnFieldCardController = null;
    }

    @FXML
    public void onSlotPaneClick(MouseEvent e) {
        // Meletakkan kartu dari hand ke field
        if (selectedHandCardController != null) {
            if (GameState.getCurrentPhase() == Phase.PLAN
                    && selectedHandCardController.getCard() instanceof CharacterCard
                    && e.getTarget() instanceof Rectangle
                    && selectedHandCardController.getCard().getManaNeeded() <= getCurrentPlayer().getMana()) {
                Rectangle r = (Rectangle) e.getTarget();
                String slotId = r.getId();
                if (isOwnFieldSlot(slotId)) {
                    CharacterCard selectedCard = (CharacterCard) selectedHandCardController.getCard();

                    try {
                        // Menambahkan kartu ke field
                        FXMLLoader fieldCardLoader = new FXMLLoader(AetherWars.class.getResource("view/field-card.fxml"));
                        fieldCardLoader.setControllerFactory(c -> new FieldCardController());
                        StackPane fieldCard = fieldCardLoader.load();
                        FieldCardController controller = fieldCardLoader.getController();
                        controller.setCard(selectedCard, slotId);
                        fieldCardControllers.put(slotId, controller);
                        for (Node n : getCurrentPlayerField().getChildren()) {
                            if (n instanceof StackPane && n.getId().equals(slotId.toLowerCase())) {
                                ((StackPane) n).getChildren().add(fieldCard);
                            }
                        }
                        this.occupiedSlots.add(slotId);

                        // Mengupdate mana player
                        getCurrentPlayer().reduceMana(selectedHandCardController.getCard().getManaNeeded());
                        setCurrentManaBar();

                        // Menghapus kartu dari hand
                        publish(new RemoveHandCardEvent(selectedHandCardController));
                        getCurrentPlayer().getHand().remove(selectedHandCardController.getCard());
                        EventBroker.removeObject(selectedHandCardController);
                        selectedHandCardController = null;
                        cardInfoBox.getChildren().clear();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @FXML
    public void onPlayerClick(MouseEvent e) {
        // Direct attack
        ImageView playerAvatar = (ImageView) e.getTarget();
        if (selectedOwnFieldCardController != null && GameState.getCurrentPhase() == Phase.ATTACK) {
            if (!hasAttackedSlots.contains(selectedOwnFieldCardController.getSlot())
                    && isOpponentFieldEmpty()
                    && playerAvatar.getId().equals("player" + getCurrentOpponent().getId() + "Avatar")) {
                getCurrentOpponent().reduceHp(selectedOwnFieldCardController.getCharacterCard().getAttack());
                setCurrentHpDisplay();

                this.hasAttackedSlots.add(selectedOwnFieldCardController.getSlot());
                selectedOwnFieldCardController = null;

                if (getCurrentOpponent().getHp() == 0) {
                    showWin(getCurrentPlayer());
                }
            }
        }
    }


    private void startDrawPhase() {
        hasDrawn = false;

        cardSelectionBox.setVisible(true);

        Deck currentDeck = getCurrentPlayer().getPlayerDeck();

        List<Card> drawnCard = currentDeck.draw();

        drawnCardControllers = new ArrayList<>();

        if (drawnCard.size() != 0) {
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
        else {
            showWin(getCurrentOpponent());
        }
    }

    private void prepareNewTurn() {
        this.turnLabel.setText(Integer.toString(GameState.getCurrentRound()));
        getCurrentPlayer().resetMana();
        this.manaProgressBar.setProgress(1);
        this.setManaGrid();

        for (HandCardController c : handCardControllers) {
            EventBroker.removeObject(c);
        }

        handBox.getChildren().clear();

        this.drawnCardControllers = new ArrayList<>();
        this.handCardControllers = new ArrayList<>();
        this.hasAttackedSlots = new ArrayList<>();

        publish(new ChangeTurnEvent());

        setHand();
    }

    private void setCurrentHpDisplay() {
        player1LifeLabel.setText(decimalFormat().format(player1.getHp()) + " / 80");
        player1ProgressBar.setProgress(player1.getHp() / 80);

        player2LifeLabel.setText(decimalFormat().format(player2.getHp()) + " / 80");
        player2ProgressBar.setProgress(player2.getHp() / 80);
    }

    private void setCurrentManaBar() {
        manaProgressBar.setProgress((float) getCurrentPlayer().getMana() / Math.min(GameState.getCurrentRound(), 10));
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

    private void setCardInfo(Card card, boolean isCardInField) {
        try {
            cardInfoBox.getChildren().clear();
            FXMLLoader cardInfoLoader = new FXMLLoader(AetherWars.class.getResource("view/card-info.fxml"));
            cardInfoLoader.setControllerFactory(c -> new CardInfoController());
            HBox infoBox = cardInfoLoader.load();
            CardInfoController controller = cardInfoLoader.getController();
            controller.setCardInfo(card, isCardInField);
            cardInfoBox.getChildren().add(infoBox);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showWin(Player player) {
        cardSelectionBox.getChildren().clear();
        cardSelectionBox.setVisible(true);
        handBox.setDisable(true);
        nextPhaseButton.setDisable(true);
        trashButton.setDisable(true);

        Label winLabel = new Label(player.getName() + " Win!");
        winLabel.getStyleClass().add("text");
        winLabel.setFont(Font.font(56));
        winLabel.setTextFill(Color.rgb(255,255,255));
    }

    private Player getCurrentPlayer() {
        if (GameState.getCurrentPlayerId() == 1) {
            return player1;
        }
        else {
            return player2;
        }
    }

    private Player getCurrentOpponent() {
        if (GameState.getCurrentPlayerId() == 1) {
            return player2;
        }
        else {
            return player1;
        }
    }

    private AnchorPane getCurrentPlayerField() {
        if (GameState.getCurrentPlayerId() == 1) {
            return player1FieldPane;
        }
        else {
            return player2FieldPane;
        }
    }

    private boolean isOwnFieldSlot(String slot) {
        List<String> l;
        if (GameState.getCurrentPlayerId() == 1) {
            l = Arrays.asList("A1", "B1", "C1", "D1", "E1");
        }
        else {
            l = Arrays.asList("A2", "B2", "C2", "D2", "E2");
        }
        return l.contains(slot);
    }

    private boolean isOpponentFieldEmpty() {
        if (GameState.getCurrentPlayerId() == 1) {
            return Collections.disjoint(Arrays.asList("A2", "B2", "C2", "D2", "E2"), this.occupiedSlots);
        }
        else {
            return Collections.disjoint(Arrays.asList("A1", "B1", "C1", "D1", "E1"), this.occupiedSlots);
        }
    }

    private static DecimalFormat decimalFormat() {
        DecimalFormatSymbols d = new DecimalFormatSymbols(Locale.US);
        return new DecimalFormat("#.#", d);
    }

    @Override
    public void publish(Event event) {
        EventBroker.sendEvent(this, event);
    }
}
