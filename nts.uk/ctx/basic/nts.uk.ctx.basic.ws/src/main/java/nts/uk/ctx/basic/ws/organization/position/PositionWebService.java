package nts.uk.ctx.basic.ws.organization.position;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.command.organization.position.CreateJobTitleCommand;
import nts.uk.ctx.basic.app.command.organization.position.CreateJobTitleCommandHandler;
import nts.uk.ctx.basic.app.command.organization.position.RemoveJobTitleCommand;
import nts.uk.ctx.basic.app.command.organization.position.RemoveJobTitleCommandHandler;
import nts.uk.ctx.basic.app.command.organization.position.UpdateJobTitleCommand;
import nts.uk.ctx.basic.app.command.organization.position.UpdateJobTitleCommandHandler;
import nts.uk.ctx.basic.app.find.organization.position.JobTitleDto;
import nts.uk.ctx.basic.app.find.organization.position.JobTitleFinder;
import nts.uk.ctx.basic.app.find.organization.positionhistory.JobTitleHisDto;
import nts.uk.ctx.basic.app.find.organization.positionhistory.JobTitleHistoryFinder;





@Path("basic/position")
@Produces("application/json")
public class PositionWebService extends WebService {

	@Inject
	private JobTitleFinder positionFinder;
	
	@Inject
	private JobTitleHistoryFinder historyFinder;

	@Inject
	private CreateJobTitleCommandHandler createPositionCommandHandler;

	@Inject
	private UpdateJobTitleCommandHandler updatePositionCommandHandler;

	@Inject
	private RemoveJobTitleCommandHandler removePositionCommandHandler;
	
	
	@POST
	@Path("findallposition/{historyId}")
	public List<JobTitleDto> findAllPositionByHis(@PathParam("historyId") String historyId){

		return this.positionFinder.findAllPositionByHis(historyId);
	}

	@POST
	@Path("getallhist")
	public List<JobTitleHisDto> getAllHistory(){

		return this.historyFinder.getAllHistory();
	}

	@Path("findallposition")
	@POST
	public List<JobTitleDto> findAllPosition() {
		return positionFinder.findAllPosition();
	}


	@Path("add")
	@POST
	public void add(CreateJobTitleCommand command) {
		this.createPositionCommandHandler.handle(command);
	}

	@Path("update")
	@POST
	public void update(UpdateJobTitleCommand command) {
		this.updatePositionCommandHandler.handle(command);
	}

	@Path("remove")
	@POST
	public void remove(RemoveJobTitleCommand command) {
		this.removePositionCommandHandler.handle(command);
	}
	

}
