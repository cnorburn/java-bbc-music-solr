package bbc.forge.music.webservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bbc.forge.config.PropertiesMuncher;
import bbc.forge.music.pojo.Record;
import bbc.forge.music.utils.Logging;

public class UpdateJob {
	
	final static Log log = LogFactory.getLog(UpdateJob.class);
	
	private static Update update=new Update();
	private static Logging logging=new Logging();
	public static Properties props  = PropertiesMuncher.munch();

	public static void main(){
		
		log.debug("Starting solr update....................");
		log.debug("Solr update instance: " + props.getProperty("solr.instance.local"));
				
		ArrayList<Record> sitemap_records=new ArrayList<Record>();
		ArrayList<Record> ion_records=new ArrayList<Record>();
		ArrayList<Record> ion_records1=new ArrayList<Record>();
		ArrayList<Record> ion_records2=new ArrayList<Record>();
		ArrayList<Record> full_records=new ArrayList<Record>();
			
		try {
			sitemap_records= (ArrayList<Record>) update.populatePojoFromSitemap();
		} catch (Exception e) {
			logging.logSitemapError(e.toString());
			return;
		}
		
		try {
			ion_records1= (ArrayList<Record>) update.populatePojoFromION("desc");
			ion_records2= (ArrayList<Record>) update.populatePojoFromION("asc");
			
			ion_records.addAll(ion_records1);
			ion_records.addAll(ion_records2);
			
		} catch (Exception e) {
			logging.logIonError(e.toString());
			return;
		}
		
		if(ion_records==null || ion_records.size()==0){
			logging.log(full_records,ion_records,sitemap_records);
			return;
		}
		if(sitemap_records==null || sitemap_records.size()==0){
			logging.log(full_records,ion_records,sitemap_records);
			return;
		}
		
		full_records=(ArrayList<Record>) update.mergeIONSitemapPojo(ion_records, sitemap_records);		
		
			
		update.updateSolrFromSitemap(sitemap_records);
		update.updateSolrFromMergedRecords(full_records);
		
		logging.log(full_records,ion_records,sitemap_records);

		log.debug("End solr update....................");

	}
	
	
}
