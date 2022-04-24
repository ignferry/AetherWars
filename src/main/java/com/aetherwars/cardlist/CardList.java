package com.aetherwars.cardlist;

import com.aetherwars.card.*;
import com.aetherwars.model.Character;
import com.aetherwars.model.Type;
import com.aetherwars.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CardList {

    private List<Card> cards;
    private List<CharacterCard> characterCards;
    private List<MorphSpellCard> morphSpellCards;
    private List<PotionSpellCard> potionSpellCards;
    private List<SwapSpellCard> swapSpellCards;

    public CardList() {
        this.cards = new ArrayList<>();
        this.characterCards = new ArrayList<>();
        this.morphSpellCards = new ArrayList<>();
        this.potionSpellCards = new ArrayList<>();
        this.swapSpellCards = new ArrayList<>();
    }

    public void loadCards(File characterCSVFile) throws IOException, URISyntaxException {
        CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            CharacterCard newCharacter = new CharacterCard()
            Character c = new Character(row[1], row[3], Type.valueOf(row[2]));
            System.out.println(c);
        }
    }

//    public static void main(String[] args) {
//        CardList cards = new CardList();
//        try {
//            cards.loadCards();
//        } catch (Exception e) {
//            System.out.println("error gan");
//        }
//    }

    public void loadCharacterCards() {

    }

    public void loadMorphSpellCards() {

    }

    public void loadPotionSpellCards() {

    }

}
