package com.aetherwars.board;

import com.aetherwars.card.*;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<String, CharacterCard> field;

    public Board() {
        this.field = new HashMap<>();
    }

    public Map<String, CharacterCard> getField() {
        return this.field;
    }

    public boolean isSlotValid(String slot) {
        return slot.length() == 2 &&
              (slot.charAt(0) == 'A' || slot.charAt(0) == 'B' || slot.charAt(0) == 'C' || slot.charAt(0) == 'D' || slot.charAt(0) == 'E') &&
              (slot.charAt(1) == '1' || slot.charAt(1) == '2');
    }

    public void putCharacterCard(CharacterCard c, String slot) {
        if (this.isSlotValid(slot)) {
            this.field.put(slot, c);
        }
    }

    public void putSpellCard(SpellCard s, String slot) {
        if (this.characterExists(slot)) {
            CharacterCard card = this.field.get(slot);
            // Use Spell Card berbeda untuk yg Cancelable dan non-Cancelable
            // 1. Non-Cancelable : level dan morph (PERMANEN)
            if (s.getSpellType()=="level" || s.getSpellType()=="morph") {
                s.useSpell(card);
            }
            // 2. Cancelable : potion dan swap (TEMP)
            else if (s.getSpellType()=="potion") {
                PotionSpellCard ss = (PotionSpellCard) s.clone();
                card.addPotionSpell(ss);
            }
            else if (s.getSpellType()=="swap") {
                SwapSpellCard ss = (SwapSpellCard) s.clone();
                card.addSwapSpell(ss);
            }
            this.field.replace(slot, card);
        }
    }

    public void attack(String attackerSlot, String targetSlot) {
        if (characterExists(attackerSlot) && characterExists(targetSlot) && attackerSlot.charAt(1) != targetSlot.charAt(1)) {
            CharacterCard player = this.field.get(attackerSlot);
            CharacterCard opponent = this.field.get(targetSlot);
            if (player.getType() == opponent.getType()) {
                opponent.setHp(opponent.getHp()-player.getAttack());
                player.setHp(player.getHp()-opponent.getAttack());
            } else {
                if (player.getType() == CharacterType.OVERWORLD && opponent.getType() == CharacterType.NETHER) {
                    opponent.setHp(opponent.getHp()-0.5*player.getAttack());
                    player.setHp(player.getHp()-2*opponent.getAttack());
                } else if (player.getType() == CharacterType.OVERWORLD && opponent.getType() == CharacterType.END) {
                    opponent.setHp(opponent.getHp()-2*player.getAttack());
                    player.setHp(player.getHp()-0.5*opponent.getAttack());
                } else if (player.getType() == CharacterType.NETHER && opponent.getType() == CharacterType.OVERWORLD) {
                    opponent.setHp(opponent.getHp()-2*player.getAttack());
                    player.setHp(player.getHp()-0.5*opponent.getAttack());
                } else if (player.getType() == CharacterType.NETHER && opponent.getType() == CharacterType.END) {
                    opponent.setHp(opponent.getHp()-0.5*player.getAttack());
                    player.setHp(player.getHp()-2*opponent.getAttack());
                } else if (player.getType() == CharacterType.END && opponent.getType() == CharacterType.OVERWORLD) {
                    opponent.setHp(opponent.getHp()-0.5*player.getAttack());
                    player.setHp(player.getHp()-2*opponent.getAttack());
                } else if (player.getType() == CharacterType.END && opponent.getType() == CharacterType.NETHER) {
                    opponent.setHp(opponent.getHp()-2*player.getAttack());
                    player.setHp(player.getHp()-0.5*opponent.getAttack());
                }
            }
            if (player.getHp() > 0 && opponent.getHp() <= 0) {
                this.destroy(targetSlot);
                player.addExp(opponent.getLevel());
                this.field.replace(attackerSlot, player);
            } else if (player.getHp() <= 0 && opponent.getHp() > 0) {
                this.destroy(attackerSlot);
                opponent.addExp(player.getLevel());
                this.field.replace(targetSlot, opponent);
            } else if (player.getHp() <= 0 && opponent.getHp() <= 0) {
                this.destroy(attackerSlot);
                this.destroy(targetSlot);
            } else {
                this.field.replace(attackerSlot, player);
                this.field.replace(targetSlot, opponent);
            }
        }
    }

    public void destroy(String slot) {
        this.field.remove(slot);
    }

    public boolean characterExists(String slot) {
        if (this.field.get(slot) != null) {
            return true;
        } else {
            return false;
        }
    }
}