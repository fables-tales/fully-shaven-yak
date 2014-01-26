package com.funandplausible.games.ggj2014;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.funandplausible.games.ggj2014.drawables.Hat;

public class HatGenerator {
	
	public final static String[] HAT_COLORS = new String[] { "blue", "red", "green", "white", "black" };
	public final static int[] HAT_INDICES = new int[] {1,1,1,1,1};
	private static final Map<String, Color> TINT_MAP = new HashMap<String, Color>();
	
	static {
		TINT_MAP.put("blue", Color.BLUE);
		TINT_MAP.put("red", Color.RED);
		TINT_MAP.put("green", Color.GREEN);
		TINT_MAP.put("white", Color.WHITE);
		TINT_MAP.put("black", new Color(0.2f, 0.2f, 0.2f, 1.0f));
	}

    private final GameServices mServices;
    private final Map<String, List<Hat>> mHatPool;
	private Map<String, Float> mHatProbabilities;
	private float mHatsPerProbability;

    public HatGenerator(GameServices gs) {
        mServices = gs;
        mHatPool = new HashMap<String, List<Hat>>();
        mHatProbabilities = new HashMap<String, Float>();

        seedHats();
    }

    public List<Hat> generateHats(int nHats) {
        List<Hat> result = new ArrayList<Hat>(nHats);
        for (int i = 0; i < nHats; i++) {
            result.add(nextHat());
        }

        return result;
    }
    
    private Hat nextHat() {
    	int availableHats = 0;
    	for (List<Hat> hs : mHatPool.values()) {
    		availableHats += hs.size();
    	}
    	
    	if (availableHats == 0) {
    		generateNewHats();
    	}

    	List<Hat> build = new ArrayList<Hat>();
    	for (List<Hat> hs : mHatPool.values()) {
    		build.addAll(hs);
    	}
    	
    	Hat selected = build.get(mServices.random().nextInt(build.size()));
    	for (List<Hat> hs : mHatPool.values()) {
    		hs.remove(selected);
    	}

    	return selected;
    	
	}
    
    private void seedHats() {
    	float smallestProbability = 1000000;
    	for (int k = 0; k < HAT_INDICES.length; k++) {
    		String s = HAT_COLORS[k];
    		int i = HAT_INDICES[k];
    		mHatPool.put(key(s, i), new ArrayList<Hat>());
    		float hatProbability = mServices.constantManager().getFloat(key(s,i) + "_hat_probability");
    		mHatProbabilities.put(
    				key(s, i), 
    				mServices.constantManager().getFloat(key(s,i) + "_hat_probability")
    				);
    		if (hatProbability < smallestProbability) {
    			smallestProbability = hatProbability;
    		}
    	}
    	
    	mHatsPerProbability = mServices.constantManager().getFloat("min_hats_per_type")/smallestProbability;
    	
    	generateNewHats();
    }
    
    private void generateNewHats() {
    	for (String s : mHatPool.keySet()) {
    		List<Hat> hats = mHatPool.get(s);
    		int hatsToGenerate = (int)(Math.ceil(mHatsPerProbability * 
    					mServices.constantManager().getFloat(s + "_hat_probability")));
    		for (int i = 0; i < hatsToGenerate; i++) {
    			hats.add(new Hat(hatSprite(index(s)), tint(color(s)), s, mServices));
    		}
    	}
	}
    
    public Sprite tintedSprite(String key) {
    	Sprite hat = hatSprite(index(key));
    	Color color = tint(color(key));
    	hat.setColor(color);
    	return hat;
    }

	private Color tint(String color) {
		return TINT_MAP.get(color);
	}

	private int index(String s) {
		return Integer.parseInt(s.split("_")[1]);
	}
	
	private String color(String s) {
		return s.split("_")[0];
	}

	private String key(String color, int index) {
    	return color + "_" + index;
    }

	private Sprite hatSprite(int spriteIndex) {
        Sprite s = mServices.contentManager().loadSprite(
                "hat" + spriteIndex + ".png");
        s.setBounds(0, 0, 60, 60);
        return s;
    }

}
