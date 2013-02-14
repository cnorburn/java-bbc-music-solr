package bbc.forge.music.solr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import bbc.forge.config.PropertiesMuncher;
import bbc.forge.music.pojo.Record;

public class UpdateSolr {

	protected final Log log = LogFactory.getLog(getClass());
	public final static String RESOURCES_DIR = "src/main/resources/";
	public static Properties props  = PropertiesMuncher.munch();

	public String updateAPI(ArrayList<Record> records, String type, Boolean delete){

		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		SolrServer server = getSolrServer();

		try {

			if (delete) server.deleteByQuery( "*:*" );

			for(Record record:records){

				SolrInputDocument doc=new SolrInputDocument();

				if (type=="sitemap"){
					doc.addField("id", record.getMbid());

					doc.addField("artist_name", record.getArtist());
					doc.addField("type","artist");

					float f=Float.valueOf(record.getPriority()).floatValue();
					f=f+1.0f;										
					doc.setDocumentBoost(f);


				}else if(type=="ion"){

					doc.addField("id", record.getPid());
					doc.addField("mbid", record.getMbid());
					doc.addField("clip_title", record.getClipTitle());
					doc.addField("media_type", record.getMediaType());
					doc.addField("artist_name", record.getArtists());
					doc.addField("type","clip");
				}

				docs.add(doc);

			}

			server.add(docs);

			server.commit();

		} catch (Exception e) {
			log.debug("Exception   " + e.getMessage());
			e.printStackTrace();
			return null;			
		}

		return "success";

	}


	public SolrServer getSolrServer(){

		try {
			return new CommonsHttpSolrServer(props.getProperty("solr.instance.local"));
		} catch (Exception e) {
			log.debug("    Exception   " + e.getMessage());
			e.printStackTrace();
			return null;			
		}

	}


	//HTTP update was developed in case the solr api update didn't work on Forge
	@Deprecated

	public String updateHTTP(ArrayList<Record> records, String type, String uri) {

		PostMethod method = new PostMethod(uri);
		method.addRequestHeader("content-type", "text/xml; charset=UTF-8");

		log.debug("post method set");

		HttpClient client = new HttpClient();

		log.debug("httpclient created");

		int loop=0;

		try{
			StringBuffer delete=new StringBuffer();
			delete.append("<delete><query>*:*</query></delete>");

			method.setRequestBody(delete.toString());
			client.executeMethod(method);		


			StringBuffer commit=new StringBuffer();
			commit.append("<commit></commit>");
			client.executeMethod(method);		



		}catch (Exception e) {
			log.debug("!!!!!!!!!!!!!! delete exception   " + e.getMessage());
			e.printStackTrace();
			return null;			
		}		

		StringBuffer xml=new StringBuffer();
		xml.append("<add>");		

		//build up some xml and update
		for(Record record:records){

			if (type=="sitemap"){

				xml.append("<doc>");
				xml.append("<field name=\"id\">").append(record.getMbid()).append("</field>");
				xml.append("<field name=\"artist_name\">").append(record.getArtist()).append("</field>");
				xml.append("</doc>");

				loop++;
				//if(loop==500) break;

			}else if(type=="ion"){

			}

		}

		xml.append("</add><commit></commit>");

		try {

			//System.out.println(xml.toString());


			method.setRequestBody(xml.toString());

			log.debug("about to execute post");

			int result = client.executeMethod(method);

			log.debug("closing connection");

			method.releaseConnection();


		} catch (Exception e) {
			log.debug("    Exception   " + e.getMessage());
			e.printStackTrace();
			return null;			
		}

		try{

			method.setRequestBody("<commit></commit>");
			client.executeMethod(method);
			method.releaseConnection();

		} catch (Exception e) {
			log.debug(" updateHTTP   Exception   " + e.getMessage());
			e.printStackTrace();
			return null;			
		}

		return "success";

	}



}
