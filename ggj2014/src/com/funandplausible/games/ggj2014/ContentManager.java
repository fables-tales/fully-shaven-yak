package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ContentManager {
    private final TextureAtlas mTextureAtlas;

    public ContentManager() {
        mTextureAtlas = new TextureAtlas(Content.file("sprites/pack.atlas"));
    }

    public Sprite loadPlainSprite(String fileName) {
        FileHandle handle = Content.file(fileName);
        Texture t = new Texture(handle, true);
        t.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
        return new Sprite(t);
    }

    public Sprite loadPackedSprite(String fileName) {
        return mTextureAtlas.createSprite(fileName);
    }
}
