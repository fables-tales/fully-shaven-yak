package com.funandplausible.games.ggj2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) throws IOException {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        String workingDir = System.getProperty("user.dir");
        System.out.println(workingDir);
        cfg.title = "ggj2014";
        cfg.useGL20 = true;
        cfg.width = 800;
        cfg.height = 600;

        Content.setPrefix("assets/");

        File fp = new File("assets/constants.txt");
        FileReader fr = new FileReader(fp);
        char[] chars = new char[(int)fp.length()];
        fr.read(chars);
        String content = new String(chars);
        fr.close();
        new LwjglApplication(new GameRoot(content), cfg);
    }
}
