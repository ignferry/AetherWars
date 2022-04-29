package com.aetherwars.card;
import com.aetherwars.cardlist.CardList;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {
    @Test
    void receiveAttack() {
        try {
            CardList.load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        CharacterCard card1 = (CharacterCard) CardList.getById(1);
        CharacterCard card2 = (CharacterCard) CardList.getById(2);
        card2.receiveAttack(card1);
        card1.receiveAttack(card2);
        assertTrue(card2.getHp() == 0);
    }
}