package com.mygdx.g3il.players;

import com.mygdx.g3il.models.ChipType;

/**
 * Created by toni on 29/06/14.
 */
public abstract class Player {
    public enum PlayerType {COMPUTER, USER, NOBODY}

    private PlayerType type;
    private ChipType myChip;

    public Player(PlayerType type, ChipType myChip) {
        this.type = type;
        this.myChip = myChip;
    }

    public boolean is(PlayerType type) {
        return (this.type == type);
    }

    public boolean is(ChipType chip) {
        return (this.myChip == chip);
    }

    public ChipType getChip() {
        return this.myChip;
    }
}
