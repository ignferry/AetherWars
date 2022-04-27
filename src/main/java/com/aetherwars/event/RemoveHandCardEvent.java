package com.aetherwars.event;

import com.aetherwars.gui.HandCardController;

public class RemoveHandCardEvent implements Event {
    private HandCardController handCardController;

    public RemoveHandCardEvent(HandCardController handCardController) {
        this.handCardController = handCardController;
    }

    public HandCardController getHandCardController() {
        return this.handCardController;
    }
}
