package nts.uk.ctx.sys.portal.ws.jobtitletying;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.jobtitletying.JobTitleTyingCommand;
import nts.uk.ctx.sys.portal.app.command.jobtitletying.UpdateJobTitleTyingCommand;
import nts.uk.ctx.sys.portal.app.command.jobtitletying.UpdateJobTitleTyingCommandHandler;
import nts.uk.ctx.sys.portal.app.find.jobtitletying.JobTitleTyingDto;
import nts.uk.ctx.sys.portal.app.find.jobtitletying.JobTitleTyingFinder;


@Path("sys/portal/jobtitletying")
@Produces("application/json")
public class JobTitleTyingWebservice extends WebService {
	@Inject
	private JobTitleTyingFinder finder;
	
	@Inject
	private UpdateJobTitleTyingCommandHandler updateJobTitleTying;

	@POST
	@Path("update")
	public void updateJobTitleTying(List<JobTitleTyingCommand>  command) {
		UpdateJobTitleTyingCommand  obj = new UpdateJobTitleTyingCommand();
		obj.setJobTitleTyings(command);
		this.updateJobTitleTying.handle(obj);
	}
	
	@POST
	@Path("findWebMenuCode")
	public List<JobTitleTyingDto> findAllDisplay(String jobId) {
		return finder.findWebMenuCode(jobId);
	}
	
}
