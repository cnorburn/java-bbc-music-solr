package bbc.forge.music.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSON {

	protected final Log log = LogFactory.getLog(getClass());

	public JSONObject buildTestJSON(){
		
		JSONObject json = new JSONObject();
		JSONObject tag_schemes = new JSONObject();
		JSONObject tags = new JSONObject();
		JSONObject blocklist = new JSONObject();

		JSONArray ts=new JSONArray();

		JSONArray j=new JSONArray();
		JSONObject x=new JSONObject();
		
		json.put("id","p000abc");
		json.put("title","radiohead clip 1");
		tag_schemes.put("namespace","musicbrainz");
		tag_schemes.put("tags", tags);
		x.put("name", "a74b1b7f-71a5-4011-9441-d0b5e4122711");
		j.add(0, x);
		tag_schemes.put("tags",j);
		ts.add(0,tag_schemes);
		json.put( "tag_schemes", ts);
		blocklist.accumulate("blocklist", json);
		j.clear();
		ts.clear();
						
		json.put("id","p001abc");
		json.put("title","cokto tweens clip");
		tag_schemes.put("namespace","musicbrainz");
		tag_schemes.put("tags", tags);
		x.put("name","000fc734-b7e1-4a01-92d1-f544261b43f5" );
		j.add(0, x);
		tag_schemes.put("tags",j);
		ts.add(0,tag_schemes);
		json.put( "tag_schemes", ts);
		blocklist.accumulate("blocklist", json);
		j.clear();
		ts.clear();
		
		
		json.put("id","p002abc");
		json.put("title","loolu and radheed");
		tag_schemes.put("namespace","musicbrainz");
		tag_schemes.put("tags", tags);
		x.put("name","002e9f6e-13af-4347-83c5-f5ace70e0ec4" );
		j.add(0, x);
		x.put("name","a74b1b7f-71a5-4011-9441-d0b5e4122711" );
		j.add(1, x);
		tag_schemes.put("tags",j);
		ts.add(0,tag_schemes);
		json.put( "tag_schemes", ts);
		blocklist.accumulate("blocklist", json);
		j.clear();
		ts.clear();
		
		
		json.put("id","p003abc");
		json.put("title","richard hawley clip");
		tag_schemes.put("namespace","musicbrainz");
		tag_schemes.put("tags", tags);
		x.put("name", "006f0783-c5a0-458b-a9da-f8551f7ebe77");
		j.add(0, x);
		tag_schemes.put("tags",j);
		ts.add(0,tag_schemes);
		json.put( "tag_schemes", ts);
		blocklist.accumulate("blocklist", json);
		j.clear();
		ts.clear();
		
		log.debug( blocklist.toString(2) );
				
		return  blocklist;
		
	}
	
}
