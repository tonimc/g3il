package com.mygdx.g3il.players;

import com.mygdx.g3il.models.Board;
import com.mygdx.g3il.models.ChipType;

/**
 * Created by toni on 23/06/14.
 */
public abstract class AIPlayer extends Player {
    protected int ROWS = Board.LENGTH;  // number of rows
    protected int COLS = Board.LENGTH;  // number of columns

    protected Board mBoard;
    protected ChipType opponentChip;   // opponent's seed

    /** Constructor with reference to game board */
    public AIPlayer(Board board, ChipType chip) {
        super(PlayerType.COMPUTER, chip);
        this.mBoard = board;
        opponentChip = this.is(ChipType.BLACK)?ChipType.WHITE:ChipType.BLACK;
    }

    /** Abstract method to get next move. Return int[2] of {row, col} */
    public abstract int[] move();  // to be implemented by subclasses
}
