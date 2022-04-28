package com.aetherwars.player;

import com.aetherwars.card.Card;
import com.aetherwars.deck.Deck;
import com.aetherwars.gamestate.GameState;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int id;
    private final String name;
    private double hp;
    private int mana;
    private List<Card> hand;
    private Deck playerDeck;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.hp = 80;
        this.mana = 1;
        this.hand = new ArrayList<>();
        this.playerDeck = new Deck();

        List<Card> initialHand = this.playerDeck.draw();
        for (Card card : initialHand) {
            this.hand.add(card);
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getHp() {
        return this.hp;
    }

    public int getMana() {
        return this.mana;
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public Deck getPlayerDeck() {
        return this.playerDeck;
    }

    public void reduceHp(double value) {
        this.hp = Math.max(this.hp - value, 0);
    }

    public void resetMana() {
        this.mana = Math.min(GameState.getCurrentRound(), 10);
    }

    public boolean reduceMana(int value) {
        if (this.mana >= value) {
            this.mana -= value;
            return true;
        }
        return false;
    }
}
