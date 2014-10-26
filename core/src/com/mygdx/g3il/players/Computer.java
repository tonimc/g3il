package com.mygdx.g3il.players;

import com.mygdx.g3il.models.Board;
import com.mygdx.g3il.models.ChipType;
import com.mygdx.g3il.players.ia.AIPlayerMinimax;
import com.mygdx.g3il.players.ia.AIPlayerTableLookup;
import com.mygdx.g3il.players.ia.AIPlayerTableLookupBest;
import com.mygdx.g3il.players.ia.AIPlayerTableLookupBetter;
import com.mygdx.g3il.players.ia.AIPlayerTableLookupMoreBetter;

/**
 * Created by toni on 29/06/14.
 */
public class Computer  {
    public static AIPlayer getPlayer(int level, Board board, ChipType type) {
        AIPlayer player;
        if(level<2) {
            player = new AIPlayerTableLookup(board, type);
        } else if(level<4) {
            player = new AIPlayerTableLookupBetter(board, type);
        } else if(level<6) {
            player = new AIPlayerTableLookupMoreBetter(board, type);
        } else if(level<8) {
            player = new AIPlayerTableLookupBest(board, type);
        } else {
            player = new AIPlayerMinimax(board, type);
        }
        return player;
    }
}
