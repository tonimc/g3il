package com.mygdx.g3il.models;

import com.badlogic.gdx.Gdx;

/**
 * Created by toni on 17/06/14.
 */
public class Board implements Cloneable {

    public static int LENGTH = 3;

    private Chip[][] mCells;
    private int numOfChips;

    public Board() {
        mCells = new Chip[LENGTH][LENGTH];
        numOfChips = LENGTH*LENGTH;
        init();
    }

    private void init() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                mCells[i][j] = new Chip(ChipType.EMPTY);
            }
        }
    }

    public Chip getChip(int row, int col) {
        return mCells[row][col];
    }

    public void setEmpty(int row, int col) {
        mCells[row][col].setType(ChipType.EMPTY);
    }

    public boolean addChip(int[] cell, ChipType type) {
        return addChip(cell[0], cell[1], type);
    }

    public boolean addChip(int row, int col, ChipType type) {
        Gdx.app.log("MyTag", "[TOUCH] POS: ROW-> "+row+", COL-> "+col);
        if(mCells[row][col].getType()==ChipType.EMPTY) {
            mCells[row][col].setType(type);
            Gdx.app.log("MyTag", "[ADD CHIP] "+ mCells[row][col].toString());
            numOfChips--;
            return true;
        }
        return false;
    }

    public Chip[][] getCells() {
        return this.mCells;
    }

    public int getLength() {
        return LENGTH;
    }

    public boolean checkFull() {
        return (numOfChips==0);
    }

    public boolean checkWin(int row, int col) {
        return (checkWinHorizontal(row)
                || checkWinVertical(col)
                || checkWinCross(row, col));
    }

    private boolean checkWinHorizontal(int row) {
        for(int i=0; i<LENGTH-1; i++) {
            if(!mCells[row][i].equals(mCells[row][i+1])) {
                return false;
            }
        }
        return true;
    }

    private boolean checkWinVertical(int col) {
        for(int i=0; i<LENGTH-1; i++) {
            if(!mCells[i][col].equals(mCells[i+1][col])) {
                return false;
            }
        }
        return true;
    }

    private boolean checkWinCross(int row, int col) {
        if((row==col) || ((row+col)==LENGTH-1)) {
            return (checkCross1() || checkCross2());
        }
        return false;
    }

    private boolean checkCross1() {
        for(int i=0; i<LENGTH-1; i++) {
            if(!mCells[i][i].equals(mCells[i+1][i+1])) {
                return false;
            }
        }
        return true;
    }

    private boolean checkCross2() {
        for(int i=0; i<LENGTH-1; i++) {
            if(!mCells[i][LENGTH-i-1].equals(mCells[i+1][LENGTH-i-2])) {
                return false;
            }
        }
        return true;
    }

    public boolean has2ChipsOfType(ChipType type) {
        if(this.numOfChips<6) {
            return true;
        }
        if(this.numOfChips<7) {
            int num = 0;
            for (int i = 0; i < LENGTH; i++) {
                for (int j = 0; j < LENGTH; j++) {
                    if(mCells[i][j].getType() == type) {
                        num++;
                    }
                }
            }
            return (num>1);
        }
        return false;
    }

    public Board clone() {
        Board copy = new Board();
        for(int i=0; i<LENGTH; i++) {
            for(int j=0; j<LENGTH; j++) {
                copy.addChip(i, j, mCells[i][j].getType());
            }
        }
        return copy;
    }

    public String toString() {
        String out= "[ ";
        for (Chip[] row : mCells) {
            out += " < ";
            for (Chip chip : row) {
                out += " "+((chip.getType()==ChipType.EMPTY)?"-":((chip.getType()==ChipType.WHITE)?"O":"X"))+", ";
            }
            out += " > ";
        }
        return out+" ]";
    }
}