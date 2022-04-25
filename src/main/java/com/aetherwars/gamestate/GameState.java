package com.aetherwars.gamestate;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private int round;
    private int currentPlayerId;
    private Phase currentPhase;
    private List<Character> hasAttacked;

    GameState() {
        this.round = 1;
        this.currentPlayerId = 1;
        this.currentPhase = Phase.DRAW;
        this.hasAttacked = new ArrayList<Character>();
    }

    public void changeTurn() {
        this.currentPlayerId = (this.currentPlayerId % 2) + 1;
    }

    public void changeToNextPhase() {
        if(this.currentPhase == Phase.DRAW) {
            this.currentPhase = Phase.PLAN;
        } else if(this.currentPhase == Phase.PLAN) {
            this.currentPhase = Phase.ATTACK;
        } else if(this.currentPhase == Phase.ATTACK) {
            this.currentPhase = Phase.END;
        } else if(this.currentPhase == Phase.END) {
            this.currentPhase = Phase.DRAW;
        }
    }

    public void resetAttackedList() {
        this.hasAttacked = new ArrayList<Character>();
    }

    public void setAttacked(char slot) {
        this.hasAttacked.add(slot);
    }

    public void hasAttacked(char slot) {
        // masih gak tau buat apa
    }
}
