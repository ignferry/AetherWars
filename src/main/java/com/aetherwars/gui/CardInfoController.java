package com.aetherwars.gui;

import com.aetherwars.AetherWars;
import com.aetherwars.card.*;
import com.aetherwars.cardlist.CardList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

public class CardInfoController implements Initializable {
    @FXML private ImageView cardImageView;
    @FXML private Label levelLabel;
    @FXML private ProgressBar xpProgressBar;
    @FXML private StackPane xpPane;
    @FXML private HBox levelExpBox;
    @FXML private GridPane cardInfoGrid;
    @FXML private ImageView typeImageView;
    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;

    public void setCardInfo(Card c, boolean isCharacterCardInField) {
        this.cardImageView.setImage(new Image(AetherWars.class.getResourceAsStream(c.getImagePath())));
        this.nameLabel.setText(c.getName());
        this.descriptionLabel.setText(c.getDescription());

        if (c instanceof CharacterCard) {
            CharacterCard characterCard = (CharacterCard) c;
            this.setAttributeInfo(characterCard);
            if (isCharacterCardInField) {
                this.levelLabel.setText(Integer.toString(characterCard.getLevel()));
                this.xpProgressBar.setProgress((double) characterCard.getExp() / (characterCard.getLevel() * 2 - 1));
                GridPane xpGrid = new GridPane();
                xpGrid.setMinWidth(150);
                xpGrid.setMinHeight(20);
                xpGrid.setPrefWidth(150);
                xpGrid.setPrefHeight(20);
                xpGrid.setMaxWidth(150);
                xpGrid.setMaxHeight(20);
                xpGrid.setGridLinesVisible(true);
                for (int i = 0; i < characterCard.getLevel() * 2 - 1; i++) {
                    ColumnConstraints column = new ColumnConstraints();
                    column.setPercentWidth((double) 100 / characterCard.getLevel());
                    xpGrid.getColumnConstraints().add(column);
                }
                RowConstraints row = new RowConstraints();
                row.setPercentHeight(100);
                xpGrid.getRowConstraints().add(row);
                xpPane.getChildren().add(xpGrid);
            }
            else {
                this.levelExpBox.setVisible(false);
            }
        }
        else {
            this.levelExpBox.setVisible(false);
            if (c instanceof LevelSpellCard) {
                LevelSpellCard levelSpellCard = (LevelSpellCard) c;
                this.setAttributeInfo(levelSpellCard);
            }
            else if (c instanceof MorphSpellCard) {
                MorphSpellCard morphSpellCard = (MorphSpellCard) c;
                this.setAttributeInfo(morphSpellCard);
            }
            else if (c instanceof PotionSpellCard) {
                PotionSpellCard potionSpellCard = (PotionSpellCard) c;
                this.setAttributeInfo(potionSpellCard);
            }
            else if (c instanceof SwapSpellCard) {
                SwapSpellCard swapSpellCard = (SwapSpellCard) c;
                this.setAttributeInfo(swapSpellCard);
            }
        }
    }

    private void setAttributeInfo(CharacterCard c) {
        if (c.getType() == CharacterType.OVERWORLD) {
            this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/overworld-icon.png")));
        }
        else if (c.getType() == CharacterType.NETHER) {
            this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/nether-icon.png")));
        }
        else if (c.getType() == CharacterType.END) {
            this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/end-icon.png")));
        }

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

        for (int i = 0; i < 1; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            this.cardInfoGrid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(50);
            this.cardInfoGrid.getRowConstraints().add(row);
        }

        this.cardInfoGrid.add(atkImageView, 0, 0);
        this.cardInfoGrid.add(atkLabel, 1, 0);
        this.cardInfoGrid.add(hpImageView, 0, 1);
        this.cardInfoGrid.add(hpLabel, 1,1);

        GridPane.setHalignment(atkImageView, HPos.CENTER);
        GridPane.setHalignment(atkLabel, HPos.CENTER);
        GridPane.setHalignment(hpImageView, HPos.CENTER);
        GridPane.setHalignment(hpLabel, HPos.CENTER);
    }

    private void setAttributeInfo(LevelSpellCard c) {
        this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/level-icon.png")));

        Label typeLabel = new Label();
        if (c.getModifierLvl() == -1) {
            typeLabel.setText("DOWN");
        }
        else if (c.getModifierLvl() == 1) {
            typeLabel.setText("UP");
        }

        typeLabel.setFont(Font.font(16));
        typeLabel.setEffect(dropShadowText());
        typeLabel.setTextFill(Color.rgb(255,255,255));

        this.cardInfoGrid.add(typeLabel, 0, 0);

        GridPane.setHalignment(typeLabel, HPos.CENTER);
    }

    private void setAttributeInfo(MorphSpellCard c) {
        this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/morph-icon.png")));

        CharacterCard target = (CharacterCard) CardList.getById(c.getTargetId());

        ImageView atkImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/atk-icon.png")));
        atkImageView.setFitHeight(25);
        atkImageView.setFitWidth(25);

        Label atkLabel = new Label(decimalFormat().format(target.getAttack()));
        atkLabel.getStyleClass().add("text");
        atkLabel.setFont(Font.font(14));
        atkLabel.setEffect(dropShadowText());
        atkLabel.setTextFill(Color.rgb(255,255,255));

        ImageView hpImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/hp-icon.png")));
        hpImageView.setFitHeight(25);
        hpImageView.setFitWidth(25);

        Label hpLabel = new Label(decimalFormat().format(target.getHp()));
        hpLabel.getStyleClass().add("text");
        hpLabel.setFont(Font.font(14));
        hpLabel.setEffect(dropShadowText());
        hpLabel.setTextFill(Color.rgb(255,255,255));

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
        targetTypeImageView.setFitHeight(25);
        targetTypeImageView.setFitWidth(25);

        for (int i = 0; i < 1; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            this.cardInfoGrid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 2; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(33.3);
            this.cardInfoGrid.getRowConstraints().add(row);
        }

        this.cardInfoGrid.add(atkImageView, 0, 0);
        this.cardInfoGrid.add(atkLabel, 1, 0);
        this.cardInfoGrid.add(hpImageView, 0, 1);
        this.cardInfoGrid.add(hpLabel, 1,1);
        this.cardInfoGrid.add(targetTypeImageView, 0,2, 2, 1);

        GridPane.setHalignment(atkImageView, HPos.CENTER);
        GridPane.setHalignment(atkLabel, HPos.CENTER);
        GridPane.setHalignment(hpImageView, HPos.CENTER);
        GridPane.setHalignment(hpLabel, HPos.CENTER);
        GridPane.setHalignment(targetTypeImageView, HPos.CENTER);
    }

    private void setAttributeInfo(PotionSpellCard c) {
        this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/potion-icon.png")));

        ImageView atkImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/atk-icon.png")));
        atkImageView.setFitHeight(25);
        atkImageView.setFitWidth(25);

        Label atkLabel = new Label(decimalFormat().format(c.getAttackChangeValue()));
        atkLabel.getStyleClass().add("text");
        atkLabel.setFont(Font.font(14));
        atkLabel.setEffect(dropShadowText());
        atkLabel.setTextFill(Color.rgb(255,255,255));

        ImageView hpImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/hp-icon.png")));
        hpImageView.setFitHeight(25);
        hpImageView.setFitWidth(25);

        Label hpLabel = new Label(decimalFormat().format(c.getHpChangeValue()));
        hpLabel.getStyleClass().add("text");
        hpLabel.setFont(Font.font(14));
        hpLabel.setEffect(dropShadowText());
        hpLabel.setTextFill(Color.rgb(255,255,255));

        ImageView durImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/duration-icon.png")));
        durImageView.setFitHeight(25);
        durImageView.setFitWidth(25);

        Label durLabel = new Label(decimalFormat().format(c.getDuration()));
        durLabel.getStyleClass().add("text");
        durLabel.setFont(Font.font(14));
        durLabel.setEffect(dropShadowText());
        durLabel.setTextFill(Color.rgb(255,255,255));

        for (int i = 0; i < 1; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            this.cardInfoGrid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 2; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(33.3);
            this.cardInfoGrid.getRowConstraints().add(row);
        }

        this.cardInfoGrid.add(atkImageView, 0, 0);
        this.cardInfoGrid.add(atkLabel, 1, 0);
        this.cardInfoGrid.add(hpImageView, 0, 1);
        this.cardInfoGrid.add(hpLabel, 1,1);
        this.cardInfoGrid.add(durImageView, 0, 2);
        this.cardInfoGrid.add(durLabel, 1,2);

        GridPane.setHalignment(atkImageView, HPos.CENTER);
        GridPane.setHalignment(atkLabel, HPos.CENTER);
        GridPane.setHalignment(hpImageView, HPos.CENTER);
        GridPane.setHalignment(hpLabel, HPos.CENTER);
        GridPane.setHalignment(durImageView, HPos.CENTER);
        GridPane.setHalignment(durLabel, HPos.CENTER);
    }

    private void setAttributeInfo(SwapSpellCard c) {
        this.typeImageView.setImage(new Image(AetherWars.class.getResourceAsStream("gameImages/swap-icon.png")));

        ImageView durImageView = new ImageView(new Image(AetherWars.class.getResourceAsStream("gameImages/duration-icon.png")));
        durImageView.setFitHeight(25);
        durImageView.setFitWidth(25);

        Label durLabel = new Label(decimalFormat().format(c.getDuration()));
        durLabel.getStyleClass().add("text");
        durLabel.setFont(Font.font(14));
        durLabel.setEffect(dropShadowText());
        durLabel.setTextFill(Color.rgb(255,255,255));

        for (int i = 0; i < 1; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            this.cardInfoGrid.getColumnConstraints().add(column);
        }

        this.cardInfoGrid.add(durImageView, 0, 0);
        this.cardInfoGrid.add(durLabel, 1,0);

        GridPane.setHalignment(durImageView, HPos.CENTER);
        GridPane.setHalignment(durLabel, HPos.CENTER);
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
}
