package bbc.forge.music.ion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import bbc.forge.music.pojo.Record;
import bbc.forge.music.utils.GUID;

public class IONData {
	protected final Log log = LogFactory.getLog(getClass());
	public static GUID guid=new GUID();

	public List <Record> createRecord(JSONObject page){
	
		ArrayList <Record> records=new ArrayList<Record>();

		JSONArray blockArray=page.getJSONArray("blocklist");

		for (Object o : blockArray) {

			JSONObject clipObject = (JSONObject) o;

			JSONArray tagSchemesArray=clipObject.getJSONArray("tag_schemes");		

			for (Object t : tagSchemesArray){

				JSONObject tagSchemesObject=(JSONObject) t;

				if(tagSchemesObject.get("namespace").equals("musicbrainz")){

					JSONArray tagsArray=tagSchemesObject.getJSONArray("tags");

					for(Object j: tagsArray){
						JSONObject tag=(JSONObject) j;
						Record record=new Record();
						record.setClipTitle(clipObject.getString( "title" ));
						record.setPid(clipObject.getString("id"));
						record.setMediaType(clipObject.getString("media_type"));
											
						// check all items in the tags array for a guid (mbid)						
						for(Object value: tag.values()){
							if (guid.getGUID(value.toString())!=null){
								record.setMbid(guid.getGUID(value.toString()));
								break;
							}
						}
																			
						records.add(record);

					}

				}

			}


		}

		return records;

	}

}
