package jobs;

import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStop;
import utils.DataAccess;

/**
 * @author Ivana Frankic
 *
 */
@OnApplicationStop
public class DBTeardown extends Job {
	private static Logger log = new Logger();

	/* (non-Javadoc)
	 * @see play.jobs.Job#doJob()
	 */
	public void doJob() {
		log.debug("DBTeardown.doJob():");

//		DataAccess.getDbInterface().dbTeardown();
	}
}
