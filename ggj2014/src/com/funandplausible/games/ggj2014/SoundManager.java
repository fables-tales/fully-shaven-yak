package com.funandplausible.games.ggj2014;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private final Map<String, Sound> mSFX = new HashMap<String, Sound>();
    private final Random mRNG = new Random();

    public void play(String sound) {
        Sound snd = getSound(sound);
        float vol = 0.8f + 0.2f * mRNG.nextFloat();
        float pitch = 0.9f + 0.24f * mRNG.nextFloat();
        snd.play(vol, pitch, 0.0f);
    }
    
    public void loopMusic(String name, float volume) {
    	if (GameRoot.services().constantManager().getBoolean("disable_music")) {
    		return;
    	}
    	Music m = loadMusic(name);
    	m.setLooping(true);
    	m.setVolume(volume);
    	m.play();
    }

    private Sound getSound(String sound) {
        Sound snd = mSFX.get(sound);
        if (snd == null) {
            snd = loadSound(sound);
            mSFX.put(sound, snd);
        }
        return snd;
    }
    
    private Music loadMusic(String name) {
    	return Gdx.audio.newMusic(Content.file(name + ".wav"));
    }

    private Sound loadSound(String sound) {
        return Gdx.audio.newSound(Content.file(sound + ".wav"));
    }
}
