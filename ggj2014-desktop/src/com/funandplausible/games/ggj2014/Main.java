package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        String workingDir = System.getProperty("user.dir");
        System.out.println(workingDir);
        cfg.title = "ggj2014";
        cfg.useGL20 = true;
        cfg.width = 800;
        cfg.height = 600;

        Content.setPrefix("assets/");

        new LwjglApplication(new GameRoot(), cfg);
    }
}
