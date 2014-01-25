package com.funandplausible.games.ggj2014;

public class ScoreBoard {
    private int mScore = 0;

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
}
