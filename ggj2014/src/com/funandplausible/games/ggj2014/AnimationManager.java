package com.funandplausible.games.ggj2014;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimationManager {
	private Map<String, List<Sprite>> mAnimations = new HashMap<String, List<Sprite>>();
	private int mCurrentFrame;
	private String mCurrentAnim = "";

	public void addAnimation(String name, List<Sprite> frames) {
		mAnimations.put(name, frames);
	}
	
	public void startAnimation(String name) {
		if (!mCurrentAnim.equals(name)) {
			mCurrentAnim = name;
			mCurrentFrame = 0;
		}
	}
	
	public Sprite nextFrame() {
		try {
			List<Sprite> currentAnim = mAnimations.get(mCurrentAnim);
			return currentAnim.get((mCurrentFrame++) % currentAnim.size());
		} catch (NullPointerException npe) {
			System.out.println("hi");
		}
		
		return null;
		
	}
}
