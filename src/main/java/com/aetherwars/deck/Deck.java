package com.aetherwars.deck;

import com.aetherwars.card.*;
import com.aetherwars.cardlist.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> cardDeck;
    private static CardList cardList = new CardList();
    private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";
    private static final String MORPH_SPELL_CSV_FILE_PATH = "card/data/spell_morph.csv";
    private static final String POTION_SPELL_CSV_FILE_PATH = "card/data/spell_ptn.csv";
    private static final String SWAP_SPELL_CSV_FILE_PATH = "card/data/spell_swap.csv";

    public Deck() {
        try {
            File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
            File morphCSVFile = new File(getClass().getResource(MORPH_SPELL_CSV_FILE_PATH).toURI());
            File ptnCSVFile = new File(getClass().getResource(POTION_SPELL_CSV_FILE_PATH).toURI());
            File swapCSVFile = new File(getClass().getResource(SWAP_SPELL_CSV_FILE_PATH).toURI());
            cardList.loadCards(characterCSVFile, morphCSVFile, ptnCSVFile, swapCSVFile);
            this.cardDeck = new ArrayList<>();
            Random random = new Random();
            int cardTotal = random.nextInt(21) + 40;
            for (int i = 0; i < cardTotal; i++) {
                this.cardDeck.add(cardList.getRandomCard());
            }
        } catch (Exception e) {

        }
    }

    public List<Card> getCardDeck() {
        return this.cardDeck;
    }

    public int getCardTotal() {
        return this.cardDeck.size();
    }

    public List<Card> draw() {
        List<Card> gacha = new ArrayList<>();
        for (int i = 0; i < Math.min(3, this.getCardTotal()); i++) {
            gacha.add(this.cardDeck.remove(this.getCardTotal()-1));
        }
        return gacha;
    }

    public void shuffle() {
        Collections.shuffle(this.cardDeck);
    }

    public void returnCard(List<Card> returned) {
        for (int i = 0; i < returned.size(); i++) {
            this.cardDeck.add(returned.remove(0));
        }
    }
}