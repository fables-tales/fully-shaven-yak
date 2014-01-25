package com.funandplausible.games.ggj2014;

import java.util.HashMap;
import java.util.Map;

public class ConstantManager {
    private final Map<String, String> mValues = new HashMap<String, String>();

    public ConstantManager(String constantsText) {
        parseConstants(constantsText);
    }

    public float getFloat(String key) {
        return Float.parseFloat(getString(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public boolean getBoolean(String key) {
        return getString(key).equals("true");
    }

    public String getString(String key) {
        if (!mValues.containsKey(key)) {
            throw new Error("couldn't find constant " + key);
        }
        return mValues.get(key);
    }

    private void parseConstants(String file) {
        String[] lines = file.split("\n");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            addConstantFromLine(line.split("\\|"));
        }
    }

    private void addConstantFromLine(String[] split) {
        if (split.length != 3) {
            throw new IllegalArgumentException(
                    "split does not contain 3 elements");
        }
        String name = split[0];
        String type = split[1];
        String value = split[2];

        validateValue(name, type, value);

        mValues.put(name, value);
    }

    private void validateValue(String name, String type, String value) {
        if (type.equals("float")) {
            checkFloatFormat(name, value);
        } else if (type.equals("int")) {
            checkIntegerFormat(name, value);
        } else if (type.equals("bool")) {
            checkBoolFormat(name, value);
        } else if (!type.equals("string")) {
            throw new Error("invalid type " + type + " for constant " + name);
        }
    }

    private void checkBoolFormat(String name, String value) throws Error {
        if (!(value.equals("true") || value.equals("false"))) {
            throw new Error("failed to parse bool value " + name);
        }
    }

    private void checkIntegerFormat(String name, String value) throws Error {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            throw new Error("Failed to parse int value " + name);
        }
    }

    private void checkFloatFormat(String name, String value) throws Error {
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException nfe) {
            throw new Error("Failed to parse float value " + name);
        }
    }

}
