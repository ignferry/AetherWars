package com.aetherwars.gui;

import com.aetherwars.AetherWars;
import com.aetherwars.card.CharacterCard;
import com.aetherwars.card.CharacterType;
import com.aetherwars.card.SpellCard;
import com.aetherwars.event.*;
import com.aetherwars.gamestate.GameState;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

public class FieldCardController implements Initializable, Publisher, Subscriber {
    @FXML private StackPane fieldCardPane;
    @FXML private ImageView typeImageView;
    @FXML private Label atkLabel;
    @FXML private Label HPLabel;
    @FXML private Label levelLabel;
    @FXML private Label expLabel;
    @FXML private ImageView cardImageView;

    private CharacterCard characterCard;
    private String slot;

    public void setCard(CharacterCard c, String slot) {
        EventBroker.addSubscriber(this, EventBroker.getGameController());
        EventBroker.addSubscriber(EventBroker.getGameController(), this);
        this.characterCard = c;
        this.slot = slot;

        setCardAttributes(c);
    }

    public void setCardAttributes(CharacterCard c) {
        this.atkLabel.setText(decimalFormat().format(c.getAttack()));
        this.HPLabel.setText(decimalFormat().format(c.getHp()));
        this.levelLabel.setText("Lv. "+ c.getLevel());
        this.expLabel.setText(c.getExp() + "/" + (c.getLevel() * 2 - 1));

        this.cardImageView.setImage(new Image(AetherWars.class.getResourceAsStream(c.getImagePath())));

        if (c.getType() == CharacterType.OVERWORLD) {
            this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/overworld-icon.png")));
        }
        else if (c.getType() == CharacterType.NETHER) {
            this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/nether-icon.png")));
        }
        else if (c.getType() == CharacterType.END) {
            this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/end-icon.png")));
        }
    }

    private static DecimalFormat decimalFormat() {
        DecimalFormatSymbols d = new DecimalFormatSymbols(Locale.US);
        return new DecimalFormat("#.#", d);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onMouseEntered(MouseEvent e) {
        ScaleTransition zoomTransition = new ScaleTransition(Duration.millis(200), this.fieldCardPane);
        zoomTransition.setFromX(1);
        zoomTransition.setFromY(1);
        zoomTransition.setToX(1.2);
        zoomTransition.setToY(1.2);
        zoomTransition.play();
    }

    @FXML
    public void onMouseExited(MouseEvent e) {
        ScaleTransition zoomTransition = new ScaleTransition(Duration.millis(200), this.fieldCardPane);
        zoomTransition.setFromX(1.2);
        zoomTransition.setFromY(1.2);
        zoomTransition.setToX(1);
        zoomTransition.setToY(1);
        zoomTransition.play();
    }

    @FXML
    public void onMouseClick(MouseEvent e) {
        publish(new ClickFieldCardEvent(this));
    }

    public CharacterCard getCharacterCard() {
        return this.characterCard;
    }

    public String getSlot() {
        return this.slot;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof RemoveFromFieldEvent) {
            RemoveFromFieldEvent removeFromFieldEvent = (RemoveFromFieldEvent) event;
            if (removeFromFieldEvent.getFieldCardController() == this) {
                StackPane parent = (StackPane) fieldCardPane.getParent();
                parent.getChildren().remove(fieldCardPane);
            }
        }
        if (event instanceof UseSpellEvent) {
            UseSpellEvent useSpellEvent = (UseSpellEvent) event;
            if (useSpellEvent.getFieldCardController() == this) {
                SpellCard spellCard = useSpellEvent.getSpellCard();
                if (!useSpellEvent.getFieldCardController().getSlot().contains(Integer.toString(GameState.getCurrentPlayerId()))) {
                    spellCard.setDuration(spellCard.getDuration() - 0.5);
                }
                useSpellEvent.getSpellCard().useSpell(this.characterCard);
                setCardAttributes(this.characterCard);
            }
        }
        if (event instanceof AddExpEvent) {
            AddExpEvent addExpEvent = (AddExpEvent) event;
            if (addExpEvent.getFieldCardController() == this) {
                this.characterCard.addExp(1);
                setCardAttributes(this.characterCard);
            }
        }
        if (event instanceof AttackEvent) {
            AttackEvent attackEvent = (AttackEvent) event;
            if (attackEvent.getDefender() == this) {
                this.characterCard.receiveAttack(attackEvent.getAttacker());
                setCardAttributes(this.characterCard);
            }
        }
        if (event instanceof ChangeTurnEvent) {
            System.out.println("Duration of " + this.slot + " reduced");
            this.characterCard.reduceDuration();
            setCardAttributes(this.characterCard);
        }
    }

    public void publish(Event event) {
        EventBroker.sendEvent(this, event);
    }
}
