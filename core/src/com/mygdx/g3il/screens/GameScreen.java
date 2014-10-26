package com.mygdx.g3il.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.g3il.G3il;
import com.mygdx.g3il.GameState;
import com.mygdx.g3il.Settings;
import com.mygdx.g3il.players.AIPlayer;
import com.mygdx.g3il.models.Board;
import com.mygdx.g3il.models.ChipType;
import com.mygdx.g3il.players.Computer;
import com.mygdx.g3il.players.Player;
import com.mygdx.g3il.players.Player.PlayerType;
import com.mygdx.g3il.players.User;
import com.mygdx.g3il.players.ia.AIPlayerTableLookupBetter;
import com.mygdx.g3il.renders.BoardRender;

public class GameScreen extends G3ilScreen {

    private int level = 1;
    private int lifes = 3;

    private static final Texture life = new Texture(Gdx.files.internal("texture/figure/life.png"));
    private static final Texture lifeKo = new Texture(Gdx.files.internal("texture/figure/life_ko.png"));

    private final Texture background;

    private final Texture dialog;
    private final BitmapFont dialogText;

    private final Board board;
    private final BoardRender renderBoard;

    private final Sound winSound;
    private final Sound looseSound;

    private Vector3 touchPoint;

    private PlayerType gameTurn = PlayerType.USER;
    private GameState gameSate = GameState.INIT;

    private Player user;
    private AIPlayer computer;

    public GameScreen(G3il game) {
        super(game);
        board = new Board();
        renderBoard = new BoardRender(board, camera.position.x, camera.position.y);

        background = new Texture(Gdx.files.internal("texture/background/game.png"));

        dialog = new Texture(Gdx.files.internal("texture/background/dialog.png"));
        dialogText = new BitmapFont(Gdx.files.internal("fonts/arial.fnt"));
        dialogText.scale(1);
        dialogText.setColor(0.988f, 0.686f, 0.243f, 1.0f);

        touchPoint =  new Vector3();

        winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/win.wav"));
        looseSound = Gdx.audio.newSound(Gdx.files.internal("sounds/failure.wav"));

        initPlayers();
    }

    public GameScreen(G3il game, int level, int lifes) {
        this(game);
        this.level = level;
        this.lifes = lifes;
        initPlayers();
    }

    private void initPlayers() {
        user = new User(ChipType.WHITE);
        computer = Computer.getPlayer(level, board, ChipType.BLACK);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.justTouched()) {
            if(gameSate == GameState.INIT) {
                gameSate = GameState.PLAYING;
            } else if (gameSate == GameState.PLAYING) {
                if(gameTurn == PlayerType.USER) {
                    camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                    if (renderBoard.isTouched(touchPoint)) {
                        int[] cell = renderBoard.getTouchCell(touchPoint);
                        if (board.addChip(cell, user.getChip())) {
                            checkBoard(cell);
                        }
                    }
                }
            } else if (gameSate == GameState.WIN) {
                nextScreen(new GameScreen(game, level+1, lifes));
            } else if (gameSate == GameState.TABLES) {
                nextScreen(new GameScreen(game, level, lifes-1));
            } else if (gameSate == GameState.LOOSE) {
                if(Gdx.input.getX() <= camera.position.x) {
                    Gdx.app.log("[LOOSE]", "tocas izquierda");
                    nextScreen(new GameScreen(game));
                } else {
                    Gdx.app.log("[LOOSE]", "tocas derecha");
                    nextScreen();
                }
                Settings.addScore(level);
                Settings.save();
            }
            Gdx.app.log("BOARD", board.toString());
        } else if(gameSate == GameState.PLAYING && gameTurn == PlayerType.COMPUTER) {
            int[] addChip = computer.move();
            if (addChip!=null) {
                Gdx.app.log("[ANDROID MOVE]", "ROW: "+addChip[0]+", COL: "+addChip[1]);
                board.addChip(addChip, computer.getChip());
                checkBoard(addChip);
            }
        }
    }

    private void checkBoard(int[] addChip) {
        if (board.checkWin(addChip[0], addChip[1])) {
            gameSate = (gameTurn == PlayerType.USER) ? GameState.WIN : GameState.LOOSE;
            gameTurn = PlayerType.NOBODY;
        } else if (board.checkFull()) {
            gameSate = (this.lifes>0)?GameState.TABLES:GameState.LOOSE;
            gameTurn = PlayerType.NOBODY;
        } else {
            gameTurn = (gameTurn == PlayerType.USER) ? PlayerType.COMPUTER : PlayerType.USER;
        }
    }

    @Override
    public void draw(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.disableBlending();
        game.batch.setColor(Color.WHITE);
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.enableBlending();
        renderBoard.draw(game.batch);
        drawLifes();
        if (gameSate == GameState.INIT) {
            game.batch.draw(dialog, camera.position.x-dialog.getWidth()/2, camera.position.y-dialog.getHeight()/2);
            dialogText.draw(game.batch, "LEVEL", camera.position.x - dialogText.getBounds("LEVEL").width/2, camera.position.y + 100);
            dialogText.draw(game.batch, ""+level, camera.position.x - dialogText.getSpaceWidth()/2, camera.position.y - 40);
        }
        if (gameSate == GameState.WIN) {
            String text = "You win";
            game.batch.draw(dialog, camera.position.x-dialog.getWidth()/2, camera.position.y-dialog.getHeight()/2);
            dialogText.draw(game.batch, text, camera.position.x - dialogText.getBounds(text).width/2, camera.position.y + 100);
            dialogText.scale(-1);
            text = "Touch to next level";
            dialogText.draw(game.batch, text, camera.position.x - dialogText.getBounds(text).width/2, camera.position.y - 40);
            dialogText.scale(1);
        }
        if (gameSate == GameState.TABLES) {
            String text = "You lost a life";
            game.batch.draw(dialog, camera.position.x-dialog.getWidth()/2, camera.position.y-dialog.getHeight()/2);
            dialogText.scale(-0.5f);
            dialogText.draw(game.batch, text, camera.position.x - dialogText.getBounds(text).width/2, camera.position.y + 100);
            dialogText.scale(-0.5f);
            text = "Touch to play again";
            dialogText.draw(game.batch, text, camera.position.x - dialogText.getBounds(text).width/2, camera.position.y - 40);
            dialogText.scale(1);
        }
        if (gameSate == GameState.LOOSE) {
            float y = 50;
            String text = "You loose";
            game.batch.draw(dialog, camera.position.x-dialog.getWidth()/2, y-20);
            dialogText.draw(game.batch, text, camera.position.x - dialogText.getBounds(text).width/2, y + 250);
            dialogText.scale(-1);
            text = "You get to level "+level;
            dialogText.draw(game.batch, text, camera.position.x - dialogText.getBounds(text).width/2, y + 140);
            text = "Play again!";
            dialogText.draw(game.batch, text, camera.position.x - dialogText.getBounds(text).width + 10, y + 60);
            text = "Exit";
            dialogText.draw(game.batch, text, camera.position.x + 20 + dialogText.getBounds(text).width, y + 60);
            dialogText.scale(1);
        }
        game.batch.end();
    }

    private void drawLifes() {
        float x = camera.position.x;
        float y = Gdx.graphics.getHeight()-life.getHeight();
        for(int i=1; i<4; i++) {
            Texture toDraw = (this.lifes>=i)?life:lifeKo;
            game.batch.draw(toDraw, x+(128*(i-1)), y);
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        winSound.dispose();
        looseSound.dispose();
        touchPoint = null;
        dialogText.dispose();
        dialog.dispose();
    }

    @Override
    public void nextScreen() {
        nextScreen(new MainMenuScreen(game));
        dispose();
    }
}
