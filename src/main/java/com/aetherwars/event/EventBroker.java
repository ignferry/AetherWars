package com.aetherwars.event;

import com.aetherwars.gui.GameController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventBroker {
    private static Map<Publisher, List<Subscriber>> subscribers;
    private static GameController gameController;

    public static void initializeNewBroker() {
        subscribers = new HashMap<>();
    }

    public static void sendEvent(Publisher publisher, Event event) {
        for (Subscriber s: subscribers.get(publisher)) {
            s.onEvent(event);
        }
    }

    public static void addSubscriber(Publisher publisher, Subscriber subscriber) {
        subscribers.putIfAbsent(publisher, new ArrayList<>());
        subscribers.get(publisher).add(subscriber);
    }

    public static void removeObject(Object object) {
        subscribers.remove(object);

        for (Map.Entry<Publisher, List<Subscriber>> entry : subscribers.entrySet()) {
            entry.getValue().remove(object);
        }
    }

    public static void setGameController(GameController gc) {
        gameController = gc;
    }

    public static GameController getGameController() {
        return gameController;
    }
}
