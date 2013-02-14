package bbc.forge.music.webservice;



import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import net.sf.json.JSONObject;
import bbc.forge.music.ion.ConstructUrl;
import bbc.forge.music.ion.IONData;
import bbc.forge.music.ion.JSONPage;
import bbc.forge.music.pojo.Record;
import bbc.forge.music.sitemap.SitemapXML;
import bbc.forge.music.solr.UpdateSolr;
import bbc.forge.music.test.JSON;

import org.apache.commons.logging.LogFactory;

public class Update {

	protected final Log log = LogFactory.getLog(getClass());

	private ConstructUrl constructUrl=new ConstructUrl();
	private JSONPage page=new JSONPage();
	private IONData ionUpdate=new IONData();
	private UpdateSolr updateSolr = new UpdateSolr();
	private SitemapXML sitemapXML=new SitemapXML();
		
	
	public void updateSolrFromSitemap(ArrayList <Record> records){
		updateSolr.updateAPI(records,"sitemap",true);
	}
	
	
	public ArrayList<Record> populatePojoFromSitemap(){
		return (ArrayList<Record>) sitemapXML.getSitemapXML();		
	}
	
	
	public ArrayList <Record> testPopulatePojoFromION(JSONObject json){
		
		ArrayList<Record> records=new ArrayList<Record>();		
		List<Record> record=null;		
	
		record=ionUpdate.createRecord(json);
		records.addAll(record);
	
		return records;
	}
	
	public  ArrayList <Record> populatePojoFromTestION() throws Exception{
		ArrayList<Record> records=new ArrayList<Record>();		
		List<Record> record=null;		
		JSON test=new JSON();
	
		try {

			
			JSONObject json=(test.buildTestJSON());
			
			record=ionUpdate.createRecord(json);
			records.addAll(record);

			}catch (Exception e) {
				throw new Exception("test data error  - " + e.getMessage());
			}

			return records;
		
	}
	
	
	public ArrayList <Record> populatePojoFromION(String order) throws Exception{
		
		ArrayList<Record> records=new ArrayList<Record>();		
		List<Record> record=null;		
			
		constructUrl.setPage(0);
		
		int pageCount=constructUrl.getPageCount(order);
		
		//populate the class from ion
		for ( int i = 1; i<=pageCount; i++ ) {
			String url=constructUrl.getURL(order);
			if (url==null) break;			
			
			String jsonPage;
			try {
			
				jsonPage = page.getJSONPage(url);
				JSONObject json=JSONObject.fromObject(jsonPage);
				record=ionUpdate.createRecord(json);
				records.addAll(record);

			}catch (Exception e) {
				throw new Exception("Ion connection error - " + e.getMessage());
			}
			
			
		}
		
		return records;
		
	}
	
	public ArrayList <Record> mergeIONSitemapPojo(ArrayList<Record> ion, ArrayList<Record> sitemap){
		Boolean dupe=false;
		
		ArrayList<Record> records=new ArrayList<Record>();		
		
		for(Record _ion:ion){
			
			for(Record _sitemap:sitemap){
				
				if(_ion.getMbid().equals(_sitemap.getMbid())){

					Record record=new Record();

					//check if a pojo has been already been created with the same pid
					//this will indicate a need for multi-value artists
				
					for(Record _record: records){					
						if(_record.getPid().equals(_ion.getPid())){
							_record.setArtists(_sitemap.getArtist());
							dupe=true;						
						}
					}
					
					if (!dupe){
						record.setArtists(_sitemap.getArtist());
						record.setClipTitle(_ion.getClipTitle());
						record.setMbid(_ion.getMbid());
						record.setPid(_ion.getPid());
						record.setMediaType(_ion.getMediaType());
						records.add(record);	
					}

					dupe=false;
										
				}
				
				
			}
		
		}
		
		return records;
		
	}
	
	public void updateSolrFromMergedRecords(ArrayList<Record> ion) {
		
		if (!ion.isEmpty())
			updateSolr.updateAPI(ion,"ion",false);
			//updateSolr.updateHTTP(ion, "ion");
	}

}
