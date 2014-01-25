package com.funandplausible.games.ggj2014;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.funandplausible.games.ggj2014.drawables.Hat;

public class ComboHandler {
	private HatInteractor mInteractor;
	private Collection<String> mRequiredHats;
	private float mStartComboTime;
	private float mComboTimeRemaining;
	private Sprite mSprite;
	private List<Sprite> mHatSprites;

	public ComboHandler(HatInteractor interactor) {
		mInteractor = interactor;
		mStartComboTime = GameRoot.services().constantManager().getFloat("first_combo_time");
		mSprite = GameRoot.services().contentManager().loadSprite("bar.png");
		mHatSprites = new ArrayList<Sprite>();
		newCombo();
	}
	
	public boolean tick() {
		List<String> hatNames = currentHatNames();
		System.out.println("hats: ");
		for (String hn : currentHatNames()){
			System.out.println(hn);
		}

		System.out.println("hats required: ");
		for (String hn : requiredHats()){
			System.out.println(hn);
		}


		if (hatNames.containsAll(requiredHats())) {
			decayCombo();
			newCombo();
		} else {
			tickCombo();
		}
		System.out.println(mComboTimeRemaining);
		
		if (comboTimeRemaining() < 0) {
			return false;
		} else {
			return true;
		}
	}

	private float comboTimeRemaining() {
		return mComboTimeRemaining;
	}

	private void tickCombo() {
		mComboTimeRemaining -= 60.0f/1000.0f;
	}

	private void decayCombo() {
		mStartComboTime *= GameRoot.services().constantManager().getFloat("combo_time_decay");
	}

	private void newCombo() {
		System.out.println("new combo");
		mComboTimeRemaining = mStartComboTime;
		ArrayList<String> build = new ArrayList<String>();
		mHatSprites.clear();
		for (int i = 0; i < 2; i++) {
			String color = HatGenerator.HAT_COLORS[random().nextInt(HatGenerator.HAT_COLORS.length)];
			int index = HatGenerator.HAT_INDICES[random().nextInt(HatGenerator.HAT_INDICES.length)];
			build.add(color + "_" + index);
			mHatSprites.add(GameRoot.services().hatGenerator().tintedSprite(color + "_" + index));
		}
		mRequiredHats = build;
	}

	private List<String> currentHatNames() {
		List<String> hatNames = new ArrayList<String>();
		for (Hat h : mInteractor.getHats()) {
			hatNames.add(h.key());
		}
		return hatNames;
	}

	private Collection<String> requiredHats() {
		return mRequiredHats;
	}
	
	private Random random() {
		return GameRoot.services().random();
	}

	public void draw(SpriteBatch spriteBatch) {
		mSprite.setBounds(600, 500, 150*mComboTimeRemaining/mStartComboTime, 25);
		mSprite.draw(spriteBatch);
		int i = 0;
		for (Sprite s : mHatSprites) {
			s.setBounds(i * 150, 500, 100, 100);
			s.draw(spriteBatch);
			i++;
		}
	}
}
