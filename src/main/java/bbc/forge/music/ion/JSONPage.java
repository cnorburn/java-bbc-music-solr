package bbc.forge.music.ion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import bbc.forge.music.ion.IONHttpConnect;

public class JSONPage {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	public String getJSONPage(String uri) throws Exception{
		
		IONHttpConnect ion=new IONHttpConnect();
		
		StringBuffer content = new StringBuffer();

		try {
			
			HttpURLConnection conn=ion.getConnection(uri);
				
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = rd.readLine()) != null) {
				content.append(line);	          
			}
			conn.disconnect();

			return content.toString();

		} catch (Exception e) {
			throw new Exception("Connect to ION error  - " + e.getMessage());
		}
	
	}
	
		
	}
	


