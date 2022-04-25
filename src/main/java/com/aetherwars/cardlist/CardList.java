package com.aetherwars.cardlist;

import com.aetherwars.card.*;
import com.aetherwars.model.Character;
import com.aetherwars.model.Type;
import com.aetherwars.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class CardList {
    private Map<Integer, Card> cards;
    private List<CharacterCard> characterCards;
    private List<MorphSpellCard> morphSpellCards;
    private List<PotionSpellCard> potionSpellCards;
    private List<SwapSpellCard> swapSpellCards;

    public CardList() {
        this.cards = new HashMap<>();
        this.characterCards = new ArrayList<>();
        this.morphSpellCards = new ArrayList<>();
        this.potionSpellCards = new ArrayList<>();
        this.swapSpellCards = new ArrayList<>();
    }

    public void loadCards(File characterCSVFile, File morphCSVFile, File ptnCSVFile, File swapCSVFile) throws IOException, URISyntaxException {
        this.loadCharacterCards(characterCSVFile);
        this.loadMorphSpellCards(morphCSVFile);
        this.loadPotionSpellCards(ptnCSVFile);
        this.loadSwapSpellCards(swapCSVFile);
        // this.loadLevelSpellCards(); SOON

        // memasukkan semua kartu ke dalam HashMap cards
        for (Card card: this.characterCards) {
            this.cards.put(card.getId(), card);
        }
        for (Card card: this.morphSpellCards) {
            this.cards.put(card.getId(), card);
        }
        for (Card card: this.potionSpellCards) {
            this.cards.put(card.getId(), card);
        }
        for (Card card: this.swapSpellCards) {
            this.cards.put(card.getId(), card);
        }
    }

    // getter
    public Card getById(int id) {
        return this.cards.get(id);
    }

    public CharacterCard getCharCards(int id) {
        return this.characterCards.get(id-1);
    }

    public Card getRandomCard() {
        List<Card> cardList = new ArrayList<Card>(cards.values());
        int randomIndex = new Random().nextInt(cardList.size());
        return cardList.get(randomIndex);
    }

    public void loadCharacterCards(File characterCSVFile) throws IOException, URISyntaxException {
        CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            int id = Integer.parseInt(row[0]);
            String name = row[1];
            CharacterType type = CharacterType.valueOf(row[2]);
            String desc = row[3];
            String imagePath = row[4];
            int attack = Integer.parseInt(row[5]);
            int health = Integer.parseInt(row[6]);
            int mana = Integer.parseInt(row[7]);
            int attackup = Integer.parseInt(row[8]);
            int healthup = Integer.parseInt(row[9]);

            CharacterCard newCharacterCard = new CharacterCard(id, name, desc, imagePath, attack, health, attackup, healthup,
                    1, 0, type);
            this.characterCards.add(newCharacterCard);
        }
    }

    public void loadMorphSpellCards(File morphCSVFile) throws IOException {
        CSVReader characterReader = new CSVReader(morphCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            int id = Integer.parseInt(row[0]);
            String name = row[1];
            String desc = row[2];
            String imagePath = row[3];
            int targetid = Integer.parseInt(row[4]);
            int mana = Integer.parseInt(row[5]);

            MorphSpellCard newMorphSpellCard = new MorphSpellCard(id, name, desc, imagePath, targetid, mana);
            this.morphSpellCards.add(newMorphSpellCard);
        }
    }

    public void loadPotionSpellCards(File ptnCSVFile) throws IOException {
        CSVReader characterReader = new CSVReader(ptnCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            int id = Integer.parseInt(row[0]);
            String name = row[1];
            String desc = row[2];
            String imagePath = row[3];
            int attack = Integer.parseInt(row[4]);
            int hp = Integer.parseInt(row[5]);
            int mana = Integer.parseInt(row[6]);
            int duration = Integer.parseInt(row[7]);

            PotionSpellCard newPotionSpellCard = new PotionSpellCard(id, name, desc, imagePath,
                    attack, hp, mana, duration);
            this.potionSpellCards.add(newPotionSpellCard);
        }
    }

    public void loadSwapSpellCards(File swapCSVFile) throws IOException {
        CSVReader characterReader = new CSVReader(swapCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            int id = Integer.parseInt(row[0]);
            String name = row[1];
            String desc = row[2];
            String imagePath = row[3];
            int duration = Integer.parseInt(row[4]);
            int mana = Integer.parseInt(row[5]);

            SwapSpellCard newSwapSpellCard = new SwapSpellCard(id, name, desc, imagePath, mana, duration);
            this.swapSpellCards.add(newSwapSpellCard);
        }
    }
}

