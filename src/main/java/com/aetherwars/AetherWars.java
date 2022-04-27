package com.aetherwars;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.aetherwars.card.Card;
import com.aetherwars.card.CharacterCard;
import com.aetherwars.cardlist.CardList;
import com.aetherwars.event.EventBroker;
import com.aetherwars.gamestate.GameState;
import com.aetherwars.gui.FieldCardController;
import com.aetherwars.gui.GameController;
import com.aetherwars.player.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.aetherwars.model.Type;
import com.aetherwars.model.Character;
import com.aetherwars.util.CSVReader;

public class AetherWars extends Application {

  @Override
  public void start(Stage stage) throws IOException {

    Text text = new Text();
    text.setText("Loading...");
    text.setX(50);
    text.setY(50);

    try {
      CardList.load();
      System.out.println(CardList.getById(2).getName());
      System.out.println("plist");
      text.setText("Minecraft: Aether Wars!");
    } catch (Exception e) {
      text.setText("Failed to load cards: " + e);
      System.out.println("apollo");
    }

    Group root = new Group();
    root.getChildren().add(text);

    Font.loadFont(getClass().getResourceAsStream("Minecraft.ttf"),14);
    Player player1 = new Player(1, "Steve");
    Player player2 = new Player(2, "Jack");

    GameState.setInitialGameState();


    try {
      CardList.load();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/game.fxml"));
    fxmlLoader.setControllerFactory(c -> new GameController(player1, player2));
    Parent r = fxmlLoader.load();

    GameController controller = fxmlLoader.getController();
    EventBroker.initializeNewBroker();
    EventBroker.setGameController(controller);
    controller.setInitialGame();

    Scene scene = new Scene(r);
    scene.getStylesheets().add(getClass().getResource("game-style.css").toExternalForm());

    stage.setTitle("Minecraft: Aether Wars");
    stage.setScene(scene);
    stage.show();


  }

  public static void main(String[] args) {
    launch();
  }
}
