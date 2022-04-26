package com.aetherwars.cardlist;

import com.aetherwars.AetherWars;
import com.aetherwars.card.*;
import com.aetherwars.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class CardList {
    private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";
    private static final String MORPH_SPELL_CSV_FILE_PATH = "card/data/spell_morph.csv";
    private static final String POTION_SPELL_CSV_FILE_PATH = "card/data/spell_ptn.csv";
    private static final String SWAP_SPELL_CSV_FILE_PATH = "card/data/spell_swap.csv";

    private static Map<Integer, Card> cards = new HashMap<>();
    private static List<CharacterCard> characterCards = new ArrayList<>();
    private static List<MorphSpellCard> morphSpellCards = new ArrayList<>();
    private static List<PotionSpellCard> potionSpellCards = new ArrayList<>();
    private static List<SwapSpellCard> swapSpellCards = new ArrayList<>();

    public CardList() {
    }

//    public static void loadCards() throws IOException, URISyntaxException {
//        loadCharacterCards(characterCSVFile);
//        loadMorphSpellCards(morphCSVFile);
//        loadPotionSpellCards(ptnCSVFile);
//        loadSwapSpellCards(swapCSVFile);
//        if(cards.isEmpty()) {
//            for (Card card: characterCards) {
//                cards.put(card.getId(), card);
//            }
//            for (Card card: morphSpellCards) {
//                cards.put(card.getId(), card);
//            }
//            for (Card card: potionSpellCards) {
//                cards.put(card.getId(), card);
//            }
//            for (Card card: swapSpellCards) {
//                cards.put(card.getId(), card);
//            }
//        }
//    }

    public static void load() throws IOException, URISyntaxException {
        File characterFile = new File(AetherWars.class.getResource(CHARACTER_CSV_FILE_PATH).toURI());
        File morphFile = new File(AetherWars.class.getResource(MORPH_SPELL_CSV_FILE_PATH).toURI());
        File ptnFile = new File(AetherWars.class.getResource(POTION_SPELL_CSV_FILE_PATH).toURI());
        File swapFile = new File(AetherWars.class.getResource(SWAP_SPELL_CSV_FILE_PATH).toURI());

        loadCharacterCards(characterFile);
        loadMorphSpellCards(morphFile);
        loadPotionSpellCards(ptnFile);
        loadSwapSpellCards(swapFile);
        // loadLevelSpellCards(); SOON

        if(cards.isEmpty()) {
            // memasukkan semua kartu ke dalam HashMap cards
            for (Card card : characterCards) {
                cards.put(card.getId(), card);
            }
            for (Card card : morphSpellCards) {
                cards.put(card.getId(), card);
            }
            for (Card card : potionSpellCards) {
                cards.put(card.getId(), card);
            }
            for (Card card : swapSpellCards) {
                cards.put(card.getId(), card);
            }
        }
    }

    public static void loadCards(File characterCSVFile, File morphCSVFile, File ptnCSVFile, File swapCSVFile) throws IOException, URISyntaxException {
        loadCharacterCards(characterCSVFile);
        loadMorphSpellCards(morphCSVFile);
        loadPotionSpellCards(ptnCSVFile);
        loadSwapSpellCards(swapCSVFile);
        // loadLevelSpellCards(); SOON
        if(cards.isEmpty()) {
            // memasukkan semua kartu ke dalam HashMap cards
            for (Card card : characterCards) {
                cards.put(card.getId(), card);
            }
            for (Card card : morphSpellCards) {
                cards.put(card.getId(), card);
            }
            for (Card card : potionSpellCards) {
                cards.put(card.getId(), card);
            }
            for (Card card : swapSpellCards) {
                cards.put(card.getId(), card);
            }
        }
    }

    // getter
    public static Card getById(int id) {
        return cards.get(id);
    }

    public static CharacterCard getCharCards(int id) {
        return characterCards.get(id-1);
    }

    public static Card getRandomCard() {
        List<Card> cardList = new ArrayList<Card>(cards.values());
        int randomIndex = new Random().nextInt(cardList.size());
        return cardList.get(randomIndex);
    }

    public static void loadCharacterCards(File characterCSVFile) throws IOException, URISyntaxException {
        if(characterCards.isEmpty()) {
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

                CharacterCard newCharacterCard = new CharacterCard(id, name, desc, imagePath, mana, attack, health, attackup, healthup,
                        1, 0, type);
                characterCards.add(newCharacterCard);
            }
        }
    }

    public static void loadMorphSpellCards(File morphCSVFile) throws IOException {
        if(morphSpellCards.isEmpty()) {
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
                morphSpellCards.add(newMorphSpellCard);
            }
        }
    }

    public static void loadPotionSpellCards(File ptnCSVFile) throws IOException {
        if(potionSpellCards.isEmpty()) {
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
                potionSpellCards.add(newPotionSpellCard);
            }
        }
    }

    public static void loadSwapSpellCards(File swapCSVFile) throws IOException {
        if(swapSpellCards.isEmpty()) {
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
                swapSpellCards.add(newSwapSpellCard);
            }
        }
    }
}

