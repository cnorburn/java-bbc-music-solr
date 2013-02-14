package bbc.forge.music.ion;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bbc.forge.config.PropertiesMuncher;

public class IONHttpConnect {

	protected final Log log = LogFactory.getLog(getClass());
	public static Properties props  = PropertiesMuncher.munch();

	public HttpURLConnection getConnection(String uri) throws Exception{
		int retries=0;
		
		HttpURLConnection conn;
	
		URL url=null;
		
		while (true){			
			try{				
				
				url = new URL(uri);
				
				conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				
				return conn;

			} catch (Exception e){
				
				log.debug("Connect to ION error  - " + e.getMessage());
				retries++;
				if (retries==Integer.parseInt(props.getProperty("http.retries"))); 
					throw new Exception("Connect to ION error  - " + e.getMessage());
			}
			
		}

	}
	
}
