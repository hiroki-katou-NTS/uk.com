package nts.uk.ctx.bs.employee.ws.jobtitle.info;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.jobtitle.info.JobTitleInfoFinder;
import nts.uk.ctx.bs.employee.app.find.jobtitle.info.dto.JobTitleInfoFindDto;

/**
 * The Class JobTitleInfoWebService.
 */
@Path("bs/employee/jobtitle/info")
@Produces(MediaType.APPLICATION_JSON)
public class JobTitleInfoWebService extends WebService {
	
	/** The job title info finder. */
	@Inject
	private JobTitleInfoFinder jobTitleInfoFinder;
	
	@Path("findByJobIdAndHistoryId")
	@POST
	public JobTitleInfoFindDto findByJobIdAndHistoryId(JobTitleInfoFindDto findObj) {
		return this.jobTitleInfoFinder.findByJobIdAndHistoryId(findObj.getJobTitleId(), findObj.getJobTitleHistoryId());
	}	
}
