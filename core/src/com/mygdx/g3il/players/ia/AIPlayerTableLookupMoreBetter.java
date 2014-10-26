package com.mygdx.g3il.players.ia;

/**
 * Created by toni on 29/06/14.
 */

import com.mygdx.g3il.models.Board;
import com.mygdx.g3il.models.ChipType;
import com.mygdx.g3il.players.AIPlayer;

/**
 * COMPUTER move based on simple table lookup of preferences
 */
public class AIPlayerTableLookupMoreBetter extends AIPlayerTableLookupBetter {

    // Moves {row, col} in order of preferences. {0, 0} at top-left corner
    protected int[][] preferredMoves = {
            {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
            {0, 1}, {1, 0}, {1, 2}, {2, 1}};

    /** constructor */
    public AIPlayerTableLookupMoreBetter(Board board, ChipType chip) {
        super(board, chip);
    }

    /** Search for the first empty cell, according to the preferences
     *  Assume that next move is available, i.e., not gameover
     *  @return int[2] of {row, col}
     */
    @Override
    public int[] move() {
        int[] move = oppositeCanWin();
        if(move!=null) {
            return move;
        }
        move = winIsPossible();
        if(move!=null) {
            return move;
        }
        for (int[] move2 : preferredMoves) {
            if (mBoard.getChip(move2[0],move2[1]).isEmpty()) {
                return move2;
            }
        }
        return null;
    }

    protected int[] oppositeCanWin() {
        if(mBoard.has2ChipsOfType(this.opponentChip)) {
            return checkWin(this.opponentChip);
        }
        return null;
    }
}
