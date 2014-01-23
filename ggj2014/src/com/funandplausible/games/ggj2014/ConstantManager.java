package com.funandplausible.games.ggj2014;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;

public class ConstantManager {
	private Map<String, String> mValues = new HashMap<String, String>();

	public ConstantManager(FileHandle internal) {
		parseConstants(internal.readString());
	}
	
	public float getFloat(String key) {
		return Float.parseFloat(mValues.get(key));
	}
	
	public int getInt(String key) {
		return Integer.parseInt(mValues.get(key));
	}
	
	public boolean getBoolean(String key) {
		return mValues.get(key).equals("true") ? true : false;
	}
	
	public String getString(String key) {
		return mValues.get(key);
	}
	
	private void parseConstants(String file) {
		String[] lines = file.split("\n");
		
		for (int i = 1; i < lines.length; i++) {
			String line = lines[i];
			System.out.println(line);
			addConstantFromLine(line.split("\\|"));
		}
	}

	private void addConstantFromLine(String[] split) {
		assert split.length == 3;
		String name = split[0];
		String type = split[1];
		String value = split[2];

		validateValue(name, type, value);
		
		mValues.put(name, value);
	}
	
	private void validateValue(String name, String type, String value)  {
		if (type.equals("float")) {
			try {
				Float.parseFloat(value);
			} catch (NumberFormatException nfe) {
				throw new Error("Failed to parse float value " + name);
			}
		} else if (type.equals("int")) {
			try {
				Integer.parseInt(value);
			} catch (NumberFormatException nfe) {
				throw new Error("Failed to parse int value " + name);
			}
		} else if (type.equals("bool")) {
			if (!(value.equals("true") || value.equals("false"))) {
				throw new Error("failed to parse bool value " + name);
			}
		} else if (!type.equals("string")) {
			throw new Error("invalid type " + type + " for constant " + name);
		}
	}

}
