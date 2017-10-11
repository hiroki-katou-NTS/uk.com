package nts.uk.ctx.bs.employee.ws.jobtitle;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.jobtitle.JobTitleFinder;
import nts.uk.ctx.bs.employee.app.find.jobtitle.dto.JobTitleFindDto;

/**
 * The Class JobTitleWebService.
 */
@Path("bs/employee/jobtitle")
@Produces(MediaType.APPLICATION_JSON)
public class JobTitleWebService extends WebService {

	/** The job title finder. */
	@Inject
	private JobTitleFinder jobTitleFinder;

	/**
	 * Find by job id.
	 *
	 * @param findObj the find obj
	 * @return the list
	 */
	@Path("history/findByJobId")
	@POST
	public JobTitleFindDto findByJobId(JobTitleFindDto findObj) {
		return this.jobTitleFinder.findJobHistoryByJobId(findObj.getJobTitleId());
	}	
}
