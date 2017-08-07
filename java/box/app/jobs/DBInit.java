package jobs;
/**
 * @author Ivana Frankic
 * */
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import utils.DataAccess;

@OnApplicationStart(async = false)
public class DBInit extends Job {
	private static Logger log = new Logger();

	
	/* (non-Javadoc)
	 * @see play.jobs.Job#doJob()
	 */
	public void doJob() {
		log.debug("DBInit.doJob():");

		DataAccess.getDbInterface().dbInit();
	}
}
