package nts.uk.ctx.basic.ws.organization.position;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.command.organization.position.DeletePositionCommand;
import nts.uk.ctx.basic.app.command.organization.position.DeletePositionCommandHandler;
import nts.uk.ctx.basic.app.command.organization.position.UpdatePositionCommand;
import nts.uk.ctx.basic.app.command.organization.position.UpdatePositionCommandHandler;
import nts.uk.ctx.basic.app.find.organization.position.JobHistDto;
import nts.uk.ctx.basic.app.find.organization.position.JobHistFinder;
import nts.uk.ctx.basic.app.find.organization.position.JobTitleDto;
import nts.uk.ctx.basic.app.find.organization.position.JobTitleFinder;


@Path("basic/position")
@Produces("application/json")
public class PositionWebService extends WebService{

	@Inject
	private JobTitleFinder positionFinder;
	@Inject
	private JobHistFinder histFinder;
	//@Inject
	//private CreatePositionCommandHandler addPosition;
	@Inject
	private UpdatePositionCommandHandler updatePosition;
	@Inject
	private DeletePositionCommandHandler deletePosition;
	
	@POST
	@Path("findallposition/{historyId}")
	public List<JobTitleDto> findAllPosition(@PathParam("historyId") String historyId){

		return this.positionFinder.findAllPosition(historyId);
	}

	@POST
	@Path("getallhist")
	public List<JobHistDto> getAllHistory(){

		return this.histFinder.getAllHistory();
	}
//	@POST
//	@Path("findposition/{jobCode}/{historyId}")
//	public Optional<PositionDto> find(@PathParam("jobCode") String jobCode,@PathParam("historyId") String historyId){
//		return this.finder.find(jobCode, historyId);
//	}
	
//	@POST
//	@Path("addPosition")
//	public void add(CreatePositionCommand command){
//		this.addPosition.handle(command);		
//	}
	
	@POST
	@Path("updatePosition")
	public void update(UpdatePositionCommand command){
		this.updatePosition.handle(command);
	}
	
	@POST
	@Path("deletePosition")
	public void deletePosition(DeletePositionCommand command){
		this.deletePosition.handle(command);
	}
}
