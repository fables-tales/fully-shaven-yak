package com.funandplausible.games.ggj2014.drawables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Drawable implements Comparable<Drawable> {
	public abstract int priority();
	public abstract void draw(SpriteBatch sb);

	@Override
	public int compareTo(Drawable o) {
		return o.priority() - priority();
	}
}

		