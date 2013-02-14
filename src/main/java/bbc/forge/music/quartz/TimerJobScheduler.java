package bbc.forge.music.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DateIntervalTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class TimerJobScheduler {
	protected final Log log = LogFactory.getLog(getClass());
	
	public TimerJobScheduler() {

		Scheduler scheduler;
			
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			JobDetail job = new JobDetail("solr-update-job", "solr-update-group", TimerJob.class);
			DateIntervalTrigger trigger = new DateIntervalTrigger("solr-update-trigger", "solr-update-group", DateIntervalTrigger.IntervalUnit.MINUTE, 60);
			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}
	
	public void stop(){
        Scheduler scheduler;

        try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			log.debug("Stopping scheduler");
						
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(){
		try{
			new TimerJobScheduler();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
