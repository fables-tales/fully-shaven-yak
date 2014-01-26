package com.funandplausible.games.ggj2014;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.funandplausible.games.ggj2014.drawables.Hat;

public class ComboHandler {
    private final HatInteractor mInteractor;
    private Collection<String> mRequiredHats;
    private float mStartComboTime;
    private float mComboTimeRemaining;
    private final Sprite mSprite;
    private final List<Sprite> mHatSprites;

    public ComboHandler(HatInteractor interactor) {
        mInteractor = interactor;
        mStartComboTime = GameRoot.services().constantManager()
                .getFloat("first_combo_time");
        mSprite = GameRoot.services().contentManager().loadPackedSprite("bar");
        mHatSprites = new ArrayList<Sprite>();
        newCombo();
    }

    public boolean tick() {
        List<String> hatNames = currentHatNames();

        boolean allPresent = true;
        for (String h : requiredHats()) {
            allPresent &= hatNames.remove(h);
        }

        if (allPresent) {
            incrementScore();
            decayCombo();
            newCombo();
            playSound();
        } else {
            tickCombo();
        }

        if (comboTimeRemaining() < 0) {
            return false;
        } else {
            return true;
        }
    }

    private void playSound() {
    	GameRoot.services().soundManager().play("combo");
		
	}

	private void incrementScore() {
        ConstantManager manager = GameRoot.services().constantManager();
        int scoreBonus = 0;
        scoreBonus += manager.getInt("score_base");
        scoreBonus += (int) (manager.getInt("score_bonus_pool") * (mComboTimeRemaining / mStartComboTime));
        GameRoot.services().scoreBoard().winPoints(scoreBonus);
    }

    private float comboTimeRemaining() {
        return mComboTimeRemaining;
    }

    private void tickCombo() {
        mComboTimeRemaining -= 60.0f / 1000.0f;
    }

    private void decayCombo() {
        mStartComboTime *= GameRoot.services().constantManager()
                .getFloat("combo_time_decay");
    }

    private void newCombo() {
        mComboTimeRemaining = mStartComboTime;
        ArrayList<String> build = new ArrayList<String>();
        mHatSprites.clear();
        for (int i = 0; i < GameRoot.services().constantManager()
                .getInt("hats_in_combo"); i++) {
            int index = random().nextInt(HatGenerator.HAT_INDICES.length);
            String color = HatGenerator.HAT_COLORS[index];
            index = HatGenerator.HAT_INDICES[index];
            build.add(color + "_" + index);
            mHatSprites.add(GameRoot.services().hatGenerator()
                    .tintedSprite(color + "_" + index));
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
        mSprite.setBounds(600, 500,
                150 * mComboTimeRemaining / mStartComboTime, 25);
        mSprite.draw(spriteBatch);
        int i = 0;
        for (Sprite s : mHatSprites) {
            s.setBounds(i * 150, 500, 100, 100);
            s.draw(spriteBatch);
            i++;
        }
    }
}
