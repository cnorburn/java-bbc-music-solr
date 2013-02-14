package bbc.forge.music.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import bbc.forge.music.webservice.UpdateJob;

public class TimerJob implements Job {

	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		UpdateJob update=new UpdateJob();
	
		log.debug("SOLR_MASTER - " + System.getenv("SOLR_MASTER"));
		log.debug("SOLR_SLAVE - " + System.getenv("SOLR_SLAVE"));
		if (!System.getenv("SOLR_MASTER").equals("true")){
			log.debug("Tomcat not MASTER -  no index updates run. ");
			return;
		}

		update.main();
		
		
	}
	
}
