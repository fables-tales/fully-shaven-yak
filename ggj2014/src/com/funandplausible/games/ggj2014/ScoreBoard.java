package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreBoard {
    private int mScore = 0;
    private BitmapFont mFont;
    
    public ScoreBoard() {
    	mFont = new BitmapFont(Content.file("calibri.fnt"));
    }

    public void losePoints(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("cannot lose negative points");
        }
        mScore -= i;
        if (mScore < 0) {
            mScore = 0;
        }
    }

    public void winPoints(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("cannot win negative points");
        }
        mScore += i;
        if (mScore < 0) {
            throw new IllegalStateException("total score wrapped");
        }
    }

    public int getCurrentScore() {
        return mScore;
    }
    
    public void draw(SpriteBatch sb) {
    	mFont.setColor(Color.BLACK);
    	mFont.draw(sb, "Score: " + mScore, 600, 450);
    }
}
