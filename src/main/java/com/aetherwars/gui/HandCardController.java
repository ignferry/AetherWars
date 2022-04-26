package com.aetherwars.gui;

import com.aetherwars.AetherWars;
import com.aetherwars.card.*;
import com.aetherwars.event.Event;
import com.aetherwars.event.EventBroker;
import com.aetherwars.event.HoverCardEvent;
import com.aetherwars.event.Publisher;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

public class HandCardController implements Initializable, Publisher {
    @FXML private StackPane handCardPane;
    @FXML private Label manaCostLabel;
    @FXML private ImageView cardImageView;
    @FXML private Rectangle attributeRectangle;
    @FXML private HBox attributeBox;
    @FXML private ImageView typeImageView;

    private Card card;

    public void setCard(Card c) {
        this.card = c;

        this.cardImageView.setImage(new Image(AetherWars.class.getResourceAsStream(c.getImagePath())));
        this.manaCostLabel.setText(Integer.toString(c.getManaNeeded()));
        if (c instanceof CharacterCard) {
            CharacterCard characterCard = (CharacterCard) c;
            if (characterCard.getType() == CharacterType.OVERWORLD) {
                this.attributeRectangle.setFill(Color.rgb(64,163,2));
                this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/overworld-icon.png")));
            }
            else if (characterCard.getType() == CharacterType.NETHER) {
                this.attributeRectangle.setFill(Color.rgb(133,0,0));
                this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/nether-icon.png")));
            }
            else if (characterCard.getType() == CharacterType.END) {
                this.attributeRectangle.setFill(Color.rgb(70,0,135));
                this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/end-icon.png")));
            }
            setAttributeBox(characterCard);
        }
        else if (c instanceof SpellCard) {
            this.attributeRectangle.setFill(Color.rgb(0,235,160));
            if (c instanceof LevelSpellCard) {
                LevelSpellCard levelSpellCard = (LevelSpellCard) c;
                this.manaCostLabel.setVisible(false);
                this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/level-icon.png")));
                setAttributeBox(levelSpellCard);
            }
            else if (c instanceof MorphSpellCard) {
                MorphSpellCard morphSpellCard = (MorphSpellCard) c;
                this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/morph-icon.png")));
                setAttributeBox(morphSpellCard);
            }
            else if (c instanceof PotionSpellCard) {
                PotionSpellCard potionSpellCard = (PotionSpellCard) c;
                this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/potion-icon.png")));
                setAttributeBox(potionSpellCard);
            }
            else if (c instanceof SwapSpellCard) {
                SwapSpellCard swapSpellCard = (SwapSpellCard) c;
                this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/swap-icon.png")));
                setAttributeBox(swapSpellCard);
            }
        }
    }

    private void setAttributeBox(CharacterCard c) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setWidth(3);
        dropShadow.setHeight(3);
        dropShadow.setRadius(1);
        dropShadow.setSpread(1);

        ImageView atkImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/atk-icon.png")));
        atkImageView.setFitHeight(25);
        atkImageView.setFitWidth(25);

        Label atkLabel = new Label(decimalFormat().format(c.getAttack()));
        atkLabel.getStyleClass().add("text");
        atkLabel.setFont(Font.font(14));
        atkLabel.setEffect(dropShadow);
        atkLabel.setTextFill(Color.rgb(255,255,255));

        ImageView hpImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/hp-icon.png")));
        hpImageView.setFitHeight(25);
        hpImageView.setFitWidth(25);

        Label hpLabel = new Label(decimalFormat().format(c.getHp()));
        hpLabel.getStyleClass().add("text");
        hpLabel.setFont(Font.font(14));
        hpLabel.setEffect(dropShadow);
        hpLabel.setTextFill(Color.rgb(255,255,255));

        this.attributeBox.getChildren().add(atkImageView);
        this.attributeBox.getChildren().add(atkLabel);
        this.attributeBox.getChildren().add(hpImageView);
        this.attributeBox.getChildren().add(hpLabel);
    }

    private void setAttributeBox(LevelSpellCard c) {

    }

    private void setAttributeBox(MorphSpellCard c) {

    }

    private void setAttributeBox(PotionSpellCard c) {

    }

    private void setAttributeBox(SwapSpellCard c) {

    }

    private static DecimalFormat decimalFormat() {
        DecimalFormatSymbols d = new DecimalFormatSymbols(Locale.US);
        return new DecimalFormat("#.#", d);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onMouseEntered() {
        ScaleTransition zoomTransition = new ScaleTransition(Duration.millis(200), this.handCardPane);
        zoomTransition.setFromX(1);
        zoomTransition.setFromY(1);
        zoomTransition.setToX(1.2);
        zoomTransition.setByY(1.2);
        zoomTransition.play();

        publish(new HoverCardEvent(this.card, false));
    }

    public void onMouseExited() {
        ScaleTransition zoomTransition = new ScaleTransition(Duration.millis(200), this.handCardPane);
        zoomTransition.setFromX(1.2);
        zoomTransition.setFromY(1.2);
        zoomTransition.setToX(1);
        zoomTransition.setByY(1);
        zoomTransition.play();

        publish(new HoverCardEvent(this.card, false));
    }

    @Override
    public void publish(Event event) {
        EventBroker.sendEvent(this, event);
    }
}
