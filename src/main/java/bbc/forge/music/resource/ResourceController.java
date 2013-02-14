package bbc.forge.music.resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.xml.sax.SAXException;

import bbc.forge.music.webservice.Update;
import bbc.forge.music.webservice.UpdateJob;

import bbc.forge.music.pojo.Record;
import bbc.forge.music.utils.Logging;
import bbc.forge.music.quartz.TimerJob;
import bbc.forge.music.quartz.TimerJobScheduler;

import javax.ws.rs.QueryParam;

@Path("/")
public class ResourceController {
	protected final Log log = LogFactory.getLog(getClass());

	private Update update=new Update();
	private Logging logging=new Logging();
	private TimerJob timer=new TimerJob();
	private TimerJobScheduler scheduler = new TimerJobScheduler();

	
	@GET 
    @Path(value="/update")
    public String update(@QueryParam("solr") String solr) throws Exception {

		Properties prop = new Properties();

   		prop.setProperty("solr.instance.local", solr);
   		prop.store(new FileOutputStream("int.properties"), null);
	
		System.out.println("Starting solr update from uri");
		System.out.println("url - " + solr);
	
		UpdateJob.main();

		return "success";
		
	}
	
	
	@GET 
    @Path(value="/start")
    public String startTimer(){
		TimerJobScheduler.main();
		return null;
	}
    
	@GET 
    @Path(value="/stop")
    public String stopTimer(){		
		scheduler.stop();
		return null;
	}
	
    
}
