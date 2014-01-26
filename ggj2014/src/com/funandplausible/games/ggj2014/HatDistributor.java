package com.funandplausible.games.ggj2014;

import java.util.List;
import java.util.Random;

import com.funandplausible.games.ggj2014.drawables.Hat;

public class HatDistributor {
    private final float mHatRadius;

    public HatDistributor(ConstantManager cm) {
        mHatRadius = cm.getFloat("hat_drop_radius");
    }

    public List<Hat> distributeHats(List<Hat> hats, float centreX, float centreY) {
    	Random rng = new Random();
        for (Hat h : hats) {
        	float x = (float) (mHatRadius * rng.nextGaussian());
        	float y = (float) (mHatRadius * rng.nextGaussian());
        	h.setPosition(centreX + x, centreY + y);
        }

        return hats;
    }
}
