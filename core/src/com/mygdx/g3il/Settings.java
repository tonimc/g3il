package com.mygdx.g3il;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
    public static int MAX_SCORES = 9;
    public final static int[] highscores = new int[] {34, 21, 13, 8, 5, 3, 2, 1, 1};
    public final static String file = ".g3il";

    public static void load () {
        try {
            FileHandle filehandle = Gdx.files.external(file);

            String[] strings = filehandle.readString().split("\n");
            for (int i = 0; i < MAX_SCORES; i++) {
                highscores[i] = Integer.parseInt(strings[i+1]);
            }
        } catch (Throwable e) {
            // :( It's ok we have defaults
        }
    }

    public static void save () {
        try {
            FileHandle filehandle = Gdx.files.external(file);
            for (int i = 0; i < MAX_SCORES; i++) {
                filehandle.writeString(Integer.toString(highscores[i])+"\n", true);
            }
        } catch (Throwable e) {
        }
    }

    public static void addScore (int score) {
        for (int i = 0; i < MAX_SCORES; i++) {
            if (highscores[i] < score) {
                for (int j = (MAX_SCORES-1); j > i; j--)
                    highscores[j] = highscores[j - 1];
                highscores[i] = score;
                break;
            }
        }
    }
}
