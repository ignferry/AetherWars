package com.aetherwars.gui;

import com.aetherwars.AetherWars;
import com.aetherwars.card.*;
import com.aetherwars.cardlist.CardList;
import com.aetherwars.event.*;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

public class HandCardController implements Initializable, Publisher, Subscriber {
    @FXML private StackPane handCardPane;
    @FXML private Label manaCostLabel;
    @FXML private ImageView cardImageView;
    @FXML private Rectangle attributeRectangle;
    @FXML private HBox attributeBox;
    @FXML private ImageView typeImageView;

    private Card card;

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card c) {
        EventBroker.addSubscriber(this, EventBroker.getGameController());
        EventBroker.addSubscriber(EventBroker.getGameController(), this);
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
        ImageView atkImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/atk-icon.png")));
        atkImageView.setFitHeight(25);
        atkImageView.setFitWidth(25);

        Label atkLabel = new Label(decimalFormat().format(c.getAttack()));
        atkLabel.getStyleClass().add("text");
        atkLabel.setFont(Font.font(14));
        atkLabel.setEffect(dropShadowText());
        atkLabel.setTextFill(Color.rgb(255,255,255));

        ImageView hpImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/hp-icon.png")));
        hpImageView.setFitHeight(25);
        hpImageView.setFitWidth(25);

        Label hpLabel = new Label(decimalFormat().format(c.getHp()));
        hpLabel.getStyleClass().add("text");
        hpLabel.setFont(Font.font(14));
        hpLabel.setEffect(dropShadowText());
        hpLabel.setTextFill(Color.rgb(255,255,255));

        this.attributeBox.getChildren().add(atkImageView);
        this.attributeBox.getChildren().add(atkLabel);
        this.attributeBox.getChildren().add(hpImageView);
        this.attributeBox.getChildren().add(hpLabel);
    }

    private void setAttributeBox(LevelSpellCard c) {
        Label typeLabel = new Label();
        if (c.getModifierLvl() == 1) {
            typeLabel.setText("UP");
        }
        else if (c.getModifierLvl() == -1) {
            typeLabel.setText("DOWN");
        }

        typeLabel.setFont(Font.font(18));
        typeLabel.setEffect(dropShadowText());
        typeLabel.setTextFill(Color.rgb(255,255,255));

        this.attributeBox.getChildren().add(typeLabel);
    }

    private void setAttributeBox(MorphSpellCard c) {
        CharacterCard target = (CharacterCard) CardList.getById(c.getTargetId());

        ImageView targetTypeImageView = new ImageView();

        if (target.getType() == CharacterType.OVERWORLD) {
            targetTypeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/overworld-icon.png")));
        }
        else if (target.getType() == CharacterType.NETHER) {
            targetTypeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/nether-icon.png")));
        }
        else if (target.getType() == CharacterType.END) {
            targetTypeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/end-icon.png")));
        }

        targetTypeImageView.setFitHeight(20);
        targetTypeImageView.setFitWidth(20);

        ImageView atkImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/atk-icon.png")));
        atkImageView.setFitHeight(20);
        atkImageView.setFitWidth(20);

        Label atkLabel = new Label(decimalFormat().format(target.getAttack()));
        atkLabel.getStyleClass().add("text");
        atkLabel.setFont(Font.font(14));
        atkLabel.setEffect(dropShadowText());
        atkLabel.setTextFill(Color.rgb(255,255,255));

        ImageView hpImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/hp-icon.png")));
        hpImageView.setFitHeight(20);
        hpImageView.setFitWidth(20);

        Label hpLabel = new Label(decimalFormat().format(target.getHp()));
        hpLabel.getStyleClass().add("text");
        hpLabel.setFont(Font.font(14));
        hpLabel.setEffect(dropShadowText());
        hpLabel.setTextFill(Color.rgb(255,255,255));

        this.attributeBox.getChildren().add(targetTypeImageView);
        this.attributeBox.getChildren().add(atkImageView);
        this.attributeBox.getChildren().add(atkLabel);
        this.attributeBox.getChildren().add(hpImageView);
        this.attributeBox.getChildren().add(hpLabel);
    }

    private void setAttributeBox(PotionSpellCard c) {
        ImageView atkImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/atk-icon.png")));
        atkImageView.setFitHeight(15);
        atkImageView.setFitWidth(15);

        Label atkLabel = new Label(decimalFormat().format(c.getAttackChangeValue()));
        atkLabel.getStyleClass().add("text");
        atkLabel.setFont(Font.font(14));
        atkLabel.setEffect(dropShadowText());
        atkLabel.setTextFill(Color.rgb(255,255,255));

        ImageView hpImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/hp-icon.png")));
        hpImageView.setFitHeight(15);
        hpImageView.setFitWidth(15);

        Label hpLabel = new Label(decimalFormat().format(c.getHpChangeValue()));
        hpLabel.getStyleClass().add("text");
        hpLabel.setFont(Font.font(14));
        hpLabel.setEffect(dropShadowText());
        hpLabel.setTextFill(Color.rgb(255,255,255));

        ImageView durImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/duration-icon.png")));
        durImageView.setFitHeight(15);
        durImageView.setFitWidth(15);

        Label durLabel = new Label(decimalFormat().format(c.getDuration()));
        durLabel.getStyleClass().add("text");
        durLabel.setFont(Font.font(14));
        durLabel.setEffect(dropShadowText());
        durLabel.setTextFill(Color.rgb(255,255,255));

        this.attributeBox.getChildren().add(atkImageView);
        this.attributeBox.getChildren().add(atkLabel);
        this.attributeBox.getChildren().add(hpImageView);
        this.attributeBox.getChildren().add(hpLabel);
        this.attributeBox.getChildren().add(durImageView);
        this.attributeBox.getChildren().add(durLabel);
    }

    private void setAttributeBox(SwapSpellCard c) {
        ImageView durImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/duration-icon.png")));
        durImageView.setFitHeight(25);
        durImageView.setFitWidth(25);

        Label durLabel = new Label(decimalFormat().format(c.getDuration()));
        durLabel.getStyleClass().add("text");
        durLabel.setFont(Font.font(16));
        durLabel.setEffect(dropShadowText());
        durLabel.setTextFill(Color.rgb(255,255,255));

        this.attributeBox.getChildren().add(durImageView);
        this.attributeBox.getChildren().add(durLabel);
    }

    private static DecimalFormat decimalFormat() {
        DecimalFormatSymbols d = new DecimalFormatSymbols(Locale.US);
        return new DecimalFormat("#.#", d);
    }

    private static DropShadow dropShadowText() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setWidth(3);
        dropShadow.setHeight(3);
        dropShadow.setRadius(1);
        dropShadow.setSpread(1);

        return dropShadow;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onMouseEntered(MouseEvent e) {
        ScaleTransition zoomTransition = new ScaleTransition(Duration.millis(200), this.handCardPane);
        zoomTransition.setFromX(1);
        zoomTransition.setFromY(1);
        zoomTransition.setToX(1.2);
        zoomTransition.setToY(1.2);
        zoomTransition.play();
    }

    @FXML
    public void onMouseExited(MouseEvent e) {
        ScaleTransition zoomTransition = new ScaleTransition(Duration.millis(200), this.handCardPane);
        zoomTransition.setFromX(1.2);
        zoomTransition.setFromY(1.2);
        zoomTransition.setToX(1);
        zoomTransition.setToY(1);
        zoomTransition.play();
    }

    @FXML
    public void onMouseClicked(MouseEvent e) {
        publish(new ClickHandCardEvent(this));
    }

    @Override
    public void publish(Event event) {
        EventBroker.sendEvent(this, event);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof RemoveHandCardEvent) {
            RemoveHandCardEvent removeHandCardEvent = (RemoveHandCardEvent) event;
            if (removeHandCardEvent.getHandCardController() == this) {
                HBox parent = (HBox) handCardPane.getParent();
                parent.getChildren().remove(handCardPane);
            }
        }
    }
}
