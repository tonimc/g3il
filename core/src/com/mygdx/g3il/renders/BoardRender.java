package com.mygdx.g3il.renders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.g3il.models.Board;
import com.mygdx.g3il.models.Chip;
import com.mygdx.g3il.models.ChipType;

/**
 * Created by toni on 23/06/14.
 */
public class BoardRender implements Render<Void> {
    
    private static String IMAGE_BOARD = "texture/figure/board.png";

    private Board mBoard;

    private Texture mImage;
    Vector2 mPosition;
    private float mWidthCell;
    private float mHeightCell;

    public BoardRender(Board board, float x, float y) {
        this.mBoard = board;
        this.mImage = new Texture(Gdx.files.internal(IMAGE_BOARD));
        this.mPosition = new Vector2();

        this.mPosition.x = x - mImage.getWidth()/2;
        this.mPosition.y = y - mImage.getHeight()/2;

        this.mWidthCell = mImage.getWidth()/3;
        this.mHeightCell = mImage.getHeight()/3;

        Gdx.app.log("BOARD POSITION", mPosition.toString());
    }

    @Override
    public void draw(SpriteBatch batch, Void... params) {
        batch.draw(mImage, mPosition.x, mPosition.y);
        for (int i = 0; i < Board.LENGTH; i++) {
            for (int j = 0; j < Board.LENGTH; j++) {
                if(mBoard.getChip(i, j).getType()!=ChipType.EMPTY) {
                    renderChip(batch, i, j);
                }
            }
        }
    }

    private void renderChip(SpriteBatch batch, int i, int j) {
        ChipRender.getInstance().draw(batch, mBoard.getChip(i, j), getPositionCell(i, j));
    }

    public boolean isTouched(Vector3 touchPosition) {
        return (
            (touchPosition.x >= mPosition.x)
            && (touchPosition.x <= (mPosition.x + mImage.getWidth()))
            && (touchPosition.y >= mPosition.y)
            && (touchPosition.y <= (mPosition.y + mImage.getHeight()))
        );
    }

    private Vector2 getPositionCell(int row, int col) {
        Vector2 position = new Vector2();
        row = (mBoard.getLength()-1) - row;
        position.x = mPosition.x+(col*mWidthCell)+mWidthCell/2;
        position.y = mPosition.y+(row*mHeightCell)+mHeightCell/2;
        return position;
    }

    public int[] getTouchCell(Vector3 touchPoint) {
        int[] coordinates = {0,2};
        if(touchPoint.x<(mPosition.x+mWidthCell*1)) { coordinates[1]=0; }
        else if(touchPoint.x<(mPosition.x+mWidthCell*2)) { coordinates[1]=1; }
        if(touchPoint.y<(mPosition.y+mHeightCell*1)) { coordinates[0]=2; }
        else if(touchPoint.y<(mPosition.y+mHeightCell*2)) { coordinates[0]=1; }
        return coordinates;
    }
}
