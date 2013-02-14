package bbc.forge.music.utils;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bbc.forge.config.PropertiesMuncher;

public class GUID {

	public static Properties props  = PropertiesMuncher.munch();

	public String getGUID(String guid){
		
		Pattern p = Pattern.compile("[0-9a-fA-F-]{36}");
		Matcher m = p.matcher(guid);

		if(m.find())
			return m.group();
		else
			return null;
		
	}
	
}
