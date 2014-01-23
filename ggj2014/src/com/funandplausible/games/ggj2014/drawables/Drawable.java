package com.funandplausible.games.ggj2014.drawables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Drawable implements Comparable<Drawable> {
	private int mPriority;
	
	public Drawable(int priority) {
		mPriority = priority;
	}
	
	public int priority() {
		return mPriority;
	}
	
	public abstract void draw(SpriteBatch sb);

	@Override
	public int compareTo(Drawable o) {
		return o.mPriority - mPriority;
	}
}

		