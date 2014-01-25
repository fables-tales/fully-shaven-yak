package com.funandplausible.games.ggj2014;

import java.util.Stack;

import com.funandplausible.games.ggj2014.drawables.Hat;

public interface HatInteractor {
	public int hatCount();
	public void loseInteraction(HatInteractor other);
	public void winInteraction(HatInteractor other);
	public Stack<Hat> getHats();
}
