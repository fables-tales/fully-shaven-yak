package com.funandplausible.games.ggj2014.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.funandplausible.games.ggj2014.Content;
import com.funandplausible.games.ggj2014.GameRoot;

public class GwtLauncher extends GwtApplication {
    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(800,
                600);
        return cfg;
    }

    @Override
    public ApplicationListener getApplicationListener() {
        Content.setPrefix("");
        return new GameRoot(getConstants());
    }

    public static native String getConstants() /*-{
		return $wnd.constants;
    }-*/;
}