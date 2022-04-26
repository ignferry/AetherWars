package com.aetherwars.gui;

import com.aetherwars.AetherWars;
import com.aetherwars.card.CharacterCard;
import com.aetherwars.card.CharacterType;
import com.aetherwars.event.*;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public void setCard(CharacterCard c) {
        characterCard = c;
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

    public void setCardAttributes(CharacterCard c) {
        characterCard = c;
        this.atkLabel.setText(decimalFormat().format(c.getAttack()));
        this.HPLabel.setText(decimalFormat().format(c.getHp()));
        this.levelLabel.setText("Lv. "+ c.getLevel());
        this.expLabel.setText(c.getExp() + "/" + c.getLevel());
    }

    private static DecimalFormat decimalFormat() {
        DecimalFormatSymbols d = new DecimalFormatSymbols(Locale.US);
        return new DecimalFormat("#.#", d);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onMouseEntered() {
        ScaleTransition zoomTransition = new ScaleTransition(Duration.millis(200), this.fieldCardPane);
        zoomTransition.setFromX(1);
        zoomTransition.setFromY(1);
        zoomTransition.setToX(1.2);
        zoomTransition.setByY(1.2);
        zoomTransition.play();

        publish(new HoverCardEvent(characterCard, true));
    }

    public void onMouseExited() {
        ScaleTransition zoomTransition = new ScaleTransition(Duration.millis(200), this.fieldCardPane);
        zoomTransition.setFromX(1.2);
        zoomTransition.setFromY(1.2);
        zoomTransition.setToX(1);
        zoomTransition.setByY(1);
        zoomTransition.play();

        publish(new HoverCardEvent(null, false));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof AttrChangedEvent) {
            AttrChangedEvent attrChangedEvent = (AttrChangedEvent) event;
            setCard(attrChangedEvent.getCharacterCard());
        }
    }

    public void publish(Event event) {
        EventBroker.sendEvent(this, event);
    }
}
