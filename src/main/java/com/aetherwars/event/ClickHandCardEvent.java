package com.aetherwars.event;

import com.aetherwars.card.Card;
import com.aetherwars.gui.HandCardController;

public class ClickHandCardEvent implements Event{
    // Event yang terbentuk saat kartu,di hand maupun saat draw diklik

    private HandCardController handCardController;

    public ClickHandCardEvent(HandCardController handCardController) {
        this.handCardController = handCardController;

    }

    public HandCardController getHandCardController() {
        return this.handCardController;
    }
}
