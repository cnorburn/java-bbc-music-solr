package bbc.forge.music.utils;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bbc.forge.config.PropertiesMuncher;
import bbc.forge.music.pojo.Record;

public class Logging {

	protected final Log log = LogFactory.getLog(getClass());
	public static Properties props  = PropertiesMuncher.munch();

	public void log(ArrayList<Record> full_records, ArrayList<Record> ion_records, ArrayList<Record> sitemap_records ){
		
		if(ion_records==null || ion_records.size()==0){
			log.debug("No ION/Dynamite data, is it down?" );
		}else if(sitemap_records==null || sitemap_records.size()==0){
			log.debug("No Sitemap data or Sitemap not reachable - " + props.getProperty("sitemap.url") );
		}else if(full_records==null || full_records.size()==0){
			log.debug("No artists/clips merged: Sitemap entries - " + sitemap_records.size() + "   ION entries - " + ion_records.size() + "  Ion entries - :" );
		}else{
			log.debug("Sitemap entries - " + sitemap_records.size());
			log.debug("ION entries - " + ion_records.size());
			log.debug("Artist/clips merged - " + full_records.size()); 
			/*
			log.debug("Artist/clips -" );
			for(record record:full_records){
				log.debug(record.getMbid() + " --->" + record.getPid() + " ---> " + record.getClipTitle() + " ---> " + record.getArtist()) ;
			}
			*/
		}		
	}
	
	
	public void logIonError(String e){		
		log.debug("Ion connection after 2 attempts - " + e);
	}
	
	public void logSitemapError(String e){		
		log.debug("Sitemap connection problems - " + e);
	}

	
}
