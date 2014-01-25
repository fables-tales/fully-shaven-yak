package com.funandplausible.games.ggj2014;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.funandplausible.games.ggj2014.drawables.Hat;

public class HatGenerator {
	
	private GameServices mServices;
	
	public HatGenerator(GameServices gs) {
		mServices = gs;
	}

	public List<Hat> generateHats(int nHats) {
		List<Hat> result = new ArrayList<Hat>(nHats);
		for (int i = 0; i < nHats; i++) {
			result.add(new Hat(randomHatSprite()));
		}
		
		return result;
	}

	private Sprite randomHatSprite() {
		int spriteIndex = mServices.random().nextInt(1);
		Sprite s = mServices.contentManager().loadSprite("hat" + spriteIndex + ".png");
		s.setBounds(0, 0, 10, 10);
		s.setColor(0.0f, 1.0f, 1.0f, 1.0f);
		return s;
	}

}
