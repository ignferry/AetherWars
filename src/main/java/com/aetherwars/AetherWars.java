package com.aetherwars;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.aetherwars.card.Card;
import com.aetherwars.cardlist.CardList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.aetherwars.model.Type;
import com.aetherwars.model.Character;
import com.aetherwars.util.CSVReader;

public class AetherWars extends Application {

  private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";
  private static final String MORPH_SPELL_CSV_FILE_PATH = "card/data/spell_morph.csv";
  private static final String POTION_SPELL_CSV_FILE_PATH = "card/data/spell_ptn.csv";
  private static final String SWAP_SPELL_CSV_FILE_PATH = "card/data/spell_swap.csv";

  @Override
  public void start(Stage stage) throws IOException {
    Text text = new Text();
    text.setText("Loading...");
    text.setX(50);
    text.setY(50);

    Group root = new Group();
    root.getChildren().add(text);

    Font.loadFont(getClass().getResourceAsStream("Minecraft.ttf"),14);
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/game.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    scene.getStylesheets().add(getClass().getResource("game-style.css").toExternalForm());

    stage.setTitle("Minecraft: Aether Wars");
    stage.setScene(scene);
    stage.show();

    try {
      File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
      File morphCSVFile = new File(getClass().getResource(MORPH_SPELL_CSV_FILE_PATH).toURI());
      File ptnCSVFile = new File(getClass().getResource(POTION_SPELL_CSV_FILE_PATH).toURI());
      File swapCSVFile = new File(getClass().getResource(SWAP_SPELL_CSV_FILE_PATH).toURI());

      CardList cards = new CardList();
      cards.loadCards(characterCSVFile, morphCSVFile, ptnCSVFile, swapCSVFile);

      text.setText("Minecraft: Aether Wars!");
    } catch (Exception e) {
      text.setText("Failed to load cards: " + e);
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
