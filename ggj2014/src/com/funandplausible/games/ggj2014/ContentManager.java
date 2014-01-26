package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ContentManager {

    public Sprite loadSprite(String fileName) {
    	FileHandle handle = Content.file(fileName);
        Texture t = new Texture(handle, true);
        t.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        return new Sprite(t);
    }
}
