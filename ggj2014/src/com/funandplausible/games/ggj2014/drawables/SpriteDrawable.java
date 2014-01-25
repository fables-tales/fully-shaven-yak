package com.funandplausible.games.ggj2014.drawables;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteDrawable extends Drawable {

    private final Sprite mSprite;
    private final int mPriority;

    public SpriteDrawable(Sprite s, int priority) {
        mPriority = priority;
        mSprite = s;
    }

    public float getWidth() {
        return mSprite.getWidth();
    }

    public float getHeight() {
        return mSprite.getHeight();
    }

    @Override
    public void draw(SpriteBatch sb) {
        mSprite.draw(sb);
    }

    public void setPosition(float x, float y) {
        mSprite.setPosition(x, y);
    }

    @Override
    public int priority() {
        return mPriority;
    }

}
