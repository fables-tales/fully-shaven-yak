package com.funandplausible.games.ggj2014;

public class ScoreBoard {
	
	int mScore = 0;

	public void losePoints(int i) {
		mScore -= i;
	}
	
	public void winPoints(int i) {
		mScore += i;
	}

}
