package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public abstract class Content {
    private static String sPrefix = null;

    public static void setPrefix(String prefix) {
        assert sPrefix == null : "setting prefix twice";
        assert prefix != null : "setting prefix to null";
        sPrefix = prefix;
    }

    public static FileHandle file(String path) {
        assert sPrefix != null : "prefix not set";
        return Gdx.files.internal(sPrefix + path);
    }
}
