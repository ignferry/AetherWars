package com.aetherwars.gamestate;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private static int turn;
    private static int currentPlayerId;
    private static Phase currentPhase;
    private static List<Character> hasAttacked;

    public static void setInitialGameState() {
        turn = 1;
        currentPlayerId = 1;
        currentPhase = Phase.DRAW;
        hasAttacked = new ArrayList<>();
    }

    public static void changeTurn() {
        turn++;
        currentPlayerId = (currentPlayerId % 2) + 1;
        currentPhase = Phase.DRAW;
    }

    public static void changeToNextPhase() {
        if(currentPhase == Phase.DRAW) {
            currentPhase = Phase.PLAN;
        } else if(currentPhase == Phase.PLAN) {
            currentPhase = Phase.ATTACK;
        } else if(currentPhase == Phase.ATTACK) {
            currentPhase = Phase.END;
        } else if(currentPhase == Phase.END) {
            currentPhase = Phase.DRAW;
        }
    }

    public static int getCurrentRound() {
        return (int) Math.ceil((double) turn / 2);
    }

    public static int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public static Phase getCurrentPhase() {
        return currentPhase;
    }

    public static void resetAttackedList() {
        hasAttacked = new ArrayList<Character>();
    }

    public static void setAttacked(char slot) {
        hasAttacked.add(slot);
    }

    public static boolean hasAttacked(char slot) {
        return hasAttacked.contains(slot);
    }
}
