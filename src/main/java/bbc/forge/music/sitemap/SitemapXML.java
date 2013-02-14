package bbc.forge.music.sitemap;


import java.io.CharArrayReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.*;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bbc.forge.config.PropertiesMuncher;
import bbc.forge.music.pojo.Record;
import bbc.forge.music.utils.GUID;

public class SitemapXML {

	protected final Log log = LogFactory.getLog(getClass());
	public static Properties props  = PropertiesMuncher.munch();
	public static GUID guid=new GUID();

	public List <Record> getSitemapXML() {

		Reader reader = null;
		StringWriter writer = null;
		String charset = "UTF-8"; 

		try {
	
			URL url=new URL(props.getProperty("sitemap.url"));
	
			InputStream gzippedResponse = url.openStream();
			InputStream ungzippedResponse = new GZIPInputStream(gzippedResponse);
			reader = new InputStreamReader(ungzippedResponse, charset);
			writer = new StringWriter();
			
			char[] buffer = new char[1024];
			for (int length = 0; (length = reader.read(buffer)) > 0;) {
				writer.write(buffer, 0, length);
			}

		} catch (Exception e) {
			log.debug("    Exception   " + e.getMessage());
			e.printStackTrace();
			return null;			
		}
		
		return parseXML(writer.toString());

	}


	public List <Record> parseXML(String xml){
			
		ArrayList <Record> records=new ArrayList<Record>();
		
		try{

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Reader rdr=new CharArrayReader(xml.toCharArray());

			Document doc=db.parse(new InputSource(rdr));
			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("url");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList mbid = element.getElementsByTagName("loc");
				Element _mbid = (Element) mbid.item(0);

				NodeList priority = element.getElementsByTagName("priority");
				Element _priority = (Element) priority.item(0);
				
				NodeList artist = element.getElementsByTagName("og:title");

				NodeList type = element.getElementsByTagName("og:type");
				Element _type = (Element) type.item(0);

				if (artist.getLength()>0){
					Element _artist = (Element) artist.item(0);
					
					String t=getCharacterDataFromElement(_type);
					
					if(t.equalsIgnoreCase("musician") || t.equalsIgnoreCase("band")){
						
						String m=getCharacterDataFromElement(_mbid);
						String a=getCharacterDataFromElement(_artist);
												
						if(!m.equals(null) && !a.equals(null)){
							Record record = new Record();
							
							record.setMbid(guid.getGUID(m));
							record.setArtist(a);

							String p=getCharacterDataFromElement(_priority);
							if(!p.equals(null))
								record.setPriority(p);
										
							records.add(record);					
							
						}
					}

				}
	
			}

		}
		catch (Exception e) {
			log.debug("   parseXML Exception   " + e.getMessage());
			e.printStackTrace();
			return null;			
		}

	
		return records;

	}

	public static String getCharacterDataFromElement(Element e) throws IOException {
		
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			
			String data=cd.getData();

			// escapeXml is trashing the utf-8 charset, so do it manually
			//return StringEscapeUtils.escapeXml(cd.getData());

			data.replace("&", "&amp;");
			data.replace(">", "&gt;");
			data.replace("<", "&lt;");
		
			return data;
			
		}
		return "?";
	}


}
