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
            this.cardDeck = new ArrayList<>();
            Random random = new Random();
            int cardTotal = random.nextInt(21) + 40;
            for (int i = 0; i < cardTotal; i++) {
                this.cardDeck.add(CardList.getRandomCard());
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
        int cardTotal = this.getCardTotal();
        for (int i = 0; i < Math.min(3, cardTotal); i++) {
            gacha.add(this.cardDeck.remove(this.getCardTotal()-1));
        }
        return gacha;
    }

    public void shuffle() {
        Collections.shuffle(this.cardDeck);
    }

    public void returnCard(List<Card> returned) {
        int size = returned.size();
        for (int i = 0; i < size; i++) {
            this.cardDeck.add(returned.remove(0));
        }
        shuffle();
    }
}